import kotlin.test.Test
import kotlin.test.assertEquals

import com.sidapps.tictactoeai.data.GameBoard
import com.sidapps.tictactoeai.data.CellState
import com.sidapps.tictactoeai.GameLogic

class GameLogicTest {
    @Test
    fun testCountPotentialWins() {
        val gameBoard = GameBoard(arrayOf(
            arrayOf(CellState.X, CellState.EMPTY, CellState.O),
            arrayOf(CellState.EMPTY, CellState.X, CellState.EMPTY),
            arrayOf(CellState.O, CellState.EMPTY, CellState.O)
        ))

        assertEquals(1, GameLogic.countPotentialWins(gameBoard, CellState.X))
        assertEquals(3, GameLogic.countPotentialWins(gameBoard, CellState.O))

        // empty board test
        val emptyGameBoard = GameBoard()
        assertEquals(0, GameLogic.countPotentialWins(emptyGameBoard, CellState.X))
        assertEquals(0, GameLogic.countPotentialWins(emptyGameBoard, CellState.O))
    }

    @Test
    fun testCheckWinner() {
        // Test where either X or O is winning
        val xWinningGameBoard = GameBoard(arrayOf(
            arrayOf(CellState.X, CellState.X, CellState.X),
            arrayOf(CellState.EMPTY, CellState.EMPTY, CellState.EMPTY),
            arrayOf(CellState.EMPTY, CellState.EMPTY, CellState.EMPTY)
        ))
        val oWinningGameBoard = GameBoard(arrayOf(
            arrayOf(CellState.O, CellState.EMPTY, CellState.EMPTY),
            arrayOf(CellState.O, CellState.EMPTY, CellState.EMPTY),
            arrayOf(CellState.O, CellState.EMPTY, CellState.EMPTY)
        ))
        assertEquals(true, GameLogic.checkWinner(xWinningGameBoard, CellState.X))
        assertEquals(true, GameLogic.checkWinner(oWinningGameBoard, CellState.O))

        // Test where nobody is winning
        val nonWinningGameBoard = GameBoard(arrayOf(
            arrayOf(CellState.X, CellState.O, CellState.X),
            arrayOf(CellState.O, CellState.X, CellState.O),
            arrayOf(CellState.O, CellState.X, CellState.EMPTY)
        ))
        assertEquals(false, GameLogic.checkWinner(nonWinningGameBoard, CellState.X))
        assertEquals(false, GameLogic.checkWinner(nonWinningGameBoard, CellState.O))
    }
}