package discretemath.bool.expression.forms;

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.expression.atomic.BooleanVariable
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet
import discretemath.bool.operator.Operators.OR

import scala.collection.immutable.ListSet;

case class Maxterm(literals: ListSet[Literal]) extends BooleanExpression {

  override def getVariables: Set[BooleanVariable] = literals.map(_.variable)

  @throws[VariableValueNotSpecifiedException]
  override def evaluate(arguments: Map[BooleanVariable, Boolean]): Boolean = {
    val values: Seq[Boolean] = literals.view.map(literal => literal.evaluate(arguments)).toSeq

    OR.evaluate(values)
  }

  override def expressViaOperators(operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression = OR.injectUsing(operatorsSet, literals.toSeq)

  override def minimize: BooleanExpression = this

  override def isSatisfiable: Boolean = true

  override def toString: String = literals.view.map(_.toString).mkString("+")
}