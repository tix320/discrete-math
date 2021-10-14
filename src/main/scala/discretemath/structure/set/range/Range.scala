package discretemath.structure.set.range

trait Range[T] {

  /**
   * @return -1 when infinity
   */
  def size: BigInt

  final def isInfinite: Boolean = size == -1

  def contains(item: T): Boolean
}