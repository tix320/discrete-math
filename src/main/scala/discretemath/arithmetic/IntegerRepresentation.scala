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
}
