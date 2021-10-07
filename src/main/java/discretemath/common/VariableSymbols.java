package discretemath.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class VariableSymbols {

	private static final char[] symbols = {'x', 'y', 'z', 'w', 'a', 'b'};

	public static Iterable<Character> getIterator(int count) {
		if (count > symbols.length) {
			throw new UnsupportedOperationException(String.valueOf(count));
		}

		return () -> new Itr(count);
	}

	private static final class Itr implements Iterator<Character> {

		private final int count;
		private int index;

		private Itr(int count) {
			this.count = count;
			this.index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < count;
		}

		@Override
		public Character next() {
			try {
				return symbols[index++];
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new NoSuchElementException();
			}
		}
	}

}
