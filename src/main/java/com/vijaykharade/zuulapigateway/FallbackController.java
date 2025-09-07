package com.vijaykharade.zuulapigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

	@GetMapping("/currency-converter-fallback")
	public Mono<String> currencyCalculationFallback(){
		return Mono.just("Currency Calculation Service is currently unavailable. Please try again later.");
	}
	
	@GetMapping("/currency-converter-feign-fallback")
	public Mono<String> currencyCalculationFeignFallback(){
		return Mono.just("Currency Calculation Service (Feign) is currently unavailable. Please try again later.");
	}
	
	@GetMapping("/currency-exchange-fallback")
	public Mono<String> currencyExchangeFallback(){
		return Mono.just("Currency Exchange Service is currently unavailable. Please try again later.");
	}
}
