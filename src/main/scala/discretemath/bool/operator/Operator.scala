package discretemath.bool.operator

import discretemath.bool.expression.BooleanExpression;


trait Operator {

  def evaluate(values: Seq[Boolean]): Boolean

  final def %(values: Boolean*): Boolean = evaluate(values)

  def inject(operands: Seq[BooleanExpression]): BooleanExpression

  @throws[UnsupportedOperationException]
  def injectUsing(operatorsSet: FunctionallyCompleteOperatorsSet, operands: Seq[BooleanExpression]): BooleanExpression

  def toString(operands: Seq[BooleanExpression]): String
}