package discretemath.structure.set.multi

import discretemath.structure.set.main.Set

object EmptyMultiSet extends AbstractMultiSet[Nothing] {

  override def cardinality = 0

  override def contains(item: Nothing) = false

  override def unionWith[I <: MultiSet[Nothing], R >: MultiSet[Nothing]](set: I): R = set

  override def intersectWith[I <: MultiSet[Nothing], R >: MultiSet[Nothing]](set: I): R = this

  override def differenceWith[I <: MultiSet[Nothing], R >: MultiSet[Nothing]](set: I): R = this

  override def sumWith(set: MultiSet[Nothing]): MultiSet[Nothing] = set

  override def symmetricDifferenceWith[I <: MultiSet[Nothing], R >: MultiSet[Nothing]](set: I): R = set

  override def cartesianProductWith[P](set: MultiSet[P]): MultiSet[(Nothing, P)] = this.asInstanceOf[MultiSet[(Nothing, P)]]

  override def mode: Option[Nothing] = None

  override def itemCount(item: Nothing): Int = 0

  override def iterator: Iterator[Nothing] = Iterator.empty

  override def withCountIterator: Iterator[(Nothing, Int)] = Iterator.empty
}
