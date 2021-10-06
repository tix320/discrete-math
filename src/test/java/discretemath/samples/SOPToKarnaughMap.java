package discretemath.samples;

import discretemath.bool.SumOfProductExpression;
import discretemath.bool.minimization.KarnaughMap;
import discretemath.bool.table.FunctionValue;
import discretemath.bool.table.TruthTable;

import static discretemath.bool.table.FunctionValue.FALSE;
import static discretemath.bool.table.FunctionValue.TRUE;

public class SOPToKarnaughMap {

	public static void main(String[] args) {
		TruthTable truthTable = TruthTable.forNDegree(3, 1);
		truthTable.setFunctionValue(0, new FunctionValue[]{TRUE, FALSE, TRUE, FALSE, FALSE, TRUE, TRUE, FALSE});

		SumOfProductExpression sumOfProductExpression = SumOfProductExpression.fromTruthTable(truthTable);

		KarnaughMap karnaughMap = KarnaughMap.fromSOPExpression(sumOfProductExpression);

		System.out.println(karnaughMap);
	}
}
