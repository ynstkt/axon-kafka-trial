package org.example.axonkafkatrial.producer.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class TrialController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun get(): GetResponse {
        return GetResponse("success")
    }
}

data class GetResponse(val body: String)
