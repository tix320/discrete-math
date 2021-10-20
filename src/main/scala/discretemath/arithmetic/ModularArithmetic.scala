package discretemath.arithmetic

import scala.annotation.tailrec

object ModularArithmetic {

  def exponentModulo(base: Int, exponent: Int, modulus: Int): Int = {
    if (modulus < 0) throw new IllegalArgumentException(s"Negative modulus: $modulus")

    if (modulus == 1) return 0

    if (exponent == 0) return 1

    if (exponent < 0) throw new UnsupportedOperationException("Negative exponent currently not supported")

    var result = 1
    var b = base % modulus
    var e = exponent

    while (e > 0) {

      if ((e & 1) == 1) { // last bit is 1
        result = (result * b) % modulus
      }

      b = (b * b) % modulus

      e = e >> 1
    }

    return result
  }

  def gcd(m: Long, n: Long): Long = {
    assert(m > 0)
    assert(n > 0)

    if (m == n) {
      return m
    }

    @tailrec
    def gcdRec(max: Long, min: Long): Long = {
      if (m == 1 || n == 1) {
        return 1
      }

      val mod = max % min

      if (mod == 0) {
        return min
      }

      return gcdRec(min, mod)
    }

    val (max, min) = if (m > n) (m, n) else (n, m)

    return gcdRec(max, min)
  }

  def lcm(m: Long, n: Long): Long = {
    val gcdValue = gcd(m, n)

    return (m * n) / gcdValue
  }

}
