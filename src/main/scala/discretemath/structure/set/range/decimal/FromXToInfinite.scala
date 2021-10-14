package discretemath.structure.set.range.decimal


import discretemath.structure.set.range._

case class FromXToInfinite(from: BigDecimal, inclusive: Boolean) extends Range[BigDecimal] {

  override def size: BigInt = -1

  override def contains(item: BigDecimal): Boolean = {
    if (item == from) {
      return inclusive
    } else {
      return item > from
    }
  }
}