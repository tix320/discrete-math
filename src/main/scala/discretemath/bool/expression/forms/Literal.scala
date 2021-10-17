package discretemath.bool.expression.forms

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.expression.atomic.BooleanVariable
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet
import discretemath.bool.operator.Operators.NOT;

case class Literal(variable: BooleanVariable, isPositive: Boolean) extends BooleanExpression {

  override def getVariables: Set[BooleanVariable] = Set(variable)

  @throws[VariableValueNotSpecifiedException]
  def evaluate(arguments: Map[BooleanVariable, Boolean]): Boolean = {
    val value = arguments.getOrElse(variable, throw new VariableValueNotSpecifiedException(variable))

    return if (isPositive) value else !value
  }

  override def expressViaOperators(operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression = {
    if (isPositive) {
      return this
    } else {
      return NOT.injectUsing(operatorsSet, Seq(variable))
    }
  }

  override def minimize: BooleanExpression = this

  override def isSatisfiable: Boolean = true

  override def toString: String = if (isPositive) variable.toString else "~" + variable.toString
}

object Literal {

  def +(variable: BooleanVariable): Literal = new Literal(variable, true)

  def ~(variable: BooleanVariable): Literal = new Literal(variable, false)
}