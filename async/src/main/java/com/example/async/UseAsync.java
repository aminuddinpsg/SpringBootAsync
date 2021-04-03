package com.example.async;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UseAsync{
	
	/* async only work 
	 * when its public
	 * when its not called from same class
	 * source: https://www.baeldung.com/spring-async#:~:text=Simply%20put%20%E2%80%93%20annotating%20a%20method,want%20to%20go%20that%20route.
	 */
	@Async
	public CompletableFuture<Integer> sendInfo (int a) throws InterruptedException{
		
		log.info("a num ---> "+a);
		Thread.sleep(3000L);
		log.info("done");
		return CompletableFuture.completedFuture(a);
	}

}
