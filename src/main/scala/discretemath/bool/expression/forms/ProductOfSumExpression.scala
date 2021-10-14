package discretemath.bool.expression.forms

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.expression.atomic.{BooleanVariable, BooleanVariables}
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet
import discretemath.bool.operator.Operators.AND
import discretemath.bool.table.{FunctionValue, TruthTable}

import scala.collection.immutable.ListSet

class ProductOfSumExpression private(val variables: Seq[BooleanVariable], val maxterms: ListSet[Maxterm]) extends BooleanExpression {

  @throws[VariableValueNotSpecifiedException]
  override def evaluate(arguments: Map[BooleanVariable, Boolean]): Boolean = {
    val results: Seq[Boolean] = maxterms.view.map(_.evaluate(arguments)).toSeq

    return AND.evaluate(results)
  }

  override def expressViaOperators(operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression = AND.injectUsing(operatorsSet, maxterms.toSeq)

  override def minimize: BooleanExpression = ???

  override def toString: String = maxterms.view.map(maxterm => "(" + maxterm + ")").mkString("")
}

object ProductOfSumExpression {

  def apply(truthTable: TruthTable): ProductOfSumExpression = {
    val variables = BooleanVariables.getVariables(truthTable.getVariablesCount)
    return apply(truthTable, variables)
  }

  def apply(truthTable: TruthTable, variables: IndexedSeq[BooleanVariable]): ProductOfSumExpression = {
    val functionsCount = truthTable.getFunctionsCount
    if (functionsCount != 1) {
      throw new IllegalArgumentException("Truth table must be contain exactly one function")
    }

    val variablesCount = truthTable.getVariablesCount
    assert(variables.length == variablesCount, s"Variable list size expected: $variablesCount but was ${variables.length}")

    val maxterms: ListSet[Maxterm] = truthTable.view
      .filter(row => row.getFunctionValue(0) == FunctionValue.FALSE)
      .map(row => {
        val literals: ListSet[Literal] = variables.view
          .zip(row.arguments)
          .map(tuple => Literal(tuple._1, !tuple._2))
          .to(ListSet)

        Maxterm(literals)
      }).to(ListSet)

    return new ProductOfSumExpression(variables, maxterms)
  }
}