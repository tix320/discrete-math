package discretemath.structure.set.fuzzy

trait FuzzySet[T] extends Iterable[T] {

  def cardinality: Int

  def contains(item: T): Boolean

  def membershipDegree(item: T): BigDecimal

  def unionWith(set: FuzzySet[T]): FuzzySet[T]

  def intersectWith(set: FuzzySet[T]): FuzzySet[T]

  def complement: FuzzySet[T]
}
