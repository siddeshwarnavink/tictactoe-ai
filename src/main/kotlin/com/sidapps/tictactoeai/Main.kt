package com.sidapps.tictactoeai.com.sidapps.tictactoeai

import com.sidapps.tictactoeai.view.GameView
import tornadofx.App
import tornadofx.launch

class MyApp: App(GameView::class)

fun main() {
    launch<MyApp>()
}