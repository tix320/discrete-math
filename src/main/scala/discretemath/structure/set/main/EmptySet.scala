package discretemath.structure.set.main

object EmptySet extends AbstractSet[Nothing] {

  override def cardinality = 0

  override def contains(item: Nothing) = false

  override def unionWith[I <: Set[Nothing], R >: Set[Nothing]](set: I): R = set

  override def intersectWith[I <: Set[Nothing], R >: Set[Nothing]](set: I): R = this

  override def differenceWith[I <: Set[Nothing], R >: Set[Nothing]](set: I): R = this

  override def symmetricDifferenceWith[I <: Set[Nothing], R >: Set[Nothing]](set: I): R = ???

  override def cartesianProductWith[P](set: Set[P]): Set[(Nothing, P)] = ???

  override def iterator: Iterator[Nothing] = Iterator.empty
}
