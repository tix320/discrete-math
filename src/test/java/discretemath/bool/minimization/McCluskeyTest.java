package discretemath.bool.minimization;

import java.util.List;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.BooleanVariable;
import discretemath.bool.table.FunctionValue;
import discretemath.bool.table.TruthTable;
import org.junit.jupiter.api.Test;

import static discretemath.bool.table.FunctionValue.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class McCluskeyTest {

	@Test
	void applyToTruthTable1() {
		int variablesCount = 4;
		TruthTable truthTable = TruthTable.forNDegree(variablesCount, 1);
		truthTable.setFunctionValue(0, new FunctionValue[]{FALSE,
														   FALSE,
														   FALSE,
														   FALSE,
														   TRUE,
														   FALSE,
														   FALSE,
														   FALSE,
														   TRUE,
														   DONT_CARE,
														   TRUE,
														   TRUE,
														   TRUE,
														   FALSE,
														   DONT_CARE,
														   TRUE});

		List<BooleanVariable> variables = BooleanVariable.getVariables(variablesCount);

		McCluskey mcCluskey = new McCluskey();

		BooleanExpression booleanExpression = mcCluskey.minimizeFromTruthTable(truthTable, variables);

		assertEquals("(xy′) + (yz′w′) + (xz)", booleanExpression.toString());
	}

	@Test
	void applyToTruthTable2() {
		int variablesCount = 4;
		TruthTable truthTable = TruthTable.forNDegree(variablesCount, 1);
		truthTable.setFunctionValue(0, new FunctionValue[]{TRUE,
														   TRUE,
														   TRUE,
														   TRUE,
														   TRUE,
														   FALSE,
														   FALSE,
														   FALSE,
														   TRUE,
														   TRUE,
														   TRUE,
														   FALSE,
														   FALSE,
														   FALSE,
														   TRUE,
														   FALSE});

		List<BooleanVariable> variables = BooleanVariable.getVariables(variablesCount);

		McCluskey mcCluskey = new McCluskey();

		BooleanExpression booleanExpression = mcCluskey.minimizeFromTruthTable(truthTable, variables);

		assertEquals("(x′y′) + (y′z′) + (xzw′) + (x′z′w′)", booleanExpression.toString());
	}

	@Test
	void applyToTruthTable3() {
		int variablesCount = 4;
		TruthTable truthTable = TruthTable.forNDegree(variablesCount, 1);
		truthTable.setFunctionValue(0, new FunctionValue[]{DONT_CARE,
														   FALSE,
														   TRUE,
														   TRUE,
														   DONT_CARE,
														   DONT_CARE,
														   FALSE,
														   FALSE,
														   FALSE,
														   DONT_CARE,
														   TRUE,
														   DONT_CARE,
														   TRUE,
														   FALSE,
														   TRUE,
														   FALSE});

		List<BooleanVariable> variables = BooleanVariable.getVariables(variablesCount);

		McCluskey mcCluskey = new McCluskey();

		BooleanExpression booleanExpression = mcCluskey.minimizeFromTruthTable(truthTable, variables);

		assertEquals("(y′z) + (xyw′)", booleanExpression.toString());
	}

	@Test
	void applyToTruthTable4() {
		int variablesCount = 5;
		TruthTable truthTable = TruthTable.forNDegree(variablesCount, 1);
		truthTable.setFunctionValue(0, new FunctionValue[]{DONT_CARE,
														   FALSE,
														   TRUE,
														   TRUE,
														   FALSE,
														   DONT_CARE,
														   TRUE,
														   FALSE,
														   TRUE,
														   DONT_CARE,
														   TRUE,
														   FALSE,
														   FALSE,
														   FALSE,
														   FALSE,
														   DONT_CARE,
														   TRUE,
														   FALSE,
														   TRUE,
														   TRUE,
														   DONT_CARE,
														   TRUE,
														   TRUE,
														   FALSE,
														   FALSE,
														   DONT_CARE,
														   FALSE,
														   FALSE,
														   DONT_CARE,
														   DONT_CARE,
														   DONT_CARE,
														   DONT_CARE});

		List<BooleanVariable> variables = BooleanVariable.getVariables(variablesCount);

		McCluskey mcCluskey = new McCluskey();

		BooleanExpression booleanExpression = mcCluskey.minimizeFromTruthTable(truthTable, variables);

		assertEquals("(y′z′w) + (x′z′a′) + (y′wa′) + (y′z′a′) + (xzw′)", booleanExpression.toString());
	}
}