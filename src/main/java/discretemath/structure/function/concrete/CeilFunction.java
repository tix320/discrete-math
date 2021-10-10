package discretemath.structure.function.concrete;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import discretemath.structure.function.CalculableFunction;
import discretemath.structure.set.Sets;

public final class CeilFunction extends CalculableFunction<BigDecimal, BigInteger> {

	public CeilFunction() {
		super(Sets.R, Sets.Z);
	}

	@Override
	public boolean isOnto() {
		return true;
	}

	@Override
	public boolean isOneToOne() {
		return false;
	}

	@Override
	protected BigInteger calculate(BigDecimal value) {
		return value.setScale(0, RoundingMode.CEILING).toBigInteger();
	}
}
