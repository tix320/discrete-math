package discretemath.bool.minimization;

import java.util.List;

import discretemath.bool.BooleanTable;
import discretemath.bool.SumOfProductExpression;
import discretemath.bool.expression.BooleanVariable;
import discretemath.bool.expression.minterm.Minterm;
import discretemath.combination.GrayCode;
import discretemath.common.BitString;

public final class KarnaughMap {

	private final GrayCode rowsGrayCode;
	private final GrayCode columnsGrayCode;

	private final BooleanTable table;

	private KarnaughMap(GrayCode rowsGrayCode, GrayCode columnsGrayCode, BooleanTable table) {
		this.rowsGrayCode = rowsGrayCode;
		this.columnsGrayCode = columnsGrayCode;
		this.table = table;
	}

	public static KarnaughMap fromSOPExpression(SumOfProductExpression expression) {
		List<BooleanVariable> variables = expression.getVariables();
		int variablesCount = variables.size();

		GrayCode rowHeaders = GrayCode.forN(getBitCountForLeftSide(variablesCount));
		GrayCode columnHeaders = GrayCode.forN(getBitCountForRightSide(variablesCount));

		BooleanTable table = buildTableFor(variablesCount);

		for (Minterm minterm : expression.getMinterms()) {
			int literalsSize = minterm.literals().size();
			BitString rowBitString = minterm.subMinterm(0, rowHeaders.getVariablesCount()).toBitString();
			BitString columnBitString = minterm.subMinterm(rowHeaders.getVariablesCount(), literalsSize).toBitString();

			int row = rowHeaders.find(rowBitString);
			int column = columnHeaders.find(columnBitString);

			table.set(row, column, true);
		}


		return new KarnaughMap(rowHeaders, columnHeaders, table);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(" ".repeat(rowsGrayCode.getVariablesCount() + 1));
		for (int j = 0; j < columnsGrayCode.getCombinationsCount(); j++) {
			builder.append("|");
			BitString bitString = columnsGrayCode.get(j);
			builder.append(bitString);
		}

		builder.append("\n");

		for (int i = 0; i < rowsGrayCode.getCombinationsCount(); i++) {
			builder.append(rowsGrayCode.get(i));
			builder.append(" ");

			for (int j = 0; j < columnsGrayCode.getCombinationsCount(); j++) {
				builder.append("|");
				builder.append(table.get(i, j) ? '1' : '0');
				builder.append(" ".repeat(columnsGrayCode.getVariablesCount() - 1));
			}

			builder.append("\n");
		}

		return builder.toString();
	}

	private static BooleanTable buildTableFor(int n) {
		int leftVariablesCount = getBitCountForLeftSide(n);
		int rightVariablesCount = getBitCountForRightSide(n);
		int rowsCount = 1 << leftVariablesCount;
		int columnsCount = 1 << rightVariablesCount;

		return BooleanTable.create(rowsCount, columnsCount);
	}

	private static int getBitCountForLeftSide(int n) {
		return (int) Math.floor(n / 2D);
	}

	private static int getBitCountForRightSide(int n) {
		return (int) Math.ceil(n / 2D);
	}
}
