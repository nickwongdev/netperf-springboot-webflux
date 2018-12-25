package com.nickwongdev.netperf.flux

import com.nickwongdev.netperf.service.WorkService
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class FluxApplication {

	@Bean
	fun workService(): WorkService {
		return WorkService()
	}
}

fun main(args: Array<String>) {
	SpringApplication.run(FluxApplication::class.java, *args)
}
