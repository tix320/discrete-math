package discretemath.bool.expression.exception;

public class InvalidArgumentCountException extends RuntimeException {

	public InvalidArgumentCountException(String message) {
		super(message);
	}

	public static void checkExact(int expectedCount, int actualCount) {
		if (actualCount != expectedCount) {
			throw new InvalidArgumentCountException("Expected: %s. Actual: %s".formatted(expectedCount, actualCount));
		}
	}

	public static void checkGTE(int expectedCount, int actualCount) {
		if (actualCount < expectedCount) {
			throw new InvalidArgumentCountException("Expected: ≥ %s. Actual: %s".formatted(expectedCount, actualCount));
		}
	}

	public static void checkLTE(int expectedCount, int actualCount) {
		if (actualCount < expectedCount) {
			throw new InvalidArgumentCountException("Expected: ≤ %s. Actual: %s".formatted(expectedCount, actualCount));
		}
	}
}
