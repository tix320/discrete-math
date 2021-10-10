package discretemath.structure.function;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticFunctionTest {

	@Test
	void isOneToOne() {
		StaticFunction<Integer, Integer> function = StaticFunction.from(Map.of(1, 2, 4, 5, 6, 7));

		assertTrue(function.isOneToOne());

		StaticFunction<Integer, Integer> function2 = StaticFunction.from(Map.of(1, 2, 4, 2, 6, 7));

		assertFalse(function2.isOneToOne());
	}

	@Test
	void getInverse() {
		StaticFunction<Integer, Integer> function = StaticFunction.from(Map.of(1, 5, 2, 8, 3, 10));

		Function<Integer, Integer> inverseFunction = function.getInverse();

		assertEquals(1, inverseFunction.apply(5));
		assertEquals(2, inverseFunction.apply(8));
		assertEquals(3, inverseFunction.apply(10));
	}
}