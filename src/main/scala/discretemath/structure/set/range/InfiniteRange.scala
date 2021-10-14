package discretemath.structure.set.range

case object InfiniteRange extends Range[Nothing] {

  override def size: BigInt = -1

  override def contains(item: Nothing): Boolean = true
}