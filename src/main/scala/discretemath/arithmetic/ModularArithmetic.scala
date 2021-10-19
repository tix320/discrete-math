package discretemath.arithmetic

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


}
