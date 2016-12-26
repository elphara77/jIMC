package be.rl.j.imc.tests;

import org.junit.Test;

import be.rl.j.imc.utils.InputUtils;

public class TestScanQuery extends ATestJIMC {

	@Test
	public void test01() {
		test("oui", 1);
	}

	@Test
	public void test02() {
		test("OUI", 1);
	}

	@Test
	public void test03() {
		test("o", 1);
	}

	@Test
	public void test04() {
		test("O", 1);
	}

	@Test
	public void test05() {
		test("yes", 1);
	}

	@Test
	public void test06() {
		test("Yes", 1);
	}

	@Test
	public void test07() {
		test("Non", -1);
	}

	@Test
	public void test08() {
		test("Yeah", -1, true);
	}

	@Test
	public void test09() {
		test("Oui", -1, true);
	}

	@Test
	public void test10() {
		test(null, 0, true);
	}

	private void test(String test, int expected) {
		test(test, expected, false);
	}

	private void test(String test, int expected, boolean mustFails) {
		try {
			int actual = InputUtils.testInputMyQuery("==> Voulez-vous continuer oui / non ? ", test, 1, "oui", "o",
					"yes", "ja", "j", "y", "yeah", -1, "non", "n", "no", "nee", "neen");

			checkFails(expected, actual, mustFails);
		} catch (Exception e) {
			checkFails(e, mustFails);
		}
	}
}