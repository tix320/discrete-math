package discretemath.bool.expression.forms;

import java.util.Map;

import discretemath.bool.expression.atomic.BooleanVariable;
import discretemath.bool.expression.atomic.BooleanVariables;
import discretemath.bool.table.FunctionValue;
import discretemath.bool.table.TruthTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductOfSumExpressionTest {

	@Test
	void evaluate() {
		TruthTable truthTable = TruthTable.forNDegree(3, 1);

		truthTable.setFunctionValue(0, new FunctionValue[]{FunctionValue.TRUE,
														   FunctionValue.FALSE,
														   FunctionValue.FALSE,
														   FunctionValue.TRUE,
														   FunctionValue.TRUE,
														   FunctionValue.TRUE,
														   FunctionValue.FALSE,
														   FunctionValue.FALSE});

		ProductOfSumExpression productOfSumExpression = ProductOfSumExpression.fromTruthTable(truthTable);

		assertEquals("(x+y+z′)(x+y′+z)(x′+y′+z)(x′+y′+z′)", productOfSumExpression.toString());

		assertTrue(productOfSumExpression.evaluate(getXYZValues(false, false, false)));
		assertFalse(productOfSumExpression.evaluate(getXYZValues(false, false, true)));
		assertFalse(productOfSumExpression.evaluate(getXYZValues(false, true, false)));
		assertTrue(productOfSumExpression.evaluate(getXYZValues(false, true, true)));
		assertTrue(productOfSumExpression.evaluate(getXYZValues(true, false, false)));
		assertTrue(productOfSumExpression.evaluate(getXYZValues(true, false, true)));
		assertFalse(productOfSumExpression.evaluate(getXYZValues(true, true, false)));
		assertFalse(productOfSumExpression.evaluate(getXYZValues(true, true, true)));
	}

	private static Map<BooleanVariable, Boolean> getXYZValues(boolean x, boolean y, boolean z) {
		return Map.of(BooleanVariables.X, x, BooleanVariables.Y, y, BooleanVariables.Z, z);
	}
}