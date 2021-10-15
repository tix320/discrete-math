package discretemath.bool.minimization

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.expression.atomic.BooleanVariable
import discretemath.bool.expression.compound.{CompoundBooleanExpression, ExpressionTreeNode, OperatorNode, ValueNode}
import discretemath.bool.expression.forms.SumOfProductExpression
import discretemath.bool.operator.Operators.{AND, OR}
import discretemath.bool.table.{FunctionValue, TruthTable}
import discretemath.common.{BitString, MoreThanOneBitDifferenceException}
import discretemath.structure.matrix.{ArrayMatrix, Matrix}
import extension.Extensions.ArrayImprovements

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object McCluskey {

  def minimizeFromTruthTable(truthTable: TruthTable, variables: IndexedSeq[BooleanVariable]): BooleanExpression = {
    assert(truthTable.getFunctionsCount == 1)
    assert(truthTable.getVariablesCount == variables.size)

    val variablesCount = truthTable.getVariablesCount

    val implicantsTable = initImplicantsTable(variablesCount)

    val mintermNumbers = new ArrayBuffer[Long]()

    for (truthTableRow <- truthTable) {
      val functionValue = truthTableRow.getFunctionValue(0)

      if (functionValue != FunctionValue.FALSE) {
        val bitString = truthTableRow.getArgumentsBitString
        val bitCount = bitString.bitCount

        val nBitImplicantsList = implicantsTable(bitCount)

        val mintermNumber = bitString.asLong

        if (functionValue == FunctionValue.TRUE) {
          mintermNumbers.addOne(mintermNumber)
        }

        nBitImplicantsList(0).add(McCluskey.Implicant(bitString, Set(), Set(mintermNumber), functionValue == FunctionValue.DONT_CARE))
      }
    }

    minimize(variablesCount, mintermNumbers, implicantsTable, variables)
  }

  def minimizeSOPExpression(expression: SumOfProductExpression): BooleanExpression = {
    val bitStrings = expression.minterms.view.map(_.toBitString).toIndexedSeq

    val variablesCount = expression.variables.size

    val implicantsTable: IndexedSeq[IndexedSeq[mutable.LinkedHashSet[Implicant]]] = McCluskey.initImplicantsTable(variablesCount)

    val mintermNumbers = new ArrayBuffer[Long](bitStrings.size)

    for (bitString <- bitStrings) {
      val bitCount = bitString.bitCount

      val nBitImplicantsList = implicantsTable(bitCount)

      val mintermNumber = bitString.asLong

      mintermNumbers.addOne(mintermNumber)

      nBitImplicantsList(0).add(McCluskey.Implicant(bitString, Set(), Set(mintermNumber), dontCare = false))
    }
    McCluskey.minimize(variablesCount, mintermNumbers, implicantsTable, expression.variables)
  }

  private def minimize(variablesCount: Int, mintermNumbers: collection.IndexedSeq[Long], implicantsTable: IndexedSeq[IndexedSeq[mutable.LinkedHashSet[McCluskey.Implicant]]], variables: IndexedSeq[BooleanVariable]) = {
    val primeImplicants = new ArrayBuffer[McCluskey.Implicant]
    for (depthIndex <- 0 until variablesCount) {
      val used = new mutable.HashSet[McCluskey.Implicant]
      for (i <- 0 until implicantsTable.size - 1) {
        val group1 = implicantsTable(i)
        val group2 = implicantsTable(i + 1)
        val implicants1 = group1(depthIndex)
        val implicants2 = group2(depthIndex)
        for (implicant1 <- implicants1) {
          for (implicant2 <- implicants2) {
            try {
              val differBitIndex = getImplicantsDifferBitIndex(implicant1, implicant2)
              val dashesIndexes = new mutable.HashSet[Int]()
              dashesIndexes.addAll(implicant1.dashIndexes)
              dashesIndexes.add(differBitIndex)
              val newMintermNumbers = implicant1.mintermNumbers.concat(implicant2.mintermNumbers)
              val newImplicant = McCluskey.Implicant(implicant1.bitString, dashesIndexes.toSet, newMintermNumbers, implicant1.dontCare && implicant2.dontCare)
              group1(depthIndex + 1).add(newImplicant)
              used.add(implicant1)
              used.add(implicant2)
            } catch {
              case _: MoreThanOneBitDifferenceException =>
            }
          }
        }
      }

      for (group <- implicantsTable) {
        val implicants = group(depthIndex)
        for (implicant <- implicants) {
          if (!used.contains(implicant) && !implicant.dontCare) primeImplicants.addOne(implicant)
        }
      }
    }
    val primeImplicantsChart: Matrix[Boolean] = ArrayMatrix(primeImplicants.size, mintermNumbers.size)
    val mintermNumbersToIndexes = new mutable.HashMap[Long, Int]
    for (i <- mintermNumbers.indices) {
      val mintermNumber = mintermNumbers(i)
      mintermNumbersToIndexes.put(mintermNumber, i)
    }

    for (i <- primeImplicants.indices) {
      val primeImplicant = primeImplicants(i)
      for (mintermNumber <- primeImplicant.mintermNumbers) {
        val columnIndex: Option[Int] = mintermNumbersToIndexes.get(mintermNumber)
        if (columnIndex.isDefined) { // empty, when it don't care number
          primeImplicantsChart.set(i, columnIndex.get, true)
        }
      }
    }

    val rowsCoverage = new Array[Int](primeImplicantsChart.rowsCount)


    for (i <- 0 until primeImplicantsChart.rowsCount) {
      for (j <- 0 until primeImplicantsChart.columnsCount) {
        val cross = primeImplicantsChart.get(i, j)
        if (cross) rowsCoverage(i) += 1
      }
    }

    var coveredColumnsCount = 0

    val resultImplicants = new ArrayBuffer[McCluskey.Implicant]

    while (coveredColumnsCount != primeImplicantsChart.columnsCount) {
      val maxCoveredRowsIndexes: collection.IndexedSeq[Int] = rowsCoverage.allMax
      var maxCoveredRowIndex: Int = 0

      if (maxCoveredRowsIndexes.size == 1) {
        maxCoveredRowIndex = maxCoveredRowsIndexes(0)
      }
      else {
        var maxMintermsCountIndex: Int = maxCoveredRowsIndexes(0)
        for (i <- 1 until maxCoveredRowsIndexes.size) {
          val index: Int = maxCoveredRowsIndexes(i)
          val implicant: McCluskey.Implicant = primeImplicants(index)
          if (implicant.mintermNumbers.size > primeImplicants(maxMintermsCountIndex).mintermNumbers.size) {
            maxMintermsCountIndex = index
          }
        }
        maxCoveredRowIndex = maxMintermsCountIndex
      }

      assert(rowsCoverage(maxCoveredRowIndex) > 0)

      resultImplicants.addOne(primeImplicants(maxCoveredRowIndex))

      for (j <- 0 until primeImplicantsChart.columnsCount) {
        if (primeImplicantsChart.get(maxCoveredRowIndex, j)) {
          for (i <- 0 until primeImplicantsChart.rowsCount) {
            if (primeImplicantsChart.get(i, j)) {
              primeImplicantsChart.set(i, j, false)
              rowsCoverage(i) -= 1
            }
          }
          coveredColumnsCount += 1
        }
      }
      rowsCoverage(maxCoveredRowIndex) = 0
    }

    convertImplicantsSumToCompoundExpression(resultImplicants, variables)
  }

  private def initImplicantsTable(variablesCount: Int): IndexedSeq[IndexedSeq[mutable.LinkedHashSet[Implicant]]] = {
    return (0 until variablesCount + 1).map(_ => (0 until variablesCount).map(_ => new mutable.LinkedHashSet[McCluskey.Implicant]))
  }

  @throws[MoreThanOneBitDifferenceException]
  private def getImplicantsDifferBitIndex(implicant: McCluskey.Implicant, other: McCluskey.Implicant): Int = {
    if (implicant.dashIndexes == other.dashIndexes) {
      return implicant.bitString.getDifferBitIndex(other.bitString)
    }
    else {
      throw new MoreThanOneBitDifferenceException("Dashes")
    }
  }

  private def convertImplicantsSumToCompoundExpression(implicants: collection.IndexedSeq[McCluskey.Implicant], variables: collection.IndexedSeq[BooleanVariable]) = {
    val products = new Array[OperatorNode](implicants.size)

    for (i <- implicants.indices) {
      val implicant = implicants(i)
      val productNode = implicant.toOperatorNode(variables)
      products(i) = productNode
    }

    new CompoundBooleanExpression(OperatorNode(OR, products))
  }

  private case class Implicant(bitString: BitString, dashIndexes: Set[Int], mintermNumbers: Set[Long], dontCare: Boolean) {
    override def toString: String = {
      val builder = new StringBuilder(bitString.toString)
      for (dashIndex <- dashIndexes) {
        builder.setCharAt(dashIndex, '-')
      }

      builder.append(" ")
      builder.append(mintermNumbers)

      return builder.toString
    }

    def toOperatorNode(variables: collection.IndexedSeq[BooleanVariable]): OperatorNode = {
      val variableIterator = variables.iterator

      val nodes = new Array[ExpressionTreeNode](bitString.length - dashIndexes.size)

      var index = 0
      for (i <- 0 until bitString.length) {
        val variable = variableIterator.next
        if (!dashIndexes.contains(i)) {
          val positive = bitString.get(i)
          val valueNode = ValueNode(variable)
          if (positive) {
            nodes(index) = valueNode
            index += 1
          }
          else {
            nodes(index) = OperatorNode.not(valueNode)
            index += 1
          }
        }
      }

      return OperatorNode(AND, nodes)
    }
  }
}

