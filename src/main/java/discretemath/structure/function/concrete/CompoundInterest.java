package discretemath.structure.function.concrete;

import java.math.BigDecimal;
import java.math.RoundingMode;

import discretemath.structure.exception.NotInvertibleException;
import discretemath.structure.function.CalculableFunction;
import discretemath.structure.function.Function;
import discretemath.structure.set.Sets;

public class CompoundInterest extends CalculableFunction<BigDecimal, BigDecimal> {

	private final BigDecimal interestRate;

	private final int compoundNumbersPerTime;

	private final int time;

	public CompoundInterest(BigDecimal interestRate, int compoundNumbersPerTime, int time) {
		super(Sets.R_NON_NEG, Sets.R_NON_NEG);
		this.interestRate = interestRate;
		this.compoundNumbersPerTime = compoundNumbersPerTime;
		this.time = time;
	}

	@Override
	protected BigDecimal calculate(BigDecimal principal) {
		BigDecimal compoundNumbers = BigDecimal.valueOf(compoundNumbersPerTime);
		BigDecimal rateDivideNumbers = interestRate.divide(compoundNumbers, 15, RoundingMode.HALF_UP);
		BigDecimal onePlusDivision = BigDecimal.ONE.add(rateDivideNumbers);
		BigDecimal exponent = compoundNumbers.multiply(BigDecimal.valueOf(time));

		BigDecimal power = onePlusDivision.pow(exponent.intValue());
		power = power.setScale(10, RoundingMode.HALF_UP);

		BigDecimal result = principal.multiply(power);

		result = result.stripTrailingZeros();

		return result;
	}

	@Override
	public Function<BigDecimal, BigDecimal> getInverse() throws NotInvertibleException {
		// TODO
		throw new UnsupportedOperationException();
	}


}
