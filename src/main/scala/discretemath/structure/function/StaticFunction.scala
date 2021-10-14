package discretemath.structure.function

import discretemath.structure.exception.NotInvertibleException
import discretemath.structure.set.main._
import discretemath.structure.set.multi.{MultiSet, SimpleMultiSet}

class StaticFunction[T, R] private(val mapping: Map[T, R]) extends AbstractFunction[T, R] {
  val domain: Set[T] = SimpleSet(mapping.keySet)
  val codomain: Set[R] = SimpleSet(mapping.values)

  override def range: Set[R] = codomain

  override def multiSetRange: MultiSet[R] = {
    val rangeSet: Map[R, Int] = mapping.view.groupMapReduce(entry => entry._2)(_ => 1)((v1, v2) => v1 + v2)

    SimpleMultiSet(rangeSet)
  }

  override def graph: Set[(T, R)] = {
    val pairSet = mapping.map(identity).toSet
    SimpleSet(pairSet)
  }

  override def isOnto = true

  @throws[NotInvertibleException]
  override def getInverse: Function[R, T] = {
    if (!isOnto || !isOneToOne) throw new NotInvertibleException

    val reverseMapping = mapping.map(_.swap)

    new StaticFunction[R, T](reverseMapping)
  }

  override protected def applyWithoutCheckInput(input: T): R =
    mapping.getOrElse(input, throw new IllegalStateException("Result for input [%s] is not defined".format(input)))
}

object StaticFunction {
  def apply[T, R](mapping: Map[T, R]) = new StaticFunction[T, R](mapping)
}