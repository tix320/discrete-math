package discretemath.structure.set.range.integer

import discretemath.structure.set.range._

case class FromInfiniteToX(to: BigInt) extends Range[BigInt] {

  override def size: BigInt = -1

  override def contains(item: BigInt): Boolean = item <= to
}