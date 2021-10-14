package discretemath.structure.set


import discretemath.structure.set.main.{BigDecimalRangeSet, BigIntegerRangeSet}
import discretemath.structure.set.range.decimal.BigDecimalRange
import discretemath.structure.set.range.integer.BigIntRange

object Sets {

  /**
   * {... ,−2, −1.4, 0, 0.3, 1, 2.4, ...} The set of all real numbers
   */
  val R = new BigDecimalRangeSet(BigDecimalRange.infinite)

  /**
   * { 0, 0.3, 1, 2.4, ...} The set of all real non-negative numbers
   */
  val R_NON_NEG = new BigDecimalRangeSet(BigDecimalRange >= 0)

  /**
   * {... ,−2,−1, 0, 1, 2, ...}, the set of all integers
   */
  val Z = new BigIntegerRangeSet(BigIntRange.infinite())

  /**
   * {1, 2, 3, ...}, the set of all positive integers
   */
  val Z_PLUS = new BigIntegerRangeSet(BigIntRange >= 1)

  /**
   * {0, 1, 2, 3, ...}, the set of all natural numbers
   */
  val N = new BigIntegerRangeSet(BigIntRange >= 0)
}