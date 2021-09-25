package discretemath.bool.expression.exception;

public class InvalidArgumentCountException extends RuntimeException {

	public InvalidArgumentCountException(int expectedCount, int actualCount) {
		super("Expected: %s. Actual: %s".formatted(expectedCount, actualCount));
	}

	public static void checkExact(int expectedCount, int actualCount) {
		if (actualCount != expectedCount) {
			throw new InvalidArgumentCountException(expectedCount, actualCount);
		}
	}

	public static void checkGTE(int expectedCount, int actualCount){
		if (actualCount < expectedCount) {
			throw new InvalidArgumentCountException(expectedCount, actualCount);
		}
	}
}
