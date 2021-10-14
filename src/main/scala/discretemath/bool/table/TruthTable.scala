package discretemath.bool.table

import discretemath.bool.table.FunctionValue.{FALSE, TRUE}
import discretemath.combination.BitCombinations
import discretemath.common.{BitString, Direction}
import discretemath.structure.matrix.{ArrayMatrix, Matrix}

import java.util.NoSuchElementException

case class TruthTable private(private val inputTable: Matrix[Boolean], private val functionValuesTable: Matrix[FunctionValue]) extends Iterable[TruthTableRow] {

  def setFunctionValue(functionIndex: Int, row: Int, value: FunctionValue): Unit = functionValuesTable.set(row, functionIndex, value)

  def setFunctionValue(functionIndex: Int, values: Array[FunctionValue]): Unit = {
    val functionValuesCount = 1 << getVariablesCount
    assert(values.length == functionValuesCount)
    for (i <- values.indices) {
      val value = values(i)
      functionValuesTable.set(i, functionIndex, value)
    }
  }

  def getVariablesCount: Int = inputTable.columnsCount

  def getFunctionsCount: Int = functionValuesTable.columnsCount

  override def iterator: Iterator[TruthTableRow] = new Iter

  override def toString: String = {
    val builder = new StringBuilder
    for (i <- 0 until inputTable.rowsCount) {
      for (j <- 0 until inputTable.columnsCount) {
        val b = inputTable.get(i, j)
        builder.append(if (b) 1 else 0).append("\t")
      }
      builder.append("|").append("\t")
      for (j <- 0 until functionValuesTable.columnsCount) {
        val b = functionValuesTable.get(i, j)
        b match {
          case FunctionValue.TRUE => builder.append(1)
          case FunctionValue.FALSE => builder.append(0)
          case FunctionValue.DONT_CARE => builder.append('x')
        }
        builder.append("\t")
      }

      builder.append("\n")
    }
    builder.toString
  }

  final private class Iter extends Iterator[TruthTableRow] {
    private var rowIndex = 0

    override def hasNext: Boolean = rowIndex < inputTable.rowsCount

    override def next: TruthTableRow = {
      if (rowIndex >= inputTable.rowsCount) throw new NoSuchElementException
      val currentRowIndex = rowIndex
      rowIndex += 1
      new TruthTableRow() {
        override def index: Int = return currentRowIndex

        override def arguments: IndexedSeq[Boolean] = inputTable.getRow(currentRowIndex)

        def argumentsCount: Int = return getVariablesCount

        override def functionsCount: Int = return getFunctionsCount

        override def getArgumentsBitString: BitString = {
          val bitString = BitString.forN(inputTable.columnsCount)
          for (j <- 0 until inputTable.columnsCount) {
            bitString.set(j, inputTable.get(currentRowIndex, j))
          }
          bitString
        }

        override def getArgument(index: Int): Boolean = {
          if (index > argumentsCount) throw new IndexOutOfBoundsException(index)
          inputTable.get(currentRowIndex, index)
        }

        override def getFunctionValue(index: Int): FunctionValue = {
          if (index > functionsCount) throw new IndexOutOfBoundsException(index)
          functionValuesTable.get(currentRowIndex, index)
        }
      }
    }
  }
}

object TruthTable {
  def forNDegree(n: Int): TruthTable = forNDegree(n, 0)

  def forNDegree(n: Int, spaceForFunctions: Int): TruthTable = {
    val rowsCount = 1 << n
    val table: Matrix[Boolean] = ArrayMatrix.create(rowsCount, n)
    val functionValueTable: Matrix[FunctionValue] = ArrayMatrix.create(rowsCount, spaceForFunctions)
    BitCombinations.nBitCombinations(n, table)
    new TruthTable(table, functionValueTable)
  }

  def forNDegreeWithAllFunctions(n: Int): TruthTable = {
    val rowsCount = 1 << n
    val functionsCount = rowsCount << n
    val truthTable = forNDegree(n, functionsCount)
    BitCombinations.nBitCombinations(rowsCount, truthTable.functionValuesTable,
      tableFillDirection = Direction.FROM_LEFT_TO_RIGHT, transformer = value => if (value) TRUE else FALSE);

    return truthTable
  }
}