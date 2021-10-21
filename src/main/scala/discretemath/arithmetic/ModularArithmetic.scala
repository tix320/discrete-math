package discretemath.arithmetic

import discretemath.arithmetic.exception.NotRelativelyPrimeException
import discretemath.arithmetic.modular.congruence.LinearCongruence

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

  def bezoutCoefficients(n: Long, m: Long): (Long, Long, Long) = {
    assert(m > 0)
    assert(n > 0)

    if (m == n) {
      return (m, 0, 1)
    }

    var (previousReminder, currentReminder) = if (m > n) (m, n) else (n, m)

    var previousLeftCoff = 1L
    var currentLeftCoff = 0L

    var previousRightCoff = 0L
    var currentRightCoff = 1L

    while (currentReminder != 0) {
      val q = previousReminder / currentReminder
      val nextReminder = previousReminder % currentReminder
      val nextLeftConf = previousLeftCoff - (q * currentLeftCoff)
      val nextRightConf = previousRightCoff - (q * currentRightCoff)

      previousReminder = currentReminder
      previousLeftCoff = currentLeftCoff
      previousRightCoff = currentRightCoff

      currentReminder = nextReminder
      currentLeftCoff = nextLeftConf
      currentRightCoff = nextRightConf
    }

    if (n > m) {
      return (previousReminder, previousLeftCoff, previousRightCoff)
    } else {
      return (previousReminder, previousRightCoff, previousLeftCoff)
    }
  }

  def inverseModulo(a: Long, m: Long): Long = {
    val (gcd, coff, _) = bezoutCoefficients(a, m)
    if (gcd != 1) {
      throw new NotRelativelyPrimeException(a, m)
    }

    return if (coff < 0) coff + m else coff
  }

  /**
   * @throws NotRelativelyPrimeException when `notRelativelyPrime` is false and any two of moduli not relatively prime
   */
  def solveCongruencesSystem(congruences: IndexedSeq[LinearCongruence], notRelativelyPrime: Boolean = false): Long = {

    val congs = if (notRelativelyPrime) {
      congruences.view
        .flatMap(cong => PrimeNumbers.factorsOf(cong.modulus)
          .map(modulus => LinearCongruence(cong.congruent, modulus).normalize()))
        .toIndexedSeq.distinct
    } else {
      congruences
    }

    val M = congs.view.map(cong => cong.modulus).product

    var sum = 0L
    for (i <- congs.indices) {
      val congruence = congs(i)
      val congruent = congruence.congruent
      val bigModulus = M / congruence.modulus
      val inverse = inverseModulo(bigModulus, congruence.modulus)

      sum += congruent * bigModulus * inverse
    }

    return sum % M
  }
}
