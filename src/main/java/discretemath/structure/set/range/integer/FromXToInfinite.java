package discretemath.structure.set.range.integer;

import java.math.BigInteger;
import java.util.Objects;

import discretemath.common.NumberUtils;
import discretemath.structure.set.range.Range;

public final class FromXToInfinite implements Range<BigInteger> {

	private final BigInteger from;

	public FromXToInfinite(BigInteger from) {
		this.from = from;
	}

	@Override
	public BigInteger size() {
		return NumberUtils.BIGINTEGER_POSITIVE_INFINITY;
	}

	@Override
	public boolean contains(BigInteger item) {
		return item.compareTo(from) >= 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FromXToInfinite that = (FromXToInfinite) o;
		return from.equals(that.from);
	}

	@Override
	public int hashCode() {
		return Objects.hash(from);
	}
}