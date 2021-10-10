package discretemath.structure.set.range.integer;

import java.math.BigInteger;
import java.util.Objects;

import discretemath.common.NumberUtils;
import discretemath.structure.set.range.EmptyRange;
import discretemath.structure.set.range.InfiniteRange;
import discretemath.structure.set.range.Range;

/**
 * Invariants: [x, y], [x, +∞], [-∞, x], [-∞, +∞]
 */
public class BigIntegerRange implements Range<BigInteger> {

	private final BigInteger from;

	private final BigInteger to;

	private BigIntegerRange(BigInteger from, BigInteger to) {
		this.from = from;
		this.to = to;
	}

	public static Range<BigInteger> rangeInclusive(BigInteger from, BigInteger to) {
		return rangeExclusive(from, to, true, true);
	}

	public static Range<BigInteger> rangeExclusive(BigInteger from, BigInteger to) {
		return rangeExclusive(from, to, false, false);
	}

	public static Range<BigInteger> rangeExclusive(BigInteger from, BigInteger to, boolean startInclusive,
												   boolean endInclusive) {
		if (NumberUtils.isPositive(from) || NumberUtils.isNegative(to)) {
			return EmptyRange.get();
		}

		if (NumberUtils.isFinite(from) && !startInclusive) {
			from = from.add(BigInteger.ONE);
		}

		if (NumberUtils.isFinite(to) && !endInclusive) {
			to = to.subtract(BigInteger.ONE);
		}

		if (NumberUtils.isFinite(from) && NumberUtils.isInfinite(to)) {
			return new FromXToInfinite(from);
		} else if (NumberUtils.isInfinite(from) && NumberUtils.isFinite(to)) {
			return new FromInfiniteToX(to);
		} else if (NumberUtils.isInfinite(from) && NumberUtils.isInfinite(to)) {
			return InfiniteRange.get();
		}

		if (from.compareTo(to) > 0) {
			return EmptyRange.get();
		}

		return new BigIntegerRange(from, to);
	}

	@Override
	public boolean contains(BigInteger item) {
		return item.compareTo(from) >= 0 && item.compareTo(to) <= 0;
	}

	@Override
	public BigInteger size() {
		return to.subtract(from).add(BigInteger.ONE);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BigIntegerRange that = (BigIntegerRange) o;
		return from.equals(that.from) && to.equals(that.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}
}
