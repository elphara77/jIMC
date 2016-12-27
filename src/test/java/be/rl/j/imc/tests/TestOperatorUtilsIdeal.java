package be.rl.j.imc.tests;

import org.junit.Test;

import be.rl.j.imc.utils.ImcOperatorUtils;

public class TestOperatorUtilsIdeal extends ATestJIMC {

	@Test
	public void test01() {
		test(22.5, 1.91, 82.08225);
	}

	@Test
	public void test02() {
		test(22.5, 1.91, 0., true);
	}

	private void test(Double idealImc, Double weight, Double expected) {
		test(idealImc, weight, expected, false);
	}

	private void test(Double idealImc, Double weight, Double expected, Boolean mustFails) {
		try {
			System.out.println("Expected : " + expected);
			Double actual = ImcOperatorUtils.idealWeightOperator.applyAsDouble(idealImc, weight);
			System.out.println("Actual : " + actual);
			checkFails(expected, actual, mustFails);
		} catch (Exception e) {
			checkFails(e, mustFails);
		}
	}
}