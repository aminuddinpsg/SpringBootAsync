package com.example.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.util.TypeKey;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AppRunner implements CommandLineRunner{
	
	@Autowired
	UseAsync useAsync;
	
	@Override
	public void run(String... args) throws Exception {
		
		log.info("start - testing");
		
		List<CompletableFuture<Integer>> completableFutures = new ArrayList<>(); 
		
		IntStream.range(1, 6)
			.forEach( nbr-> {
				
				CompletableFuture<Integer> comp = null;
				try {
					 comp = useAsync.sendInfo(nbr);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				completableFutures.add(comp);
			});
		
		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
        // avoid throwing an exception in the join() call
				        .exceptionally(ex -> null)
				        .join();
		
		Map<Boolean, List<CompletableFuture<Integer>>> result =
		        completableFutures.stream()
		                .collect(Collectors.partitioningBy(CompletableFuture::isCompletedExceptionally));
		
		//return true if get exception
		List<CompletableFuture<Integer>> Error = result.get(true);
		// return false if does not get exception
		List<CompletableFuture<Integer>> notError = result.get(false);
		
		for(CompletableFuture<Integer> getInt : Error) {
			log.info("asdasd "+getInt.get());
		}
		
		for(CompletableFuture<Integer> getInt : notError) {
			log.info("asdasd "+getInt.get());
		}
		
		log.info("end - testing");
		
	}

}
