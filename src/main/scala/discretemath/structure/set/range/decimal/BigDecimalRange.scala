package discretemath.structure.set.range.decimal

import discretemath.structure.set.range._

case class BigDecimalRange(from: BigDecimal, to: BigDecimal, fromInclusive: Boolean, toInclusive: Boolean) extends Range[BigDecimal] {

  override def size: BigInt = -1

  override def contains(item: BigDecimal): Boolean = {
    if (item == from) {
      return fromInclusive
    } else if (item == to) {
      return toInclusive
    } else {
      return item > from && item < to
    }
  }
}


object BigDecimalRange {

  def >(from: BigDecimal): Range[BigDecimal] = FromXToInfinite(from, inclusive = false)

  def >=(from: BigDecimal): Range[BigDecimal] = FromXToInfinite(from, inclusive = true)

  def <(to: BigDecimal): Range[BigDecimal] = FromInfiniteToX(to, inclusive = false)

  def <=(to: BigDecimal): Range[BigDecimal] = FromInfiniteToX(to, inclusive = true)

  def infinite: Range[BigDecimal] = InfiniteRange.asInstanceOf[Range[BigDecimal]]

  def empty: Range[BigDecimal] = EmptyRange.asInstanceOf[Range[BigDecimal]]
}