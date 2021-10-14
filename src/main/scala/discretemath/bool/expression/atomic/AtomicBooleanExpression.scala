package discretemath.bool.expression.atomic

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet

trait AtomicBooleanExpression extends BooleanExpression {

  final override def expressViaOperators(operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression = this

  final override def minimize: BooleanExpression = this
}
