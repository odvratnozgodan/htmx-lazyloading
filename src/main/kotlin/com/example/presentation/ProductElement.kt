package com.example.presentation

import com.example.model.product.Product
import kotlinx.html.DIV
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.p

fun DIV.productPlaceholder(elementId: Int) {
    productContainer {
        id = "product_$elementId"
        val timeout = 1.5f
        // Code for loading the product data from the server. This contains the actual data of the product.
        attributes["hx-get"] = "/product/$elementId"
        // Target this element when replacing the data.
        attributes["hx-target"] = "#product_$elementId"
        // Trigger when the item becomes visible. For that we use the isElementVisible function written in the
        // script block of the HTML.homePage() function.
        // This is triggered on page load(to check the elements that are visible right away) and
        // every 1.5 seconds(to check if the element has come into view).
        attributes["hx-trigger"] =
            "load[isElementVisible(\"product_$elementId\")],every ${timeout}s [isElementVisible(\"product_$elementId\")]"
        // Swap the outer HTML of the element.
        attributes["hx-swap"] = "outerHTML"
    }
}

fun DIV.productElement(product: Product) {
    productContainer {
        id = "product_${product.id}"

        productThumbImage(product)
        productTitle(product.title)
        productDescription(product.description)
        productFooter {
            productPrice(product.price)
            buttonAddToCart()
        }
    }
}

private fun DIV.productContainer(block: DIV.() -> Unit = {}) {
    div("flex flex-col rounded-md p-2 gap-2 bg-gray-200 min-h-64") {
        block()
    }
}

private fun DIV.productThumbImage(product: Product) {
    img(classes = "max-h-48 aspect-auto object-cover") {
        src = product.thumbnail
        alt = "productThumb_${product.id}"
    }
}

private fun DIV.productTitle(title: String) {
    p("font-medium text-black text-lg") { +title }
}

private fun DIV.productDescription(description: String) {
    p("font-regular text-black text-base") { +description }
}

private fun DIV.productFooter(block: DIV.() -> Unit = {}) {
    div("flex-row md:inline-flex justify-between items-center") {
        block()
    }
}

private fun DIV.productPrice(price: Double) {
    p("font-medium text-black text-2xl") { +"${String.format("%.2f", price)}$" }
}

private fun DIV.buttonAddToCart() {
    button(
        classes = "w-full md:w-auto px-4 py-2 text-sm font-medium leading-5 text-white transition-colors duration-150 bg-purple-600 border border-transparent rounded-lg active:bg-purple-600 hover:bg-purple-700 focus:outline-none focus:shadow-outline-purple"
    ) {
        +"""Add to cart"""
    }
}
