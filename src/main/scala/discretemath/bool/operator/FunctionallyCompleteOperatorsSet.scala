package discretemath.bool.operator

import discretemath.bool.operator.Operators._;

sealed abstract class FunctionallyCompleteOperatorsSet(operators: Operator*) {

  def contains(operator: Operator): Boolean = operators.contains(operator)
}

object FunctionallyCompleteOperatorsSet {

  case object AND_OR_NOT extends FunctionallyCompleteOperatorsSet(AND, OR, NOT)
  case object AND_NOT extends FunctionallyCompleteOperatorsSet(AND, NOT)
  case object OR_NOT extends FunctionallyCompleteOperatorsSet(OR, NOT)
  case object ONLY_NAND extends FunctionallyCompleteOperatorsSet(NAND)
  case object ONLY_NOR extends FunctionallyCompleteOperatorsSet(NOR)
}


