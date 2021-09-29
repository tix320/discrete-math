package discretemath.samples;

import discretemath.bool.table.TruthTable;

public class BuildTruthTableForAllFunctions {

	public static void main(String[] args) {
		System.out.println(TruthTable.forNDegree(2));
		System.out.println("-------------");
		System.out.println(TruthTable.forNDegreeWithAllFunctions(2));
		System.out.println("-------------");
		System.out.println(TruthTable.forNDegree(3));
		System.out.println("-------------");
		System.out.println(TruthTable.forNDegreeWithAllFunctions(3));
		System.out.println("-------------");
		System.out.println(TruthTable.forNDegree(5));
	}
}
