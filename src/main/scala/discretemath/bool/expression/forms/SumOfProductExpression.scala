package discretemath.bool.expression.forms

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.expression.atomic.BooleanVariable
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException
import discretemath.bool.minimization.McCluskey
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet
import discretemath.bool.operator.Operators.OR
import discretemath.bool.table.{FunctionValue, TruthTable}

import scala.collection.immutable.ListSet

class SumOfProductExpression private(val variables: IndexedSeq[BooleanVariable], val minterms: Set[Minterm]) extends BooleanExpression {

  override def getVariables: Set[BooleanVariable] = minterms.flatMap(_.getVariables)

  @throws[VariableValueNotSpecifiedException]
  def evaluate(arguments: Map[BooleanVariable, Boolean]): Boolean = {
    val functionValuesCount = 1 << variables.length

    return minterms.size match {
      case 0 => false
      case `functionValuesCount` => true
      case _ => OR.evaluate(minterms.view.map(_.evaluate(arguments)).toSeq)
    }
  }

  def expressViaOperators(operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression = OR.injectUsing(operatorsSet, minterms.toSeq)

  override def minimize: BooleanExpression = McCluskey.minimizeSOPExpression(this)

  override def isSatisfiable: Boolean = {
    return minterms.size match {
      case 0 => false
      case _ => true
    }
  }

  override def toString: String = minterms.view.map(_.toString).mkString(" + ")
}

object SumOfProductExpression {

  def apply(truthTable: TruthTable): SumOfProductExpression = {
    val variables = BooleanVariable.getN(truthTable.getVariablesCount)
    return apply(truthTable, variables)
  }

  def apply(truthTable: TruthTable, variables: IndexedSeq[BooleanVariable]): SumOfProductExpression = {
    val functionsCount = truthTable.getFunctionsCount
    if (functionsCount != 1) {
      throw new IllegalArgumentException("Truth table must be contain exactly one function")
    }

    val variablesCount = truthTable.getVariablesCount
    assert(variables.length == variablesCount, s"Variable list size expected: $variablesCount but was ${variables.length}")

    val minterms: ListSet[Minterm] = truthTable.view
      .filter(row => row.getFunctionValue(0) == FunctionValue.TRUE)
      .map(row => {
        val literals: ListSet[Literal] = variables.view
          .zip(row.arguments)
          .map(tuple => Literal(tuple._1, tuple._2))
          .to(ListSet)

        Minterm(literals)
      }).to(ListSet)

    return new SumOfProductExpression(variables, minterms)
  }
}