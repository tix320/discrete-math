package discretemath.structure.set.range.decimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import discretemath.common.NumberUtils;
import discretemath.structure.set.range.EmptyRange;
import discretemath.structure.set.range.InfiniteRange;
import discretemath.structure.set.range.Range;

/**
 * Invariants: [x, y], [x, +∞], [-∞, x], [-∞, +∞]
 */
public class BigDecimalRange implements Range<BigDecimal> {

	private final BigDecimal from;

	private final BigDecimal to;

	private final boolean startInclusive;

	private final boolean endInclusive;

	private BigDecimalRange(BigDecimal from, BigDecimal to, boolean startInclusive, boolean endInclusive) {
		this.from = from;
		this.to = to;
		this.startInclusive = startInclusive;
		this.endInclusive = endInclusive;
	}

	public static Range<BigDecimal> rangeInclusive(BigDecimal from, BigDecimal to) {
		return new BigDecimalRange(from, to, true, true);
	}

	public static Range<BigDecimal> rangeExclusive(BigDecimal from, BigDecimal to) {
		return new BigDecimalRange(from, to, false, false);
	}

	public static Range<BigDecimal> rangeExclusive(BigDecimal from, BigDecimal to, boolean startInclusive,
												   boolean endInclusive) {
		if (NumberUtils.isPositive(from) || NumberUtils.isNegative(to)) {
			return EmptyRange.get();
		}

		if (NumberUtils.isFinite(from) && !startInclusive) {
			from = from.add(BigDecimal.ONE);
		}

		if (NumberUtils.isFinite(to) && !endInclusive) {
			to = to.subtract(BigDecimal.ONE);
		}

		if (NumberUtils.isFinite(from) && NumberUtils.isInfinite(to)) {
			return new FromXToInfinite(from, startInclusive);
		} else if (NumberUtils.isInfinite(from) && NumberUtils.isFinite(to)) {
			return new FromInfiniteToX(to, endInclusive);
		} else if (NumberUtils.isInfinite(from) && NumberUtils.isInfinite(to)) {
			return InfiniteRange.get();
		}

		if (from.compareTo(to) > 0) {
			return EmptyRange.get();
		}

		return new BigDecimalRange(from, to, startInclusive, endInclusive);
	}

	@Override
	public BigInteger size() {
		return NumberUtils.BIGINTEGER_POSITIVE_INFINITY;
	}

	@Override
	public boolean contains(BigDecimal item) {
		int fromCompare = item.compareTo(from);
		int toCompare = item.compareTo(to);

		if (fromCompare == 0) {
			return startInclusive;
		} else if (toCompare == 0) {
			return endInclusive;
		} else {
			return fromCompare > 0 && toCompare < 0;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BigDecimalRange that = (BigDecimalRange) o;
		return startInclusive == that.startInclusive && endInclusive == that.endInclusive && from.equals(
				that.from) && to.equals(that.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to, startInclusive, endInclusive);
	}
}
