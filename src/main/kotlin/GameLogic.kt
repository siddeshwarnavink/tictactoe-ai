package com.sidapps.tictactoeai

import com.sidapps.tictactoeai.data.CellState
import com.sidapps.tictactoeai.data.GameBoard
import com.sidapps.tictactoeai.data.Gamestate
import com.sidapps.tictactoeai.data.TreeNode
import kotlin.math.max
import kotlin.math.min

object GameLogic {
    fun countPotentialWins(gameBoard: GameBoard, player: CellState): Int {
        var potentialWins = 0

        // Rows
        for (row in 0 until 3) {
            if (gameBoard.board[row][0] == player && gameBoard.board[row][1] == player && gameBoard.board[row][2] != player)
                potentialWins++

            if (gameBoard.board[row][0] == player && gameBoard.board[row][2] == player && gameBoard.board[row][1] != player)
                potentialWins++

            if (gameBoard.board[row][1] == player && gameBoard.board[row][2] == player && gameBoard.board[row][0] != player)
                potentialWins++
        }

        // Columns
        for (col in 0 until 3) {
            if (gameBoard.board[0][col] == player && gameBoard.board[1][col] == player && gameBoard.board[2][col] != player)
                potentialWins++

            if (gameBoard.board[0][col] == player && gameBoard.board[2][col] == player && gameBoard.board[1][col] != player)
                potentialWins++

            if (gameBoard.board[1][col] == player && gameBoard.board[2][col] == player && gameBoard.board[0][col] != player)
                potentialWins++
        }

        // Diagonals
        if (gameBoard.board[0][0] == player && gameBoard.board[1][1] == player && gameBoard.board[2][2] != player)
            potentialWins++

        if (gameBoard.board[0][0] == player && gameBoard.board[2][2] == player && gameBoard.board[1][1] != player)
            potentialWins++

        if (gameBoard.board[1][1] == player && gameBoard.board[2][2] == player && gameBoard.board[0][0] != player)
            potentialWins++

        if (gameBoard.board[0][2] == player && gameBoard.board[1][1] == player && gameBoard.board[2][0] != player)
            potentialWins++

        if (gameBoard.board[0][2] == player && gameBoard.board[2][0] == player && gameBoard.board[1][1] != player)
            potentialWins++

        if (gameBoard.board[1][1] == player && gameBoard.board[2][0] == player && gameBoard.board[0][2] != player)
            potentialWins++

        return potentialWins
    }


    fun checkWinner(gameBoard: GameBoard, player: CellState): Boolean {
        // Rows
        for (row in gameBoard.board) {
            if (row.all { it == player })
                return true
        }

        // Columns
        for (col in gameBoard.board.indices) {
            if (gameBoard.board.all { it[col] == player })
                return true
        }

        // Diagonals
        if (gameBoard.board[0][0] == player && gameBoard.board[1][1] == player && gameBoard.board[2][2] == player)
            return true
        if (gameBoard.board[0][2] == player && gameBoard.board[1][1] == player && gameBoard.board[2][0] == player)
            return true

        return false
    }

    fun calculateHeuristicValue(gameBoard: GameBoard): Int {
        if (checkWinner(gameBoard, CellState.O))
            return 100

        if (checkWinner(gameBoard, CellState.X))
            return -100

        val oPotentialWins = countPotentialWins(gameBoard, CellState.O)
        val xPotentialWins = countPotentialWins(gameBoard, CellState.X)

        return oPotentialWins - xPotentialWins
    }

    fun makePredictions(gameState: Gamestate, depth: Int = 0, maxDepth: Int = 3): TreeNode<GameBoard> {
        val rootNode = TreeNode(gameState.gameBoard)

        if (depth >= maxDepth
            || checkWinner(gameState.gameBoard, CellState.X)
            || checkWinner(gameState.gameBoard, CellState.O)
        )
            return rootNode

        val currentPlayer = if (gameState.isPlayer) CellState.X else CellState.O
        val nextStateList = mutableListOf<Pair<GameBoard, Int>>()

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (gameState.gameBoard.getCellState(i, j) == CellState.EMPTY) {
                    val nextBoard = gameState.gameBoard.copy()
                    nextBoard.setCellState(i, j, currentPlayer)

                    val heuristicValue = calculateHeuristicValue(nextBoard)
                    nextStateList.add(Pair(nextBoard, heuristicValue))
                }
            }
        }

        nextStateList.sortByDescending { it.second }

        for ((nextBoard, heuristicValue) in nextStateList) {
            val nextState = Gamestate(!gameState.isPlayer, nextBoard)
            val childNode = makePredictions(nextState, depth + 1, maxDepth)
            childNode.heuristicValue = heuristicValue
            rootNode.addChild(childNode)
        }

        return rootNode
    }

    fun minimax(node: TreeNode<GameBoard>, depth: Int, alpha: Int, beta: Int, maximizingPlayer: Boolean): Int {
        var alphaLocal = alpha
        var betaLocal = beta

        if (depth == 0 || node.children.isEmpty())
            return node.heuristicValue

        if (maximizingPlayer) {
            var value = Int.MIN_VALUE
            for (child in node.children) {
                value = max(value, minimax(child, depth - 1, alphaLocal, betaLocal, false))
                alphaLocal = max(alphaLocal, value)
                if (betaLocal <= alphaLocal)
                    break
            }
            return value
        } else {
            var value = Int.MAX_VALUE
            for (child in node.children) {
                value = min(value, minimax(child, depth - 1, alphaLocal, betaLocal, true))
                betaLocal = min(betaLocal, value)
                if (betaLocal <= alphaLocal)
                    break
            }
            return value
        }
    }

    fun alphaBetaPruning(node: TreeNode<GameBoard>, depth: Int): TreeNode<GameBoard> {
        var bestValue = Int.MIN_VALUE
        var bestChild: TreeNode<GameBoard>? = null
        val alpha = Int.MIN_VALUE
        val beta = Int.MAX_VALUE
        for (child in node.children) {
            val value = minimax(child, depth - 1, alpha, beta, false)
            if (value > bestValue) {
                bestValue = value
                bestChild = child
            }
        }
        return bestChild ?: throw IllegalStateException("No valid moves found")
    }

}