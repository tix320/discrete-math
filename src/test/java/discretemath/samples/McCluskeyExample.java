package discretemath.samples;

import java.util.List;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.atomic.BooleanVariable;
import discretemath.bool.expression.atomic.BooleanVariables;
import discretemath.bool.minimization.McCluskey;
import discretemath.bool.table.FunctionValue;
import discretemath.bool.table.TruthTable;

import static discretemath.bool.table.FunctionValue.*;

public class McCluskeyExample {

	public static void main(String[] args) {
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

		List<BooleanVariable> variables = BooleanVariables.getVariables(variablesCount);

		McCluskey mcCluskey = new McCluskey();

		BooleanExpression booleanExpression = mcCluskey.minimizeFromTruthTable(truthTable, variables);

		System.out.println(booleanExpression);
	}

}