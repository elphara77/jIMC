package be.rl.j.imc.tests;

import org.junit.Test;

import be.rl.j.imc.utils.ImcOperatorUtils;

public class TestOperatorUtilsOverload extends ATestJIMC {

	@Test
	public void test01() {
		test(20., 73., 1.91);
	}

	@Test
	public void test02() {
		test(20., 95., 1.91, true);
	}

	private void test(Double imcMin, Double ownWeight, Double ownHeight) {
		test(imcMin, ownWeight, ownHeight, false);
	}

	private void test(Double imcMin, Double ownWeight, Double ownHeight, Boolean mustFails) {
		try {
			if (mustFails) {
				System.out.println("error or upper than 0.1 Expected");
			} else {
				System.out.println("under a diff of 0.1 Expected");
			}

			Double actual = ImcOperatorUtils.overloadFunction.apply(imcMin).apply(ownWeight).apply(ownHeight);

			System.out.println("Actual : " + actual);
			checkNear(actual, mustFails);
		} catch (Exception e) {
			checkFails(e, mustFails);
		}
	}
}