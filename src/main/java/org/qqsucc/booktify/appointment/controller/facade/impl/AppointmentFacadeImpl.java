package org.qqsucc.booktify.appointment.controller.facade.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.qqsucc.booktify.appointment.controller.dto.*;
import org.qqsucc.booktify.appointment.controller.facade.AppointmentFacade;
import org.qqsucc.booktify.appointment.controller.mapper.AppointmentMapper;
import org.qqsucc.booktify.appointment.repository.entity.Appointment;
import org.qqsucc.booktify.appointment.repository.entity.enumeration.AppointmentStatus;
import org.qqsucc.booktify.appointment.service.AppointmentService;
import org.qqsucc.booktify.appointment.service.domain.AppointmentFilter;
import org.qqsucc.booktify.common.dto.TimeslotDto;
import org.qqsucc.booktify.common.exception.AccessDeniedException;
import org.qqsucc.booktify.common.exception.AlreadyExistException;
import org.qqsucc.booktify.common.exception.BusinessException;
import org.qqsucc.booktify.common.security.bean.CustomUserDetails;
import org.qqsucc.booktify.common.util.SecurityUtils;
import org.qqsucc.booktify.notification.service.NotificationService;
import org.qqsucc.booktify.procedure.repository.entity.Procedure;
import org.qqsucc.booktify.procedure.service.ProcedureService;
import org.qqsucc.booktify.procedure.service.TimeslotService;
import org.qqsucc.booktify.user.controller.dto.ClientDto;
import org.qqsucc.booktify.user.controller.mapper.UserMapper;
import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.repository.entity.enums.UserRole;
import org.qqsucc.booktify.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AppointmentFacadeImpl implements AppointmentFacade {

	NotificationService notificationService;
	AppointmentService appointmentService;
	ProcedureService procedureService;
	TimeslotService timeslotService;
	PasswordEncoder passwordEncoder;
	UserService userService;

	AppointmentMapper appointmentMapper;
	UserMapper userMapper;

	EntityManager entityManager;

	@Override
	public Page<AppointmentRespDto> getAppointments(Boolean showCanceled, Pageable pageable) {
		CustomUserDetails authUser = SecurityUtils.getAuthUserDetails();
		return appointmentService.findAllFilter(AppointmentFilter.builder()
				.statuses(showCanceled ? EnumSet.allOf(AppointmentStatus.class) : EnumSet.of(AppointmentStatus.ACTIVE))
				.masterId(UserRole.ROLE_MASTER.equals(authUser.getRole()) ? authUser.getId() : null)
				.clientId(UserRole.ROLE_CLIENT.equals(authUser.getRole()) ? authUser.getId() : null)
				.build(), pageable
		).map(appointmentMapper::toRespDto);
	}

	@Override
	@Transactional
	public AppointmentDto bookAppointment(AppointmentReqDto appointmentReqDto) {
		return bookAppointmentForClient(appointmentReqDto, SecurityUtils.getAuthUserId());
	}

	@Override
	@Transactional
	public AppointmentDto bookAppointmentByMaster(AppointmentMasterReqDto appointmentReqDto) {
		User client = findExistedOrCreateNewClient(appointmentReqDto.getClient());
		return bookAppointmentForClient(appointmentReqDto, client.getId());
	}

	@Override
	public AppointmentDto remindAppointment(UUID appointmentId, AppointmentRemindDto remindDto) {
		Appointment appointment = appointmentService.findById(appointmentId);

		throwIfAppointmentCanNotBeChanged(SecurityUtils.getAuthUser(), appointment);

		Instant notificationDate = appointment.getStartDate().minus(remindDto.getHoursBefore(), ChronoUnit.HOURS);

		if (notificationDate.isBefore(Instant.now())) {
			throw new BusinessException("Notification date can not be in the past");
		}

		appointment.setNotificationDate(notificationDate);

		return appointmentMapper.toDto(appointment);
	}

	@Override
	@Transactional
	public AppointmentDto cancelAppointment(UUID appointmentId, AppointmentCancelDto cancelDto) {
		Appointment appointment = appointmentService.findById(appointmentId);

		throwIfAppointmentCanNotBeChanged(SecurityUtils.getAuthUser(), appointment);

		appointment.setStatus(AppointmentStatus.CANCELED);
		appointment.setCanceledReason(cancelDto.getCancelReason());
		appointment.setCanceledDate(Instant.now());

		return appointmentMapper.toDto(appointment);
	}

	private AppointmentDto bookAppointmentForClient(AppointmentReqDto appointmentReqDto, UUID clientId) {
		Procedure procedure = procedureService.findById(appointmentReqDto.getProcedureId());

		TimeslotDto timeslotDto = new TimeslotDto(appointmentReqDto.getStartDate(), appointmentReqDto.getEndDate());
		boolean isNotAvailableTimeslot = !timeslotService.isAvailableTimeslot(timeslotDto, procedure);

		if (isNotAvailableTimeslot) {
			throw new BusinessException("Picked timeslot is not available/already booked");
		}

		Instant notificationDate = Optional.ofNullable(appointmentReqDto.getNotificationDate())
				.filter(date -> appointmentReqDto.getStartDate().isBefore(date))
				.orElse(null);

		Appointment appointment = Appointment.builder()
				.price(procedure.getPrice())
				.clientId(clientId)
				.startDate(appointmentReqDto.getStartDate())
				.endDate(appointmentReqDto.getEndDate())
				.notificationDate(notificationDate)
				.procedureId(procedure.getId())
				.build();

		Appointment savedAppointment = appointmentService.save(appointment);

		entityManager.flush();
		entityManager.refresh(savedAppointment);

		notificationService.sendAppointmentBooked(appointment);

		return appointmentMapper.toDto(savedAppointment);
	}

	private User findExistedOrCreateNewClient(ClientDto clientDto) {
		Optional<User> existedUser = userService.findOptByPhone(clientDto.getPhone());

		if (existedUser.isPresent() && !existedUser.get().isClient()) {
			throw new AlreadyExistException("Entered phone number is not available");
		}

		return existedUser.orElseGet(() -> {
			User newClient = userMapper.toClientUser(clientDto);
			newClient.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
			return userService.save(newClient);
		});
	}

	private static void throwIfAppointmentCanNotBeChanged(User authUser, Appointment appointment) {
		boolean userIsNotAppointmentMaster = authUser.isMaster() && ObjectUtils.notEqual(
				authUser.getId(), appointment.getProcedure().getMasterId()
		);
		boolean userIsNotAppointmentClient = authUser.isClient() && ObjectUtils.notEqual(
				authUser.getId(), appointment.getClientId()
		);

		if (userIsNotAppointmentMaster || userIsNotAppointmentClient) {
			throw new AccessDeniedException("No access to perform appointment cancellation");
		}

		if (AppointmentStatus.CANCELED.equals(appointment.getStatus())) {
			throw new BusinessException("Appointment is already canceled");
		}

		if (Instant.now().isAfter(appointment.getStartDate())) {
			throw new BusinessException("Past appointment can not be canceled");
		}
	}

}
