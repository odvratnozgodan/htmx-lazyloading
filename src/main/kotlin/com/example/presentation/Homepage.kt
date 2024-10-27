package com.example.presentation

import com.example.model.product.Product
import kotlinx.html.DIV
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.meta
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.style
import kotlinx.html.unsafe

fun HTML.homePage() {
    head {
        meta {
            charset = "UTF-8"
        }
        meta {
            name = "viewport"
            content = "width=device-width, initial-scale=1.0"
        }

        /* Load HTMX */
        script(src = "https://unpkg.com/htmx.org@1.9.4") { }
        script(src = "https://unpkg.com/htmx.org/dist/ext/json-enc.js") { }
        script(src = "https://unpkg.com/htmx.org/dist/ext/preload.js") { }

        /* Load TailwindCSS from Play CDN */
        script {
            src = "https://cdn.tailwindcss.com"
        }
        /* Change the default HTMX indicator style */
        style {
            +"""
                .htmx-indicator{
                    visibility: hidden;
                    opacity:0;
                    transition: opacity 500ms ease-in;
                }
                .htmx-request .htmx-indicator{
                    visibility: visible;
                    opacity:1;
                }
                .htmx-request.htmx-indicator{
                    visibility: visible;
                    opacity:1;
                }
            """
        }
        script{
            unsafe {
                +"""
                function isElementVisible(elementId) {
                    const element = document.getElementById(elementId);
                    if (!element) {
                        return false;
                    }
                    const elementRectangle = element.getBoundingClientRect();
                    const elementTop = elementRectangle.top;
                    const elementBottom = elementTop + elementRectangle.height;
                
                    const isVisible = elementTop < window.innerHeight && elementBottom >= 0;
                    return isVisible;
                }
            """.trimIndent()
            }
        }
    }
    body {
        div("container max-w-screen-xl mx-auto px-4") {
            // Code for loading the initial data from the server. This data contains the initial list of products
            // but only as placeholders.
            attributes["hx-get"] = "/products"
            attributes["hx-trigger"] = "load"
            attributes["hx-swap"] = "outerHTML"
            attributes["hx-target"] = "#items"
            attributes["hx-indicator"] = "#spinner"
            h1("font-medium text-black text-3xl md:text-4xl my-5") { +"""Products""" }
            div("htmx-indicator w-full flex items-center justify-center mb-5") {
                id = "spinner"
                div("relative") {
                    img(classes = "h-8 w-8") {
                        src = "/assets/loading-bars.svg"
                    }
                }
            }
            productsGrid()
        }
    }
}

fun DIV.productsGrid(block: DIV.() -> Unit = {}) {
    div("grid grid-cols-3 items-stretch gap-4 justify-center") {
        id = "items"
        block()
    }
}

fun DIV.productElement(elementId:Int, product: Product?){
    div("flex flex-col rounded-md p-2 gap-2 bg-gray-200 min-h-64") {
        id="product_$elementId"
        if(product == null){
            // Code for loading the item data from the server
            val timeout = 1.5f
            attributes["hx-get"] = "/product/$elementId"
            attributes["hx-target"] = "#product_$elementId"
            attributes["hx-trigger"] = "load[isElementVisible(\"product_$elementId\")],every ${timeout}s [isElementVisible(\"product_$elementId\")]"
            attributes["hx-swap"] = "outerHTML"
        }

        product?.let {
            productElementContent(it)
        }
    }
}

fun DIV.productElementContent(product: Product){
    img(classes = "max-h-48 aspect-auto object-cover") {
        src = product.thumbnail
        alt = "productThumb_${product.id}"
    }
    p("font-medium text-black text-lg") { +product.title }
    p("font-regular text-black text-base") { +product.description }
    div("inline-flex justify-between items-center") {
        p("font-medium text-black text-2xl") { +"${String.format("%.2f", product.price)}$" }
        button(
            classes = "px-4 py-2 text-sm font-medium leading-5 text-white transition-colors duration-150 bg-purple-600 border border-transparent rounded-lg active:bg-purple-600 hover:bg-purple-700 focus:outline-none focus:shadow-outline-purple"
        ) {
            +"""Add to cart"""
        }
    }
}