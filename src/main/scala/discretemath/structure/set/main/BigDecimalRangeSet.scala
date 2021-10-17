package discretemath.structure.set.main

import discretemath.structure.exception.UncountableSetException
import discretemath.structure.set.RangeSet
import discretemath.structure.set.range._

import scala.math.BigDecimal

class BigDecimalRangeSet(val range: Range[BigDecimal]) extends AbstractSet[BigDecimal] with RangeSet[BigDecimal] {

  override def cardinality: Int = -1

  override def contains(item: BigDecimal): Boolean = range.contains(item)

  override def unionWith[I <: Set[BigDecimal], R >: Set[BigDecimal]](set: I): R = ??? // TODO

  override def intersectWith[I <: Set[BigDecimal], R >: Set[BigDecimal]](set: I): R = ???

  override def differenceWith[I <: Set[BigDecimal], R >: Set[BigDecimal]](set: I): R = ???

  override def symmetricDifferenceWith[I <: Set[BigDecimal], R >: Set[BigDecimal]](set: I): R = ???

  override def cartesianProductWith[P](set: Set[P]): Set[(BigDecimal, P)] = ???

  override def iterator = throw new UncountableSetException

  override def powerSet: Set[Set[BigDecimal]] = ???
}
