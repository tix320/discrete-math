package discretemath.structure.set.range.integer

import discretemath.structure.set.range._

case class BigIntRange(from: BigInt, to: BigInt) extends Range[BigInt] {

  override def size: BigInt = to - from + 1

  override def contains(item: BigInt): Boolean = item >= from && item <= to
}


object BigIntRange {

  def >(from: BigInt): Range[BigInt] = FromXToInfinite(from + 1)

  def >=(from: BigInt): Range[BigInt] = FromXToInfinite(from)

  def <(to: BigInt): Range[BigInt] = FromInfiniteToX(to - 1)

  def <=(to: BigInt): Range[BigInt] = FromInfiniteToX(to)

  def infinite(): Range[BigInt] = InfiniteRange.asInstanceOf[Range[BigInt]]

  def empty(): Range[BigInt] = EmptyRange.asInstanceOf[Range[BigInt]]
}