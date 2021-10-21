package discretemath.arithmetic.modular.congruence

case class LinearCongruence(congruent: Long, modulus: Long) {
  assert(modulus >= 0)

  def normalize(): LinearCongruence = {
    return LinearCongruence(congruent % modulus, modulus)
  }
}
