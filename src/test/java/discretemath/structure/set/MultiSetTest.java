package discretemath.structure.set;

import java.util.Map;

import discretemath.structure.set.multi.MultiSet;
import discretemath.structure.set.multi.SimpleMultiSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiSetTest {

	@Test
	void union() {
		MultiSet<Integer> set1 = SimpleMultiSet.fromCountMap(Map.of(1, 2, 2, 3, 4, 1));
		MultiSet<Integer> set2 = SimpleMultiSet.fromCountMap(Map.of(1, 1, 3, 2, 4, 2));

		assertEquals(SimpleMultiSet.fromCountMap(Map.of(1, 2, 2, 3, 3, 2, 4, 2)), set1.unionWith(set2));

	}

	@Test
	void intersect() {
		MultiSet<Integer> set1 = SimpleMultiSet.fromCountMap(Map.of(1, 2, 2, 3, 4, 3, 5, 0));
		MultiSet<Integer> set2 = SimpleMultiSet.fromCountMap(Map.of(1, 1, 3, 2, 4, 2));

		assertEquals(SimpleMultiSet.fromCountMap(Map.of(1, 1, 4, 2)), set1.intersectWith(set2));
	}

	@Test
	void difference() {
		MultiSet<Integer> set1 = SimpleMultiSet.fromCountMap(Map.of(1, 2, 2, 3, 4, 1));
		MultiSet<Integer> set2 = SimpleMultiSet.fromCountMap(Map.of(1, 1, 3, 2, 4, 2));

		assertEquals(SimpleMultiSet.fromCountMap(Map.of(1, 1, 2, 3)), set1.differenceWith(set2));
	}

	@Test
	void sum() {
		MultiSet<Integer> set1 = SimpleMultiSet.fromCountMap(Map.of(1, 2, 2, 3, 4, 1));
		MultiSet<Integer> set2 = SimpleMultiSet.fromCountMap(Map.of(1, 1, 3, 2, 4, 2));

		assertEquals(SimpleMultiSet.fromCountMap(Map.of(1, 3, 2, 3, 3, 2, 4, 3)), set1.sumWith(set2));
	}
}