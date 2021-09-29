package discretemath.combination;

import discretemath.bool.BooleanTable;
import discretemath.common.Direction;

public class BitCombinations {

	public static BooleanTable nBitCombinations(int n) {
		int rowsCount = 1 << n;

		BooleanTable table = BooleanTable.create(rowsCount, n);

		nBitCombinations(n, table, 0, 0, Direction.FROM_UP_TO_DOWN);

		return table;
	}

	public static void nBitCombinations(int n, BooleanTable table) {
		nBitCombinations(n, table, 0, 0, Direction.FROM_UP_TO_DOWN);
	}

	public static void nBitCombinations(int n, BooleanTable table, int startRow, int startColumn,
										Direction tableFillDirection) {
		TableCombinationValueSetter valueSetter = getTableValueSetter(tableFillDirection, startRow, startColumn);

		int rowsCount = 1 << n;

		for (int i = 0; i < rowsCount; i++) {
			for (int j = 0; j < n; j++) {
				boolean value = ((i >> j) & 1) == 1;
				valueSetter.perform(table, i, n - 1 - j, value);
			}
		}
	}

	private static TableCombinationValueSetter getTableValueSetter(Direction direction, int startRow, int startColumn) {
		return switch (direction) {
			case FROM_UP_TO_DOWN -> (table, i, j, value) -> table.set(startRow + i, startColumn + j, value);
			case FROM_LEFT_TO_RIGHT -> (table, i, j, value) -> table.set(startRow + j, startColumn + i, value);
		};
	}

	private interface TableCombinationValueSetter {

		void perform(BooleanTable table, int i, int j, boolean value);
	}
}
