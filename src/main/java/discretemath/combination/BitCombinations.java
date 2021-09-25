package discretemath.combination;

import discretemath.common.Direction;

public class BitCombinations {

	public static boolean[][] nBitCombinations(int n) {
		int rowsCount = 1 << n;
		boolean[][] table = new boolean[rowsCount][n];

		nBitCombinations(n, table, 0, 0, Direction.FROM_UP_TO_DOWN);

		return table;
	}

	public static boolean[][] nBitCombinations(int n, boolean[][] table) {
		nBitCombinations(n, table, 0, 0, Direction.FROM_UP_TO_DOWN);

		return table;
	}

	public static void nBitCombinations(int n, boolean[][] table, int startRow, int startColumn,
										Direction tableFillDirection) {
		TableCombinationValueSetter valueSetter = getTableValueSetter(tableFillDirection, startRow, startColumn);

		int rowsCount = 1 << n;

		for (int i = 0; i < rowsCount; i++) {
			for (int j = 0; j < n; j++) {
				int val = rowsCount * j + i;
				int ret = (1 & (val >>> j));
				valueSetter.perform(table, i, j, ret);
			}
		}
	}

	private static TableCombinationValueSetter getTableValueSetter(Direction direction, int startRow, int startColumn) {
		return switch (direction) {
			case FROM_UP_TO_DOWN -> (table, i, j, ret) -> table[startRow + i][startColumn + j] = ret != 0;
			case FROM_LEFT_TO_RIGHT -> (table, i, j, ret) -> table[startRow + j][startColumn + i] = ret != 0;
		};
	}

	private interface TableCombinationValueSetter {

		void perform(boolean[][] table, int i, int j, int ret);
	}
}
