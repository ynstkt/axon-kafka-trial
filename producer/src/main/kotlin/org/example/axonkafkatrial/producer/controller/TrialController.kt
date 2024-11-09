package org.example.axonkafkatrial.producer.controller

import org.axonframework.commandhandling.gateway.CommandGateway
import org.example.axonkafkatrial.producer.command.DocCreate
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/")
class TrialController(private val commandGateway: CommandGateway) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun get(): GetResponse {
        return GetResponse("success")
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody req: PostRequest): PostResponse {
        val id = UUID.randomUUID()
        val command = DocCreate(id, req.body)
        commandGateway.sendAndWait<DocCreate>(command)
        return PostResponse(id)
    }
}

data class GetResponse(val body: String)
data class PostRequest(val body: String)
data class PostResponse(val id: UUID)
