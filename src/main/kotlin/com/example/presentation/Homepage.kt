package com.example.presentation

import kotlinx.html.DIV
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.meta
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
        script {
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
            /*
            Code for loading the initial data from the server. This data contains the initial list of products
             but only as placeholders.
            */
            // The endpoint that will return the initial data
            attributes["hx-get"] = "/products"
            // Trigger when the page is loaded
            attributes["hx-trigger"] = "load"
            // Target this element when replacing the data
            attributes["hx-target"] = "#items"
            // Swap the outer HTML of the element. This is because we structured the response in that way
            attributes["hx-swap"] = "outerHTML"
            // Specify id of indicator we want to use for this request
            attributes["hx-indicator"] = "#spinner"
            h1("font-medium text-black text-3xl md:text-4xl my-5") { +"""Products""" }

            // This is just a indicator for showing the loading state.
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

