package discretemath.structure.set.fuzzy


class StaticFuzzySet[T] private(private val membershipDegreeMap: Map[T, BigDecimal]) extends FuzzySet[T] {
  membershipDegreeMap.values.foreach(value => assert(value >= 0 && value <= 1))

  override def cardinality: Int = membershipDegreeMap.size

  override def contains(item: T): Boolean = membershipDegreeMap.contains(item)

  override def membershipDegree(item: T): BigDecimal = membershipDegreeMap.getOrElse(item, 0)

  override def unionWith(set: FuzzySet[T]): FuzzySet[T] = {
    val resultMap: Map[T, BigDecimal] = membershipDegreeMap.keySet.view.concat(set.iterator)
      .map((k: T) => (k, this.membershipDegree(k) max set.membershipDegree(k)))
      .toMap

    new StaticFuzzySet(resultMap)
  }

  override def intersectWith(set: FuzzySet[T]): FuzzySet[T] = {
    val resultMap: Map[T, BigDecimal] = membershipDegreeMap.keySet.view.concat(set.iterator)
      .filter((k: T) => this.membershipDegreeMap.contains(k) && set.contains(k))
      .map((k: T) => (k, this.membershipDegree(k) min set.membershipDegree(k)))
      .toMap

    new StaticFuzzySet(resultMap)
  }

  override def complement: FuzzySet[T] = StaticFuzzySet(membershipDegreeMap.map(entry => (entry._1, 1 - entry._2)))

  override def iterator: Iterator[T] = membershipDegreeMap.keySet.iterator

  def canEqual(other: Any): Boolean = other.isInstanceOf[StaticFuzzySet[_]]

  override def equals(other: Any): Boolean = other match {
    case that: StaticFuzzySet[T] =>
      (that canEqual this) &&
        membershipDegreeMap == that.membershipDegreeMap
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(membershipDegreeMap)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object StaticFuzzySet {

  def apply[T](membershipDegreeMap: Map[T, BigDecimal]): StaticFuzzySet[T] = return new StaticFuzzySet[T](membershipDegreeMap)

}