package discretemath.structure.matrix;

trait Matrix[T] {

  def rowsCount: Int

  def columnsCount: Int

  def get(i: Int, j: Int): T

  def getRow(i: Int): IndexedSeq[T]

  def set(i: Int, j: Int, value: T): Unit

  def add(matrix: Matrix[T]): Matrix[T]

  def multiply(matrix: Matrix[T]): Matrix[T]

  def transpose: Matrix[T]
}