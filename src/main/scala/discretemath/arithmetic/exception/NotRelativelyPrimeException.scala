package discretemath.arithmetic.exception

class NotRelativelyPrimeException(numbers: Long*) extends RuntimeException(s"${numbers.mkString(",")}") {

}
