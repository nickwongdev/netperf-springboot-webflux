package com.nickwongdev.netperf.flux.handler

import com.nickwongdev.netperf.flux.model.WorkRequest
import com.nickwongdev.netperf.flux.model.WorkResponse
import com.nickwongdev.netperf.service.WorkService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.ResponseStatusException

/**
 * Service that handles Routed calls
 */
@Component
class WorkHandler @Autowired constructor(
		private val workService: WorkService
) {

	companion object {
		const val DEFAULT_NUMCOROUTINES = 1
		const val DEFAULT_CALCITERMIN = 1
		const val DEFAULT_CALCITERMAX = 2
		const val DEFAULT_WAITMIN = 1L
		const val DEFAULT_WAITMAX = 2L
	}

	suspend fun work(serverRequest: ServerRequest): ServerResponse = coroutineScope {

		// Extract the query params
		val iter = serverRequest.queryParam("numCoroutines").map(String::toInt).orElse(DEFAULT_NUMCOROUTINES)
		val cMin = serverRequest.queryParam("calculationsMin").map(String::toInt).orElse(DEFAULT_CALCITERMIN)
		val cMax = serverRequest.queryParam("calculationsMax").map(String::toInt).orElse(DEFAULT_CALCITERMAX)
		val wMin = serverRequest.queryParam("waitMin").map(String::toLong).orElse(DEFAULT_WAITMIN)
		val wMax = serverRequest.queryParam("waitMax").map(String::toLong).orElse(DEFAULT_WAITMAX)

		// To be fair, we should decode the payload (Using Kotlin await syntax)
		val workRequest = serverRequest.body(BodyExtractors.toMono(WorkRequest::class.java)).awaitSingle()

		// Coroutine Invocation of Work
		val iterations = workService.work(iter, cMin, cMax, wMin, wMax)

		// Check to make sure iterations happened
		if (iterations < 1) {
			throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Your request parameters did not result in enough iterations")
		}

		// Async Await build the response
		ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(fromObject(WorkResponse(workRequest.id, iterations)))
				.awaitSingle()
	}
}
