package discretemath.structure.matrix

import java.util.Objects
import scala.reflect.ClassTag

case class ArrayMatrix[T] private(table: Array[Array[T]]) extends Matrix[T] {
  override def rowsCount: Int = table.length

  override def columnsCount: Int = table(0).length

  override def get(i: Int, j: Int): T = table(i)(j)

  override def getRow(i: Int): IndexedSeq[T] = table(i)

  override def set(i: Int, j: Int, value: T): Unit = table(i)(j) = value

  override def add(matrix: Matrix[T]): Matrix[T] = ??? //TODO

  override def multiply(matrix: Matrix[T]): Matrix[T] = ??? //TODO

  override def transpose: Matrix[T] = ??? //TODO
}

object ArrayMatrix {
  def from[T](table: Array[Array[T]]): ArrayMatrix[T] = {
    Objects.requireNonNull(table(0), "Matrix table's row must not be null")
    val firstRowLength = table(0).length
    for (row <- table) {
      Objects.requireNonNull(row, "Matrix table's row must not be null")
      if (row.length != firstRowLength) throw new IllegalArgumentException("Matrix table's rows must be same size")
    }
    new ArrayMatrix[T](table)
  }

  def create[T: ClassTag](n: Int, m: Int): ArrayMatrix[T] = {
    val table = Array.ofDim[T](n, m)
    new ArrayMatrix[T](table)
  }
}