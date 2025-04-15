package com.fps.demoapp

enum class DemoScreen(
    val title: String,
    val route: String) {

    Start(title = "", route = "start"),
    Main(title = "FPS Device Identifier", route = "main")
}
