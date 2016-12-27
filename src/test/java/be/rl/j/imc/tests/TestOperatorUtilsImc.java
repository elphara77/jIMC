package be.rl.j.imc.tests;

import org.junit.Test;

import be.rl.j.imc.utils.ImcOperatorUtils;

public class TestOperatorUtilsImc extends ATestJIMC {

	@Test
	public void test01() {
		test(100., 100., 0., true);
	}

	@Test
	public void test02() {
		test(100., 100., 0.01);
	}

	private void test(Double weight, Double height, Double expected) {
		test(weight, height, expected, false);
	}

	private void test(Double weight, Double height, Double expected, Boolean mustFails) {
		try {
			System.out.println("Expected : " + expected);
			Double actual = ImcOperatorUtils.imcOperator.applyAsDouble(weight, height);
			System.out.println("Actual : " + actual);
			checkFails(expected, actual, mustFails);
		} catch (Exception e) {
			checkFails(e, mustFails);
		}
	}
}