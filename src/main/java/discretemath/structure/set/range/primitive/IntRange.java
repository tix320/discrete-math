package discretemath.structure.set.range.primitive;

import java.math.BigInteger;
import java.util.Objects;

import discretemath.structure.set.range.EmptyRange;
import discretemath.structure.set.range.Range;

public class IntRange implements Range<Integer> {

	private final int from;

	private final int to;

	private IntRange(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public static Range<Integer> rangeInclusive(int from, int to) {
		return rangeExclusive(from, to, true, true);
	}

	public static Range<Integer> rangeExclusive(int from, int to) {
		return rangeExclusive(from, to, false, false);
	}

	public static Range<Integer> rangeExclusive(int from, int to, boolean startInclusive, boolean endInclusive) {
		if (!startInclusive) {
			from++;
		}

		if (!endInclusive) {
			to--;
		}

		if (from > to) {
			return EmptyRange.get();
		}

		return new IntRange(from, to);
	}

	@Override
	public BigInteger size() {
		return BigInteger.valueOf(to - from + 1);
	}

	@Override
	public boolean contains(Integer item) {
		return item >= from && item <= to;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IntRange intRange = (IntRange) o;
		return from == intRange.from && to == intRange.to;
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}
}
