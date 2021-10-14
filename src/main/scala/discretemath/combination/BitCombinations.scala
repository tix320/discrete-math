package discretemath.combination

import discretemath.common.Direction
import discretemath.common.Direction.FROM_UP_TO_DOWN
import discretemath.structure.matrix.{ArrayMatrix, Matrix}

object BitCombinations {

  def nBitCombinations(n: Int): Matrix[Boolean] = {
    val rowsCount = 1 << n
    val table: Matrix[Boolean] = ArrayMatrix.create(rowsCount, n)
    nBitCombinations(n, table, 0, 0, Direction.FROM_UP_TO_DOWN, identity)
    table
  }

  def nBitCombinations(n: Int, table: Matrix[Boolean]): Unit = nBitCombinations(n, table, transformer = identity)

  def nBitCombinations[T](n: Int, table: Matrix[T], startRow: Int = 0, startColumn: Int = 0, tableFillDirection: Direction = FROM_UP_TO_DOWN, transformer: Boolean => T): Unit = {
    val valueSetter = getTableValueSetter(tableFillDirection, startRow, startColumn, transformer)
    val rowsCount = 1 << n
    for (i <- 0 until rowsCount) {
      for (j <- 0 until n) {
        val value = ((i >> j) & 1) == 1
        valueSetter(table, i, n - 1 - j, value)
      }
    }
  }

  private def getTableValueSetter[T](direction: Direction, startRow: Int, startColumn: Int, transformer: Boolean => T) = direction match {
    case Direction.FROM_UP_TO_DOWN => (table: Matrix[T], i: Int, j: Int, value: Boolean) => table.set(startRow + i, startColumn + j, transformer(value))
    case Direction.FROM_LEFT_TO_RIGHT => (table: Matrix[T], i: Int, j: Int, value: Boolean) => table.set(startRow + j, startColumn + i, transformer(value))
  }
}