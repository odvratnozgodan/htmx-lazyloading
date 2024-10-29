## Grid item lazy loading# README.md

## Introduction

This project is a simple demo of using HTMX to lazily load grid items as they become visible to the user. The motivation
behind this demo is to showcase how HTMX can be used to improve the performance of web applications by only loading the
necessary data when it's needed.
I was inspired by the great article [How to create Conditional HX-Triggers based on Element Visibility with HTMX](https://hamy.xyz/labs/2024-10_htmx-trigger-isvisible-conditional)
by Hamilton Greene. This is almost a 1:1 of what he did but I wanted to showcase how it could be used for loading data 
on an e-commerce platform.

## Live preview

You can check how it works here: [https://htmx-lazyloading.onrender.com](https://htmx-lazyloading.onrender.com/)

**Please note** this is hosted as a free instance on Render, it spins down when inactive, so you may experience a delay
when initially loading the page. In that case please be patient, it may take up to 50 seconds for the instance to 
spin up, but after that it should behave normally. 

## Technologies Used

This demo uses the following technologies alongside **[Ktor](https://ktor.io/)**:

1. **[HTMX](https://htmx.org/)**: HTMX (HTML + JavaScript Mashup) is a JavaScript library that allows you to update
   parts of your web page without a full page refresh. It does this by using HTTP requests to fetch and update the
   necessary data. In this demo, HTMX is used to lazily load grid items as they come into view.

2. **[TailwindCSS](https://tailwindcss.com/docs/)**: TailwindCSS is a utility-first CSS framework that allows you to
   build responsive, customizable web applications quickly. In this demo, TailwindCSS is used for basic styling of the
   grid items.

## How HTMX is Used in this Demo

In this demo, HTMX is used to lazily load grid items as they come into view. Initially, only the placeholders for the
grid items are loaded. When a item comes into view, a HTMX request is sent to the server to fetch the view data for
that item. The server then responds with the necessary data, which is then used to update the grid item's content. This
process is repeated for each grid item as it comes into view, ensuring that only the necessary data is loaded when it's
needed.

To achieve this, the following steps are taken:

1. The `hx-get` attribute is added to the grid item's container, specifying the URL to fetch the data from when the
   item comes into view.

2. The `hx-target` attribute is added to the grid item's container, specifying the element that should be updated with
   the fetched data.

3. The `hx-trigger` attribute is added to the grid item's container, specifying the event that triggers the HTMX
   request.

4. The `hx-swap` attribute is added to the grid item's container, specifying which item to swap out when the data is 
   received.

By following these steps, HTMX is able to lazily load grid items as they come into view, improving the performance of
the web application and providing a smoother user experience.

There are two places HTMX is used:
1. The [homePage() function](src/main/kotlin/com/example/presentation/Homepage.kt) where we initially load the grid with item placeholders.
2. The [productPlaceholder() function](src/main/kotlin/com/example/presentation/ProductElement.kt) where we load the data for the single item.