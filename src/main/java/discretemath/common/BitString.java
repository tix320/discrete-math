package discretemath.common;

import java.util.BitSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class BitString implements Iterable<Boolean> {

	private final BitSet bitSet;

	private final int length;

	private BitString(BitSet bitSet, int length) {
		this.bitSet = bitSet;
		this.length = length;
	}

	public static BitString forN(int n) {
		return new BitString(new BitSet(n), n);
	}

	public void set(int index, boolean value) {
		if (index >= length) {
			throw new IndexOutOfBoundsException(index);
		}

		bitSet.set(index, value);
	}

	public boolean get(int index) {
		if (index >= length) {
			throw new IndexOutOfBoundsException(index);
		}

		return bitSet.get(index);
	}

	public int length() {
		return length;

	}

	public int bitCount() {
		return bitSet.cardinality();
	}

	public long asLong() {
		if (length > 64) {
			throw new UnsupportedOperationException();
		}

		long value = 0L;
		for (int i = length - 1; i >= 0; i--) {
			int shiftCount = length - 1 - i;
			value += bitSet.get(i) ? (1L << shiftCount) : 0L;
		}
		return value;
	}

	public boolean differInOneBit(BitString other) {
		try {
			getDifferBitIndex(other);
			return true;
		} catch (MoreThanOneBitDifferenceException e) {
			return false;
		}
	}

	public int getDifferBitIndex(BitString other) throws MoreThanOneBitDifferenceException {
		BitSet clone = (BitSet) this.bitSet.clone();
		clone.xor(other.bitSet);

		if (clone.cardinality() != 1) {
			throw new MoreThanOneBitDifferenceException(Integer.toString(clone.cardinality()));
		}

		return clone.length() - 1;
	}

	@Override
	public Iterator<Boolean> iterator() {
		return new Itr();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BitString bitString = (BitString) o;
		return length == bitString.length && bitSet.equals(bitString.bitSet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bitSet, length);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean bit = bitSet.get(i);
			builder.append(bit ? '1' : '0');
		}

		return builder.toString();
	}

	private final class Itr implements Iterator<Boolean> {

		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < length;
		}

		@Override
		public Boolean next() {
			try {
				return bitSet.get(index++);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new NoSuchElementException();
			}
		}
	}
}
