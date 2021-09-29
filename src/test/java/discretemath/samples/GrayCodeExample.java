package discretemath.samples;

import discretemath.combination.GrayCode;

public class GrayCodeExample {

	public static void main(String[] args) {
		GrayCode combinations = GrayCode.forN(3);

		System.out.println(combinations);
	}
}
