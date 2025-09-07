package com.vijaykharade.zuulapigateway;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import io.micrometer.tracing.Tracer;
import reactor.core.publisher.Mono;

/**
 * A global filter for logging incoming requests to the API Gateway.
 * Unlike Zuul's blocking model, Spring Cloud Gateway uses a reactive, non-blocking model.
 * This filter implements GlobalFilter and Ordered to work within the Gateway's filter chain.
 */
@Component
public class GatewayLoggingFilter implements GlobalFilter, Ordered {

	private final Logger logger = LoggerFactory.getLogger(GatewayLoggingFilter.class);

	// Inject the Micrometer Tracer to access trace and span IDs
		@Autowired
		private Tracer tracer;

		@Override
		public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
			// Log the incoming request's path, including trace and span IDs for correlation
			String traceId = tracer.currentSpan() != null ? tracer.currentSpan().context().traceId() : "N/A";
			String spanId = tracer.currentSpan() != null ? tracer.currentSpan().context().spanId() : "N/A";
			logger.info("Request received. Trace ID: {}, Span ID: {}, Path: {}", traceId, spanId, exchange.getRequest().getPath());

			// Continue the filter chain
			return chain.filter(exchange);
		}

		@Override
		public int getOrder() {
			// Set the order to ensure this filter runs early in the chain
			return -1;
		}
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		// Log the incoming request's path
//		logger.info("Request Path -> {}", exchange.getRequest().getPath());
//
//		// Continue the filter chain
//		return chain.filter(exchange);
//	}
//
//	@Override
//	public int getOrder() {
//		// Set the order to ensure this filter runs early in the chain
//		return -1;
//	}
}