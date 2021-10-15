package discretemath.structure.matrix

import discretemath.common.{Adder, Multiplier}

import java.util
import java.util.Objects
import scala.reflect.ClassTag

class ArrayMatrix[T] private(val table: Array[Array[T]]) extends Matrix[T] with Equals {
  override def rowsCount: Int = table.length

  override def columnsCount: Int = if (table.length == 0) 0 else table(0).length

  override def get(i: Int, j: Int): T = table(i)(j)

  override def getRow(i: Int): IndexedSeq[T] = table(i)

  override def set(i: Int, j: Int, value: T): Unit = table(i)(j) = value

  override def add(matrix: Matrix[T])(implicit adder: Adder[T], tag: ClassTag[T]): Matrix[T] = {
    assert(this.rowsCount == matrix.rowsCount)
    assert(this.columnsCount == matrix.columnsCount)

    val resultMatrix: ArrayMatrix[T] = ArrayMatrix(rowsCount, matrix.columnsCount)

    for (i <- 0 until rowsCount) {
      for (j <- 0 until matrix.columnsCount) {
        val sum = adder.add(this.get(i, j), matrix.get(i, j))
        resultMatrix.set(i, j, sum)
      }
    }

    resultMatrix
  }

  override def multiply(matrix: Matrix[T])(implicit multiplier: Multiplier[T], adder: Adder[T], tag: ClassTag[T]): Matrix[T] = {
    assert(this.columnsCount == matrix.rowsCount)

    val resultMatrix: ArrayMatrix[T] = ArrayMatrix(rowsCount, matrix.columnsCount)

    for (i <- 0 until rowsCount) {
      for (j2 <- 0 until matrix.columnsCount) {
        var sum = multiplier.multiply(this.get(i, 0), matrix.get(0, j2))
        for (j <- 1 until columnsCount) {
          val product = multiplier.multiply(this.get(i, j), matrix.get(j, j2))
          sum = adder.add(sum, product)
        }

        resultMatrix.set(i, j2, sum)
      }
    }

    resultMatrix
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Matrix[_]]

  override def equals(that: Any): Boolean = {
    that match {
      case matrix: ArrayMatrix[T] => util.Arrays.deepEquals(this.table.asInstanceOf[Array[Object]], matrix.table.asInstanceOf[Array[Object]])
      case _ => false
    }
  }

  override def hashCode(): Int = util.Arrays.deepHashCode(this.table.asInstanceOf[Array[Object]])
}

object ArrayMatrix {

  def apply[T](table: Array[Array[T]]): ArrayMatrix[T] = {
    Objects.requireNonNull(table(0), "Matrix table's row must not be null")
    val firstRowLength = table(0).length
    for (row <- table) {
      Objects.requireNonNull(row, "Matrix table's row must not be null")
      if (row.length != firstRowLength) throw new IllegalArgumentException("Matrix table's rows must be same size")
    }
    new ArrayMatrix[T](table)
  }

  def apply[T: ClassTag](n: Int, m: Int): ArrayMatrix[T] = {
    val table = Array.ofDim[T](n, m)
    new ArrayMatrix[T](table)
  }
}