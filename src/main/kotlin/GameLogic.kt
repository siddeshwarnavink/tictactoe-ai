package org.sidapps.tictactoeai

import org.sidapps.tictactoeai.data.CellState
import org.sidapps.tictactoeai.data.GameBoard
import org.sidapps.tictactoeai.data.Gamestate
import org.sidapps.tictactoeai.data.TreeNode

class GameLogic {
    companion object {
        fun makePredictions(gameState: Gamestate, depth: Int = 0, maxDepth: Int = 3): TreeNode<GameBoard> {
            val rootNode = TreeNode(gameState.gameBoard)

            if (depth < maxDepth) {
                val currentPlayer = if (gameState.isPlayer) CellState.X else CellState.O
                val nextStateList = mutableListOf<GameBoard>()

                for (i in 0 until 3) {
                    for (j in 0 until 3) {
                        if (gameState.gameBoard.getCellState(i, j) == CellState.EMPTY) {
                            val nextBoard = gameState.gameBoard.copy()
                            nextBoard.setCellState(i, j, currentPlayer)
                            nextStateList.add(nextBoard)
                        }
                    }
                }

                for (nextBoard in nextStateList) {
                    val nextState = Gamestate(!gameState.isPlayer, nextBoard)
                    val childNode = makePredictions(nextState, depth + 1, maxDepth)
                    rootNode.addChild(childNode)
                }
            }

            return rootNode
        }
    }
}