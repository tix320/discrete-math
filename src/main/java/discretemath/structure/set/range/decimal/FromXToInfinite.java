package discretemath.structure.set.range.decimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import discretemath.common.NumberUtils;
import discretemath.structure.set.range.Range;

public final class FromXToInfinite implements Range<BigDecimal> {

	private final BigDecimal from;

	private final boolean inclusive;

	public FromXToInfinite(BigDecimal from, boolean inclusive) {
		this.from = from;
		this.inclusive = inclusive;
	}

	@Override
	public BigInteger size() {
		return NumberUtils.BIGINTEGER_POSITIVE_INFINITY;
	}

	@Override
	public boolean contains(BigDecimal item) {
		int compare = item.compareTo(from);
		if (compare == 0) {
			return inclusive;
		} else {
			return compare > 0;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FromXToInfinite that = (FromXToInfinite) o;
		return inclusive == that.inclusive && from.equals(that.from);
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, inclusive);
	}
}