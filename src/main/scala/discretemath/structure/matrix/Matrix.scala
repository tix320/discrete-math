package discretemath.structure.matrix

import discretemath.common.{Adder, Multiplier}

import scala.reflect.ClassTag;

trait Matrix[T] {

  def rowsCount: Int

  def columnsCount: Int

  def get(i: Int, j: Int): T

  def getRow(i: Int): IndexedSeq[T]

  def set(i: Int, j: Int, value: T): Unit

  def add(matrix: Matrix[T])(implicit adder: Adder[T], tag: ClassTag[T]): Matrix[T]

  def multiply(matrix: Matrix[T])(implicit multiplier: Multiplier[T], adder: Adder[T], tag: ClassTag[T]): Matrix[T]
}