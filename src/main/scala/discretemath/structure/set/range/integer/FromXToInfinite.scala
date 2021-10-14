package discretemath.structure.set.range.integer

import discretemath.structure.set.range._

case class FromXToInfinite(from: BigInt) extends Range[BigInt] {

  override def size: BigInt = -1

  override def contains(item: BigInt): Boolean = item >= from
}