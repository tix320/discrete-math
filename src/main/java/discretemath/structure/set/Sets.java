package discretemath.structure.set;

import java.math.BigDecimal;
import java.math.BigInteger;

import discretemath.common.NumberUtils;
import discretemath.structure.set.range.decimal.BigDecimalRange;
import discretemath.structure.set.range.integer.BigIntegerRange;
import discretemath.structure.set.range.primitive.IntRange;

public class Sets {

	/**
	 * The set of all real numbers
	 */
	public static final Set<BigDecimal> R = new BigDecimalRangeSet(
			BigDecimalRange.rangeInclusive(NumberUtils.DECIMAL_NEGATIVE_INFINITY,
					NumberUtils.DECIMAL_POSITIVE_INFINITY));

	/**
	 * The set of all real non-negative numbers
	 */
	public static final Set<BigDecimal> R_NON_NEG = new BigDecimalRangeSet(
			BigDecimalRange.rangeInclusive(BigDecimal.ZERO, NumberUtils.DECIMAL_POSITIVE_INFINITY));

	/**
	 * {... ,−2,−1, 0, 1, 2, ...}, the set of all integers
	 */
	public static final Set<BigInteger> Z = new BigIntegerRangeSet(
			BigIntegerRange.rangeInclusive(NumberUtils.BIGINTEGER_NEGATIVE_INFINITY,
					NumberUtils.BIGINTEGER_POSITIVE_INFINITY));

	/**
	 * {1, 2, 3, ...}, the set of all positive integers
	 */
	public static final Set<BigInteger> Z_PLUS = new BigIntegerRangeSet(
			BigIntegerRange.rangeInclusive(BigInteger.ONE, NumberUtils.BIGINTEGER_POSITIVE_INFINITY));

	/**
	 * {0, 1, 2, 3, ...}, the set of all natural numbers
	 */
	public static final Set<BigInteger> N = new BigIntegerRangeSet(
			BigIntegerRange.rangeInclusive(BigInteger.ZERO, NumberUtils.BIGINTEGER_POSITIVE_INFINITY));

	/**
	 * {0, 1, 2, 3, ...}, the set of all natural numbers until {@link Integer#MAX_VALUE}
	 */
	public static final Set<Integer> N_INT = new IntRangeSet(IntRange.rangeInclusive(0, Integer.MAX_VALUE));
}
