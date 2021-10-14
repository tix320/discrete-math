package discretemath.structure.sequence

import discretemath.structure.exception.{NotInvertibleException, TermNotDefinedException}
import discretemath.structure.function.StaticFunction
import discretemath.structure.set.main._
import discretemath.structure.set.multi.SimpleMultiSet

import scala.collection.immutable

final class StaticSequence[T](val terms: IndexedSeq[T]) extends AbstractSequence[T](SimpleSet(immutable.Set(terms: _*))) {

  override def range: Set[T] = SimpleSet(immutable.Set(terms: _*))

  override def graph: Set[(BigInt, T)] = {
    val pairSet = terms.view.zipWithIndex.map(entry => new Tuple2[BigInt, T](entry._2, entry._1)).toSet
    SimpleSet(pairSet)
  }

  override def multiSetRange: SimpleMultiSet[T] = SimpleMultiSet(terms)

  override def isOnto = true

  @throws[NotInvertibleException]
  override def getInverse: StaticFunction[T, BigInt] = {
    if (!isOnto || !isOneToOne) throw new NotInvertibleException

    val reverseMapping = terms.view.zipWithIndex.map(entry => (entry._1, BigInt(entry._2))).toMap
    StaticFunction(reverseMapping)
  }

  override protected def applyWithoutCheckInput(input: BigInt): T = {
    if (input > terms.size) {
      throw new TermNotDefinedException(String.valueOf(input))
    }

    terms(input.intValue)
  }
}