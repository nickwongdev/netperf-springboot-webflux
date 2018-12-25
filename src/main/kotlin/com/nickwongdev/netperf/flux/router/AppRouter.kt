package com.nickwongdev.netperf.flux.router

import com.nickwongdev.netperf.flux.handler.WorkHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

/**
 * Work Router
 */
@Configuration
class AppRouter @Autowired constructor(private val workHandler: WorkHandler) {

	@Bean
	fun workRouter() = router {
		(POST("/work") and accept(MediaType.APPLICATION_JSON_UTF8)).invoke {
			// Switch to coroutines as soon as possible
			GlobalScope.mono {
				workHandler.work(it)
			}
		}
	}
}

