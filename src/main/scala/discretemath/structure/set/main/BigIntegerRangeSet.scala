package discretemath.structure.set.main

import discretemath.structure.exception.TooLargeSetException
import discretemath.structure.set.RangeSet
import discretemath.structure.set.range._

import scala.math.BigInt

class BigIntegerRangeSet(val range: Range[BigInt]) extends AbstractSet[BigInt] with RangeSet[BigInt] {
  override def cardinality: Int = {
    if (range.size > Int.MaxValue) throw new TooLargeSetException
    range.size.intValue
  }

  override def contains(item: BigInt): Boolean = range.contains(item)

  override def unionWith[I <: Set[BigInt], R >: Set[BigInt]](set: I): R = ??? // TODO

  override def intersectWith[I <: Set[BigInt], R >: Set[BigInt]](set: I): R = ???

  override def differenceWith[I <: Set[BigInt], R >: Set[BigInt]](set: I): R = ???

  override def symmetricDifferenceWith[I <: Set[BigInt], R >: Set[BigInt]](set: I): R = ???

  override def cartesianProductWith[P](set: Set[P]): Set[(BigInt, P)] = ???

  override def iterator: Iterator[BigInt] = ???
}