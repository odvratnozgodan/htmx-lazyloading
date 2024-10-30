package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.path

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
    install(CallLogging) {
        filter { call ->
            !call.request.path().startsWith("/assets")
        }
    }
}
