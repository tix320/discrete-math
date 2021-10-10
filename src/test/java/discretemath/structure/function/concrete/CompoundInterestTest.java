package discretemath.structure.function.concrete;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompoundInterestTest {

	@Test
	void calculate() {
		CompoundInterest compoundInterest = new CompoundInterest(BigDecimal.valueOf(0.05), 12, 10);

		assertEquals(BigDecimal.valueOf(8235.0474885), compoundInterest.calculate(BigDecimal.valueOf(5000)));
	}
}