package discretemath.bool.expression

import discretemath.bool.expression.atomic.BooleanVariable
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException
import discretemath.bool.operator.{FunctionallyCompleteOperatorsSet, Operators}

trait BooleanExpression {

  @throws[VariableValueNotSpecifiedException]
  def evaluate(arguments: Map[BooleanVariable, Boolean]): Boolean

  @throws[UnsupportedOperationException]
  def expressViaOperators(operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression

  def minimize: BooleanExpression

  // Building

  final def *(expression: BooleanExpression): BooleanExpression = Operators.AND.inject(Seq(this, expression))

  final def +(expression: BooleanExpression): BooleanExpression = Operators.OR.inject(Seq(this, expression))

  final def ^(expression: BooleanExpression): BooleanExpression = Operators.XOR.inject(Seq(this, expression))

  final def ↑(expression: BooleanExpression): BooleanExpression = Operators.NAND.inject(Seq(this, expression))

  final def ↓(expression: BooleanExpression): BooleanExpression = Operators.NOR.inject(Seq(this, expression))

  final def ->(expression: BooleanExpression): BooleanExpression = Operators.CONDITIONAL.inject(Seq(this, expression))

  final def <->(expression: BooleanExpression): BooleanExpression = Operators.BICONDITIONAL.inject(Seq(this, expression))

  final def unary_~(): BooleanExpression = Operators.NOT.inject(Seq(this))
}
