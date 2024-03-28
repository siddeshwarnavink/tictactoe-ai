package com.sidapps.tictactoeai.view

import javafx.scene.layout.GridPane
import tornadofx.View

class GameView : View("Tic-Tac-Toe") {
    override val root: GridPane by fxml("/GameView.fxml")
}