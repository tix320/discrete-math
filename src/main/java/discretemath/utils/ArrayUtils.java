package discretemath.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {

	public static int getIndexOfMax(int[] array) {
		if (array == null || array.length == 0) return -1;

		int largest = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > array[largest]) {
				largest = i;
			}
		}
		return largest;
	}

	public static List<Integer> getIndexesOfMax(int[] array) {
		List<Integer> indexes = new ArrayList<>();

		int max = array[0];
		indexes.add(0);
		for (int i = 1; i < array.length; ++i) {
			if (array[i] == max) {
				indexes.add(i);
			} else if (array[i] > max) {
				indexes.clear();
				indexes.add(i);
				max = array[i];
			}
		}

		return indexes;
	}
}
