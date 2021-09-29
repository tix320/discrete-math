package discretemath.common;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BitString implements Iterable<Boolean> {

	private final boolean[] bits;

	private BitString(boolean[] bits) {
		this.bits = bits;
	}

	public static BitString forN(int n) {
		boolean[] bits = new boolean[n];
		return new BitString(bits);
	}

	public void set(int index, boolean value) {
		bits[index] = value;
	}

	public boolean get(int index) {
		return bits[index];
	}

	public int length() {
		return bits.length;
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
		return Arrays.equals(bits, bitString.bits);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(bits);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (boolean bit : bits) {
			builder.append(bit ? '1' : '0');
		}

		return builder.toString();
	}

	private final class Itr implements Iterator<Boolean> {

		private int index = 0;


		@Override
		public boolean hasNext() {
			return index < bits.length;
		}

		@Override
		public Boolean next() {
			try {
				return bits[index++];
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new NoSuchElementException();
			}
		}
	}
}
