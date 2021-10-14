package discretemath.bool.expression

import discretemath.bool.expression.atomic.BooleanVariable
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet

trait BooleanExpression {
	
  @throws[VariableValueNotSpecifiedException]
  def evaluate(arguments: Map[BooleanVariable, Boolean]): Boolean

  @throws[UnsupportedOperationException]
  def expressViaOperators(operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression

  def minimize: BooleanExpression
}
