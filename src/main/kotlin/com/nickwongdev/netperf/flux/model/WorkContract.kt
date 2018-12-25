package com.nickwongdev.netperf.flux.model

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

data class WorkRequest @JsonCreator constructor(val id: UUID = UUID.randomUUID(), var content: String)
data class WorkResponse @JsonCreator constructor(val id: UUID = UUID.randomUUID(), var iterations: Int)
