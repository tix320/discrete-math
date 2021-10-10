package discretemath.structure.set.range;

import java.math.BigInteger;

import discretemath.common.NumberUtils;

public interface Range<T> {

	/**
	 * @return {@link NumberUtils#BIGINTEGER_POSITIVE_INFINITY} when infinity
	 */
	BigInteger size();

	boolean contains(T item);
}
