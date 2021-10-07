package discretemath.samples;

import discretemath.bool.expression.*;
import discretemath.bool.expression.compound.CompoundBooleanExpression;
import discretemath.bool.expression.compound.OperatorNode;
import discretemath.bool.expression.compound.ValueNode;
import discretemath.bool.expression.atomic.BooleanVariable;

import static discretemath.bool.operator.FunctionallyCompleteOperatorsSet.*;

public class ExpressFunctionViaOperators {

	public static void main(String[] args) {
		BooleanVariable x = new BooleanVariable('x');
		BooleanVariable y = new BooleanVariable('y');

		ValueNode node1 = new ValueNode(x);
		OperatorNode node2 = OperatorNode.forNegation(new ValueNode(y));
		ValueNode node3 = new ValueNode(y);
		OperatorNode node4 = OperatorNode.forNegation(new ValueNode(x));

		OperatorNode node5 = OperatorNode.forOR(node1, node2);
		OperatorNode node6 = OperatorNode.forNegation(node5);

		OperatorNode node7 = OperatorNode.forOR(node3, node4);

		OperatorNode rootNode = OperatorNode.forOR(node6, node7);


		CompoundBooleanExpression expression = new CompoundBooleanExpression(rootNode);

		System.out.println(expression);

		System.out.println("------------");
		BooleanExpression viaANDExpression = expression.expressViaOperators(AND_NOT);
		BooleanExpression normalizedViaANDExpression = viaANDExpression.normalize();

		System.out.println(viaANDExpression);
		System.out.println(normalizedViaANDExpression);

		BooleanExpression viaORExpression = normalizedViaANDExpression.expressViaOperators(OR_NOT);
		BooleanExpression normalizedViaORExpression = viaORExpression.normalize();

		System.out.println("------------");
		System.out.println(viaORExpression);
		System.out.println(normalizedViaORExpression);

		BooleanExpression viaNANDExpression = expression.expressViaOperators(SINGLE_NAND);

		System.out.println("------------");
		System.out.println(viaNANDExpression);

		BooleanExpression viaNORExpression = expression.expressViaOperators(SINGLE_NOR);

		System.out.println("------------");
		System.out.println(viaNORExpression);
	}
}
