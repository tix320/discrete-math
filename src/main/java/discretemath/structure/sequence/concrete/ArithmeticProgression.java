package discretemath.structure.sequence.concrete;

import java.math.BigDecimal;

import discretemath.structure.sequence.SimpleSequence;
import discretemath.structure.set.Sets;

public final class ArithmeticProgression extends SimpleSequence<BigDecimal> {

	private final BigDecimal initialTerm;

	private final BigDecimal difference;

	public ArithmeticProgression(BigDecimal initialTerm, BigDecimal difference) {
		super(Sets.R);
		this.initialTerm = initialTerm;
		this.difference = difference;
	}

	@Override
	protected BigDecimal getTerm(int n) {
		return initialTerm.add(difference.multiply(BigDecimal.valueOf(n)));
	}

	@Override
	public boolean isOneToOne() {
		return true;
	}
}
