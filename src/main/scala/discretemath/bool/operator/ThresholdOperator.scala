package discretemath.bool.operator;

import discretemath.bool.expression.BooleanExpression

class ThresholdOperator(val threshold: Double, val weights: Array[Double]) extends Operator {

  def evaluate(values: Seq[Boolean]): Boolean = {
    assert(values.length == weights.length)

    val sum = weights.view.zip(values).filter(tuple => tuple._2).map(tuple => tuple._1).sum

    return sum >= threshold
  }

  override def inject(operands: Seq[BooleanExpression]): BooleanExpression = {
    assert(operands.length == weights.length)

    injectOperator(this, operands)
  }

  @throws[UnsupportedOperationException]
  def injectUsing(operatorsSet: FunctionallyCompleteOperatorsSet, operands: Seq[BooleanExpression]): BooleanExpression = {
    assert(operands.length == weights.length)

    val expressedExpressions = expressViaOperators(operatorsSet, operands)

    if (operatorsSet.contains(this)) {
      return inject(expressedExpressions)
    }

    throw new NotImplementedError()
  }

  def toString(operands: Seq[BooleanExpression]): String = {
    assert(weights.length == operands.length)

    buildNAryOperatorString(" 〶 ", operands)
  }
}