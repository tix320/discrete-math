package discretemath.structure.function.concrete;

import java.math.BigInteger;

import discretemath.structure.sequence.CacheableSequence;
import discretemath.structure.sequence.RecurrentSequence;
import discretemath.structure.sequence.Sequence;
import discretemath.structure.set.Sets;

public final class FactorialFunction extends RecurrentSequence<BigInteger> {

	private FactorialFunction() {
		super(Sets.Z_PLUS);
	}

	public static Sequence<BigInteger> get() {
		return new CacheableSequence<>(new FactorialFunction());
	}

	@Override
	public boolean isOnto() {
		return false;
	}

	@Override
	public boolean isOneToOne() {
		return false;
	}

	@Override
	protected BigInteger getNextRecurrentValue(int n) {
		if (n == 0 || n == 1) {
			return BigInteger.ONE;
		}

		return getNextRecurrentValue(n - 1).multiply(BigInteger.valueOf(n));
	}
}
