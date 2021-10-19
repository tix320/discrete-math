package discretemath.arithmetic

import extension.Extensions.IntImprovements

object IntegerRepresentation {

  def toBaseExpansion(n: Int, base: Int): String = {
    if (base < 2) throw new IllegalArgumentException(s"Invalid base: $base")

    if (n == 0 || n == 1) {
      return (n + 48).toChar.toString
    }

    val isNegative = n < 0

    val stringRepresentation = if (isNegative) Integer.toString(-n, base) else Integer.toString(n, base)

    val expansionBuilder = new StringBuilder

    if (isNegative) {
      expansionBuilder.append("-")
    }

    var exponent = stringRepresentation.length - 1

    val appendOperator = if (isNegative) " - " else " + "

    for (i <- 0 until stringRepresentation.length) {
      val c = stringRepresentation.charAt(i)

      val digit = c.toInt - 48

      if (digit != 0) {
        expansionBuilder.append(digit)

        if (exponent != 0) {
          expansionBuilder.append(" ⋅ ").append(base)
          if (exponent != 1) {
            val exponentPart = exponent.toExponentString
            expansionBuilder.append(exponentPart)
          }

        }

        expansionBuilder.append(appendOperator)
      }

      exponent -= 1
    }

    expansionBuilder.delete(expansionBuilder.length() - 3, expansionBuilder.length())

    return expansionBuilder.toString()
  }

  def toCantorExpansion(n: Int): String = {
    if (n == 0) {
      return "0 ⋅ 1!"
    }

    val isNegative = n < 0

    val number = if (isNegative) -n else n

    val expansionBuilder = new StringBuilder

    if (isNegative) {
      expansionBuilder.append("-")
    }

    var currentFactorial = 1

    var factorialIndex = 1
    while (currentFactorial <= number) {
      factorialIndex += 1
      currentFactorial = currentFactorial * factorialIndex
    }
    currentFactorial = currentFactorial / factorialIndex
    factorialIndex -= 1

    var sum = number

    val appendOperator = if (isNegative) " - " else " + "

    while (factorialIndex > 0) {
      val aₙ = sum / currentFactorial
      sum = sum - aₙ * currentFactorial

      if (aₙ != 0) {
        expansionBuilder.append(aₙ).append(" ⋅ ").append(factorialIndex).append("!").append(appendOperator)
      }

      currentFactorial /= factorialIndex
      factorialIndex -= 1
    }

    expansionBuilder.delete(expansionBuilder.length() - 3, expansionBuilder.length())

    return expansionBuilder.toString()
  }
}
