package discretemath.samples;

import discretemath.bool.KarnaughMap;
import discretemath.bool.SumOfProductExpression;
import discretemath.bool.table.TruthTable;

public class SOPToKarnaughMap {

	public static void main(String[] args) {
		TruthTable truthTable = TruthTable.forNDegree(3, 1);
		truthTable.setFunctionValue(0, new boolean[]{true, false, true, false, false, true, true, false});

		SumOfProductExpression sumOfProductExpression = SumOfProductExpression.fromTruthTable(truthTable);

		KarnaughMap karnaughMap = KarnaughMap.fromSOPExpression(sumOfProductExpression);

		System.out.println(karnaughMap);
	}
}
