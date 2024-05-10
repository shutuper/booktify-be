package org.qqsucc.booktify.common.util;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Component
public class AsyncService {

	@Async
	public void runAsync(Runnable runnable) {
		runnable.run();
	}

	@Async
	public <T> CompletableFuture<T> runAsync(Supplier<T> supplier) {
		return CompletableFuture.completedFuture(supplier.get());
	}

}
