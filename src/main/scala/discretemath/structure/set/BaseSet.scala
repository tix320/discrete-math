package discretemath.structure.set

trait BaseSet[T, CC[_], C] extends Iterable[T] {

  /**
   * Returns -1 when infinity
   */
  def cardinality: Int

  def contains(item: T): Boolean

  def unionWith[I <: C, R >: C](set: I): R

  def intersectWith[I <: C, R >: C](set: I): R

  def differenceWith[I <: C, R >: C](set: I): R

  def symmetricDifferenceWith[I <: C, R >: C](set: I): R

  def cartesianProductWith[P](set: CC[P]): CC[(T, P)]
}
