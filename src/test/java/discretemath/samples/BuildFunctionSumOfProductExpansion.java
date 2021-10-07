package discretemath.samples;

import discretemath.bool.expression.forms.SumOfProductExpression;
import discretemath.bool.table.FunctionValue;
import discretemath.bool.table.TruthTable;

import static discretemath.bool.table.FunctionValue.FALSE;
import static discretemath.bool.table.FunctionValue.TRUE;

public class BuildFunctionSumOfProductExpansion {

	public static void main(String[] args) {
		TruthTable truthTable = TruthTable.forNDegree(3, 1);
		truthTable.setFunctionValue(0, new FunctionValue[]{TRUE, FALSE, TRUE, FALSE, FALSE, TRUE, TRUE, FALSE});

		SumOfProductExpression sumOfProductExpression = SumOfProductExpression.fromTruthTable(truthTable);

		System.out.println(sumOfProductExpression);
	}
}
