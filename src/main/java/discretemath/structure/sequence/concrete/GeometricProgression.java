package discretemath.structure.sequence.concrete;

import java.math.BigDecimal;

import discretemath.structure.sequence.SimpleSequence;
import discretemath.structure.set.Sets;

public final class GeometricProgression extends SimpleSequence<BigDecimal> {

	private final BigDecimal initialTerm;

	private final BigDecimal ratio;

	public GeometricProgression(BigDecimal initialTerm, BigDecimal ratio) {
		super(Sets.R);
		this.initialTerm = initialTerm;
		this.ratio = ratio;
	}

	@Override
	protected BigDecimal getTerm(int n) {
		return initialTerm.multiply(ratio.pow(n));
	}
}
