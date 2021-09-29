package discretemath.samples;

import discretemath.bool.SumOfProductExpression;
import discretemath.bool.table.TruthTable;

public class BuildFunctionSumOfProductExpansion {

	public static void main(String[] args) {
		TruthTable truthTable = TruthTable.forNDegree(3, 1);
		truthTable.setFunctionValue(0, new boolean[]{true, false, true, false, false, true, true, false});

		SumOfProductExpression sumOfProductExpression = SumOfProductExpression.fromTruthTable(truthTable);

		System.out.println(sumOfProductExpression);
	}
}
