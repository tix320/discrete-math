package discretemath.bool.expression.forms;

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.expression.atomic.BooleanVariable
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet
import discretemath.bool.operator.Operators.AND
import discretemath.common.BitString

import scala.collection.immutable.ListSet;

case class Minterm(literals: ListSet[Literal]) extends BooleanExpression {

  override def getVariables: Set[BooleanVariable] = literals.map(_.variable)

  @throws[VariableValueNotSpecifiedException]
  override def evaluate(arguments: Map[BooleanVariable, Boolean]): Boolean = {
    val values: Seq[Boolean] = literals.view.map(literal => literal.evaluate(arguments)).toSeq

    AND.evaluate(values)
  }

  override def expressViaOperators(operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression = AND.injectUsing(operatorsSet, literals.toSeq)

  override def minimize: BooleanExpression = this

  override def isSatisfiable: Boolean = true

  override def toString: String = literals.view.map(_.toString).mkString("")

  def subMinterm(fromIndex: Int, toIndex: Int): Minterm = Minterm(literals.slice(fromIndex, toIndex))

  def toBitString: BitString = {
    val literals = this.literals
    val literalsIterator = literals.iterator
    val bitString = BitString.forN(literals.size)
    for (i <- 0 until literals.size) {
      val literal = literalsIterator.next
      bitString.set(i, literal.isPositive)
    }
    bitString
  }
}