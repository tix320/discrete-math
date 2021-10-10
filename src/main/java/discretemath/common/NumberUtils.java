package discretemath.common;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberUtils {

	public static final BigInteger BIGINTEGER_POSITIVE_INFINITY = new BigInteger("0");

	public static final BigInteger BIGINTEGER_NEGATIVE_INFINITY = new BigInteger("0");

	public static final BigDecimal DECIMAL_POSITIVE_INFINITY = new BigDecimal("0");

	public static final BigDecimal DECIMAL_NEGATIVE_INFINITY = new BigDecimal("0");

	public static boolean isFinite(BigInteger bigInteger) {
		return bigInteger != BIGINTEGER_POSITIVE_INFINITY && bigInteger != BIGINTEGER_NEGATIVE_INFINITY;
	}

	public static boolean isFinite(BigDecimal bigDecimal) {
		return bigDecimal != DECIMAL_POSITIVE_INFINITY && bigDecimal != DECIMAL_NEGATIVE_INFINITY;
	}

	public static boolean isInfinite(BigInteger bigInteger) {
		return bigInteger == BIGINTEGER_POSITIVE_INFINITY || bigInteger == BIGINTEGER_NEGATIVE_INFINITY;
	}

	public static boolean isInfinite(BigDecimal bigDecimal) {
		return bigDecimal == DECIMAL_POSITIVE_INFINITY || bigDecimal == DECIMAL_NEGATIVE_INFINITY;
	}

	public static boolean isPositive(BigInteger bigInteger) {
		return bigInteger == BIGINTEGER_POSITIVE_INFINITY;
	}

	public static boolean isPositive(BigDecimal bigDecimal) {
		return bigDecimal == DECIMAL_POSITIVE_INFINITY;
	}

	public static boolean isNegative(BigInteger bigInteger) {
		return bigInteger == BIGINTEGER_NEGATIVE_INFINITY;
	}

	public static boolean isNegative(BigDecimal bigDecimal) {
		return bigDecimal == DECIMAL_NEGATIVE_INFINITY;
	}

	public static boolean GT(BigInteger bigInteger, int than) {
		return bigInteger.compareTo(BigInteger.valueOf(than)) > 0;
	}

	public static boolean GTE(BigInteger bigInteger, int than) {
		return bigInteger.compareTo(BigInteger.valueOf(than)) >= 0;
	}

	public static boolean LT(BigInteger bigInteger, int than) {
		return bigInteger.compareTo(BigInteger.valueOf(than)) < 0;
	}

	public static boolean LTE(BigInteger bigInteger, int than) {
		return bigInteger.compareTo(BigInteger.valueOf(than)) <= 0;
	}
}
