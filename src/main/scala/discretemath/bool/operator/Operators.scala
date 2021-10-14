package discretemath.bool.operator

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.expression.compound.{CompoundBooleanExpression, ExpressionTreeNode, OperatorNode}
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet._

object Operators {

  object AND extends Operator {

    override def evaluate(values: Seq[Boolean]): Boolean = {
      assert(values.nonEmpty)

      for (value <- values) {
        if (!value) return false
      }
      true
    }

    override def inject(operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length >= 2)

      injectOperator(this, operands)
    }

    @throws[UnsupportedOperationException]
    override def injectUsing(operatorsSet: FunctionallyCompleteOperatorsSet, operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length >= 2)

      val expressedExpressions = expressViaOperators(operatorsSet, operands)

      if (operatorsSet.contains(this)) {
        return inject(expressedExpressions)
      }

      val operandNodes = ExpressionTreeNode.fromBooleanExpression(expressedExpressions)

      val rootNode = operatorsSet match {
        case OR_NOT => OperatorNode.not(OperatorNode(OR, operandNodes.map(node => OperatorNode.not(node))))
        case ONLY_NOR => OperatorNode(NOR, operandNodes.map(node => OperatorNode * (NOR, node, node)))
        case ONLY_NAND =>
          val nandCombined = OperatorNode(NAND, operandNodes)
          OperatorNode * (NAND, nandCombined, nandCombined)
        case _ => throw new UnsupportedOperationException
      }

      CompoundBooleanExpression(rootNode)
    }

    override def toString(operands: Seq[BooleanExpression]): String = {
      assert(operands.length >= 2)

      buildNAryOperatorString("", operands)
    }
  }

  object OR extends Operator {
    override def evaluate(values: Seq[Boolean]): Boolean = {
      assert(values.nonEmpty)

      for (value <- values) {
        if (value) return true
      }
      false
    }

    override def inject(operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length >= 2)

      injectOperator(this, operands)
    }

    @throws[UnsupportedOperationException]
    override def injectUsing(operatorsSet: FunctionallyCompleteOperatorsSet, operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length >= 2)

      val expressedExpressions = expressViaOperators(operatorsSet, operands)

      if (operatorsSet.contains(this)) {
        return inject(expressedExpressions)
      }

      val operandNodes = ExpressionTreeNode.fromBooleanExpression(expressedExpressions)

      val rootNode = operatorsSet match {
        case AND_NOT => OperatorNode.not(OperatorNode(AND, operandNodes.map(node => OperatorNode.not(node))))
        case ONLY_NAND => OperatorNode(NAND, operandNodes.map(node => OperatorNode * (NAND, node, node)))
        case ONLY_NOR =>
          val nandCombined = OperatorNode(NOR, operandNodes)
          OperatorNode * (NOR, nandCombined, nandCombined)
        case _ => throw new UnsupportedOperationException
      }

      CompoundBooleanExpression(rootNode)
    }

    override def toString(operands: Seq[BooleanExpression]): String = {
      assert(operands.length >= 2)

      buildNAryOperatorString(" + ", operands)
    }
  }

  object NOT extends Operator {

    override def evaluate(values: Seq[Boolean]): Boolean = {
      assert(values.length == 1)

      return !values.head
    }

    override def inject(operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length == 1)

      injectOperator(this, operands)
    }

    @throws[UnsupportedOperationException]
    override def injectUsing(operatorsSet: FunctionallyCompleteOperatorsSet, operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length == 1)

      val booleanExpression = operands.head.expressViaOperators(operatorsSet)

      if (operatorsSet.contains(this)) {
        return inject(Seq(booleanExpression))
      }

      val node = ExpressionTreeNode.fromBooleanExpression(booleanExpression)

      return operatorsSet match {
        case ONLY_NAND => new CompoundBooleanExpression(OperatorNode * (NAND, node, node))
        case ONLY_NOR => new CompoundBooleanExpression(OperatorNode * (NOR, node, node))
        case _ => throw new UnsupportedOperationException();
      }
    }

    override def toString(operands: Seq[BooleanExpression]): String = {
      assert(operands.length == 1)

      return "~" + wrapWithParentheses(operands.head)
    }
  }

  object NAND extends Operator {
    override def evaluate(values: Seq[Boolean]): Boolean = {
      assert(values.nonEmpty)

      for (value <- values) {
        if (!value) return true
      }
      return false
    }

    override def inject(operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length >= 2)

      injectOperator(this, operands)
    }

    @throws[UnsupportedOperationException]
    override def injectUsing(operatorsSet: FunctionallyCompleteOperatorsSet, operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length >= 2)

      val expressedExpressions = expressViaOperators(operatorsSet, operands)

      if (operatorsSet.contains(this)) {
        return inject(expressedExpressions)
      }

      throw new NotImplementedError()
    }

    override def toString(operands: Seq[BooleanExpression]): String = {
      assert(operands.length >= 2)

      return buildNAryOperatorString(" ↑ ", operands)
    }
  }

  object NOR extends Operator {
    override def evaluate(values: Seq[Boolean]): Boolean = {
      assert(values.nonEmpty)

      for (value <- values) {
        if (value) return false
      }
      return true
    }

    override def inject(operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length >= 2)

      injectOperator(this, operands)
    }

    @throws[UnsupportedOperationException]
    override def injectUsing(operatorsSet: FunctionallyCompleteOperatorsSet, operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length >= 2)

      val expressedExpressions = expressViaOperators(operatorsSet, operands)

      if (operatorsSet.contains(this)) {
        return inject(expressedExpressions)
      }

      throw new NotImplementedError()
    }

    override def toString(operands: Seq[BooleanExpression]): String = {
      assert(operands.length >= 2)

      return buildNAryOperatorString(" ↓ ", operands)
    }
  }

  object XOR extends Operator {
    override def evaluate(values: Seq[Boolean]): Boolean = {
      assert(values.nonEmpty)

      var result = values.head

      for (i <- 1 until values.length) {
        val value = values(i)
        result = result ^ value
      }

      return result
    }

    override def inject(operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length >= 2)

      injectOperator(this, operands)
    }

    @throws[UnsupportedOperationException]
    override def injectUsing(operatorsSet: FunctionallyCompleteOperatorsSet, operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length >= 2)

      val expressedExpressions = expressViaOperators(operatorsSet, operands)

      if (operatorsSet.contains(this)) {
        return inject(expressedExpressions)
      }

      throw new NotImplementedError()
    }

    override def toString(operands: Seq[BooleanExpression]): String = {
      assert(operands.length >= 2)

      return buildNAryOperatorString(" ⊕ ", operands);
    }
  }

  object CONDITIONAL extends Operator {
    override def evaluate(values: Seq[Boolean]): Boolean = {
      return values match {
        case Seq(hypothesis, conclusion) => !hypothesis || conclusion
        case _ => throw new AssertionError()
      }
    }

    override def inject(operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length == 2)

      injectOperator(this, operands)
    }

    @throws[UnsupportedOperationException]
    override def injectUsing(operatorsSet: FunctionallyCompleteOperatorsSet, operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length == 2)

      val expressedExpressions = expressViaOperators(operatorsSet, operands)

      if (operatorsSet.contains(this)) {
        return inject(expressedExpressions)
      }

      throw new NotImplementedError()
    }

    override def toString(operands: Seq[BooleanExpression]): String = {
      assert(operands.length == 2)

      return buildNAryOperatorString(" → ", operands)
    }
  }

  object BICONDITIONAL extends Operator {
    override def evaluate(values: Seq[Boolean]): Boolean = {
      return values match {
        case Seq(conclusion1, conclusion2) => conclusion1 == conclusion2
        case _ => throw new AssertionError()
      }
    }

    override def inject(operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length == 2)

      injectOperator(this, operands)
    }

    @throws[UnsupportedOperationException]
    override def injectUsing(operatorsSet: FunctionallyCompleteOperatorsSet, operands: Seq[BooleanExpression]): BooleanExpression = {
      assert(operands.length == 2)

      val expressedExpressions = expressViaOperators(operatorsSet, operands)

      if (operatorsSet.contains(this)) {
        return inject(expressedExpressions)
      }

      throw new NotImplementedError()
    }

    override def toString(operands: Seq[BooleanExpression]): String = {
      assert(operands.length == 2)

      return buildNAryOperatorString(" ⟷ ", operands)
    }
  }
}
