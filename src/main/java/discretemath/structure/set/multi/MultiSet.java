package discretemath.structure.set.multi;

import java.util.Iterator;

import discretemath.structure.set.Set;
import discretemath.structure.tuple.Pair;

public interface MultiSet<T> extends Set<T> {

	MultiSet<T> sumWith(MultiSet<T> set);

	T mode();

	int itemCount(T item);

	Iterator<Pair<T, Integer>> withCountIterator();
}
