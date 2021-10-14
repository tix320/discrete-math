package discretemath.bool.table;

sealed trait FunctionValue {
}

object FunctionValue {

  case object TRUE extends FunctionValue
  case object FALSE extends FunctionValue
  case object DONT_CARE extends FunctionValue
}

