package com.sidapps.tictactoeai.com.sidapps.tictactoeai

import com.sidapps.tictactoeai.view.GameView
import javafx.stage.Stage
import tornadofx.App
import tornadofx.launch

class MyApp: App(GameView::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.resizableProperty().set(false)
    }
}

fun main() {
    launch<MyApp>()
}