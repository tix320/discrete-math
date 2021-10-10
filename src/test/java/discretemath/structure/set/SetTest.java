package discretemath.structure.set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SetTest {

	@Test
	void union() {
		Set<Integer> set1 = SimpleSet.of(1, 2, 3);
		Set<Integer> set2 = SimpleSet.of(3, 4, 5);

		assertEquals(SimpleSet.of(1, 2, 3, 4, 5), set1.unionWith(set2));

	}

	@Test
	void intersect() {
		Set<Integer> set1 = SimpleSet.of(1, 2, 3, 4);
		Set<Integer> set2 = SimpleSet.of(3, 4, 5);

		assertEquals(SimpleSet.of(3, 4), set1.intersectWith(set2));
	}

	@Test
	void difference() {
		Set<Integer> set1 = SimpleSet.of(1, 2, 3, 4);
		Set<Integer> set2 = SimpleSet.of(3, 4, 5);

		assertEquals(SimpleSet.of(1, 2), set1.differenceWith(set2));
	}

	@Test
	void symmetricDifference() {
		Set<Integer> set1 = SimpleSet.of(1, 2, 3, 4);
		Set<Integer> set2 = SimpleSet.of(3, 4, 5);

		assertEquals(SimpleSet.of(1, 2, 5), set1.symmetricDifferenceWith(set2));
	}
}