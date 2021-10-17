package discretemath.structure.set.main

import scala.collection.immutable


class SimpleSet[T] private(val set: immutable.Set[T]) extends AbstractSet[T] {

  override def cardinality: Int = set.size

  override def contains(item: T): Boolean = set.contains(item)

  override def unionWith[I <: Set[T], R >: Set[T]](set: I): R = SimpleSet(this.set.concat(set))

  override def intersectWith[I <: Set[T], R >: Set[T]](set: I): R = SimpleSet(this.set.view.filter(item => set.contains(item)).toSet)

  override def differenceWith[I <: Set[T], R >: Set[T]](set: I): R = SimpleSet(this.set.view.filter(item => !set.contains(item)).toSet)

  override def symmetricDifferenceWith[I <: Set[T], R >: Set[T]](set: I): R = unionWith(set).differenceWith(intersectWith(set))

  override def cartesianProductWith[P](set: Set[P]): Set[(T, P)] = SimpleSet(view.flatMap(x => set.map(y => (x, y))).toSet)

  override def powerSet: Set[Set[T]] = SimpleSet(set.subsets().map(subset => SimpleSet(subset)).toSet).asInstanceOf[Set[Set[T]]]

  override def iterator: Iterator[T] = set.iterator

  override def toString: String = set.toString
}

object SimpleSet {
  def apply[T](set: immutable.Set[T]): SimpleSet[T] = new SimpleSet[T](set)

  def apply[T](items: Iterable[T]): SimpleSet[T] = apply(items.toSet)

  def apply[T](items: T*): SimpleSet[T] = apply(immutable.Set(items: _*))
}
