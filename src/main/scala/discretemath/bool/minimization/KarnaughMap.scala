package discretemath.bool.minimization

import discretemath.bool.expression.forms.SumOfProductExpression
import discretemath.combination.GrayCode
import discretemath.structure.matrix.{ArrayMatrix, Matrix}

class KarnaughMap private(val rowsGrayCode: GrayCode, val columnsGrayCode: GrayCode, val table: Matrix[Boolean]) {

  override def toString: String = {
    val builder = new StringBuilder
    builder.append(" ".repeat(rowsGrayCode.getVariablesCount + 1))
    for (j <- 0 until columnsGrayCode.getCombinationsCount) {
      builder.append("|")
      val bitString = columnsGrayCode.get(j)
      builder.append(bitString)
    }
    builder.append("\n")
    for (i <- 0 until rowsGrayCode.getCombinationsCount) {
      builder.append(rowsGrayCode.get(i))
      builder.append(" ")
      for (j <- 0 until columnsGrayCode.getCombinationsCount) {
        builder.append("|")
        builder.append(if (table.get(i, j)) '1'
        else '0')
        builder.append(" ".repeat(columnsGrayCode.getVariablesCount - 1))
      }
      builder.append("\n")
    }
    builder.toString
  }
}

object KarnaughMap {

  def apply(expression: SumOfProductExpression): KarnaughMap = {
    val variablesCount = expression.variables.size
    val rowHeaders = GrayCode.forN(getBitCountForLeftSide(variablesCount))
    val columnHeaders = GrayCode.forN(getBitCountForRightSide(variablesCount))
    val table = buildTableFor(variablesCount)

    for (minterm <- expression.minterms) {
      val literalsSize = minterm.literals.size
      val rowBitString = minterm.subMinterm(0, rowHeaders.getVariablesCount).toBitString
      val columnBitString = minterm.subMinterm(rowHeaders.getVariablesCount, literalsSize).toBitString
      val row = rowHeaders.find(rowBitString)
      val column = columnHeaders.find(columnBitString)
      table.set(row, column, true)
    }
    new KarnaughMap(rowHeaders, columnHeaders, table)
  }

  private def buildTableFor(n: Int): Matrix[Boolean] = {
    val leftVariablesCount = getBitCountForLeftSide(n)
    val rightVariablesCount = getBitCountForRightSide(n)
    val rowsCount = 1 << leftVariablesCount
    val columnsCount = 1 << rightVariablesCount
    ArrayMatrix(rowsCount, columnsCount)
  }

  private def getBitCountForLeftSide(n: Int) = Math.floor(n / 2D).toInt

  private def getBitCountForRightSide(n: Int) = Math.ceil(n / 2D).toInt
}