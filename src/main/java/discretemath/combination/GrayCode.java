package discretemath.combination;

import discretemath.common.BitString;

public class GrayCode {

	private final BitString[] table;

	private GrayCode(BitString[] table) {
		this.table = table;
	}

	public static GrayCode forN(int n) {
		BitString[] bitSetTable = createBitStringTable(n);

		return new GrayCode(bitSetTable);
	}

	public BitString get(int index) {
		return table[index];
	}

	public int find(BitString bitString) {
		if (bitString.length() != getVariablesCount()) {
			throw new IllegalArgumentException(bitString.toString());
		}

		for (int i = 0; i < table.length; i++) {
			BitString string = table[i];
			if (bitString.equals(string)) {
				return i;
			}
		}

		throw new IllegalStateException();
	}

	public int getCombinationsCount() {
		return table.length;
	}

	public int getVariablesCount() {
		return table[0].length();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (BitString bitString : table) {
			for (int i = 0; i < bitString.length(); i++) {
				boolean b = bitString.get(i);
				builder.append(b ? 1 : 0).append("\t");
			}
			builder.append("\n");
		}

		return builder.toString();
	}

	private static BitString[] createBitStringTable(int n) {
		int rowsCount = 1 << n;

		BitString[] table = new BitString[rowsCount];
		for (int i = 0; i < table.length; i++) {
			table[i] = BitString.forN(n);
		}

		for (int i = 0; i < rowsCount; i++) {
			int grayCode = i ^ (i >> 1);
			for (int j = 0; j < n; j++) {
				boolean value = ((grayCode >> j) & 1) == 1;
				table[i].set(n - 1 - j, value);
			}
		}

		return table;
	}
}
