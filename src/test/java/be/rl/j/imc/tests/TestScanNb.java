package be.rl.j.imc.tests;

import org.junit.Test;

import be.rl.j.imc.utils.InputUtils;

public class TestScanNb extends ATestJIMC {

	@Test
	public void test01() {
		test("1", 1.);
	}

	@Test
	public void test02() {
		test("2", 2.);
	}

	@Test
	public void test03() {
		test("2 ", 2.);
	}

	@Test
	public void test04() {
		test(" 2 ", 2.);
	}

	@Test
	public void test05() {
		test("  2.5 ", 2.5);
	}

	@Test
	public void test06() {
		test(" 2,5 ", 2.5);
	}

	@Test
	public void test07() {
		test(" 2,5, ", 2.5);
	}

	@Test
	public void test08() {
		test("&&ss 2,5, ,,,s", 2.5);
	}

	@Test
	public void test09() {
		test("2,5", 25., true);
	}

	@Test
	public void test10() {
		test("25.1", 25., true);
	}

	private void test(String test, Double expected) {
		test(test, expected, false);
	}

	private void test(String test, Double expected, boolean mustFails) {
		try {
			Double actual = scansMyNb(test);
			int len = 20;
			if (test.length() < len) {
				String testSpaces = String.format("%" + len + "s", test).substring(0, len - test.length())
						.replaceAll(".", ".");
				System.out.print(String.format("Testing (%s) scansMyNb : \"%" + len + "s\" --> result : \"%2.2f\"",
						mustFails ? "invert" : "normal", testSpaces + test, expected));
			}
			checkFails(expected, actual, mustFails);
		} catch (Exception e) {
			checkFails(e, mustFails);
		}
	}

	private Double scansMyNb(String str) {
		return InputUtils.testInputMyNb(str);
	}
}