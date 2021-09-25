package discretemath.samples;

import discretemath.bool.table.TruthTable;

public class BuildTruthTableForAllFunctions {

	public static void main(String[] args) {
		TruthTable.forNDegree(2).print();
		System.out.println("-------------");
		TruthTable.forNDegreeWithAllFunctions(2).print();
		System.out.println("-------------");
		TruthTable.forNDegree(3).print();
		System.out.println("-------------");
		TruthTable.forNDegreeWithAllFunctions(3).print();
	}
}
