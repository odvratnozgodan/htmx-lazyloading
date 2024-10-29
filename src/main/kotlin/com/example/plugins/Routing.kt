package com.example.plugins

import com.example.data.repository.ProductsRepository
import com.example.presentation.homePage
import com.example.presentation.productElement
import com.example.presentation.productPlaceholder
import com.example.presentation.productsGrid
import io.ktor.server.application.*
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.staticFiles
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.div
import kotlinx.html.stream.createHTML
import java.io.File

fun Application.configureRouting() {
    routing {
        val jobsRepository = ProductsRepository()

        staticFiles("/assets", File("assets"))

        get("/") {
            call.respondHtml{
                homePage()
            }
        }

        get("/products") {
            val response = jobsRepository.getProducts()
            call.respondText {
                createHTML().div{
                    // Render the products grid with placeholder.
                    productsGrid {
                        response.forEach {
                            productPlaceholder(it.id)
                        }
                    }
                }.toString()
            }
        }

        get("/product/{id}") {
            val productId = call.parameters["id"]?.toInt() ?: -1
            val response = jobsRepository.getProduct(productId)
            response?.let {
                call.respondText {
                    createHTML().div{
                        // Render the product with the actual data.
                        productElement(response)
                    }.toString()
                }
            } ?: throw BadRequestException("No job with id")
        }
    }
}
