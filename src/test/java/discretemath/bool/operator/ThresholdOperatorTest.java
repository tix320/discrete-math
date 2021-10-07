package discretemath.bool.operator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThresholdOperatorTest {

	@Test
	void evaluate() {
		ThresholdOperator thresholdOperator = new ThresholdOperator(0.5, new double[]{-1, 1, 2});

		assertFalse(thresholdOperator.evaluate(false, false, false));
		assertTrue(thresholdOperator.evaluate(false, false, true));
		assertTrue(thresholdOperator.evaluate(false, true, false));
		assertTrue(thresholdOperator.evaluate(false, true, true));
		assertFalse(thresholdOperator.evaluate(true, false, false));
		assertTrue(thresholdOperator.evaluate(true, false, true));
		assertFalse(thresholdOperator.evaluate(true, true, false));
		assertTrue(thresholdOperator.evaluate(true, true, true));
	}
}