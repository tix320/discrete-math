package discretemath.samples;

import discretemath.bool.SumOfProductExpression;
import discretemath.bool.table.TruthTable;

public class BuildFunctionSumOfProductExpansion {

	public static void main(String[] args) {
		boolean[][] truthTable = new boolean[][]{{false, false, false, true},
												 {false, false, true, false},
												 {false, true, false, true},
												 {false, true, true, false},
												 {true, false, false, false},
												 {true, false, true, true},
												 {true, true, false, true},
												 {true, true, true, false}};

		TruthTable table = TruthTable.of(3, truthTable);

		SumOfProductExpression sumOfProductExpression = SumOfProductExpression.fromTruthTable(table);

		System.out.println(sumOfProductExpression);
	}
}
