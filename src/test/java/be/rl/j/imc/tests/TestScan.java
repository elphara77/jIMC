package be.rl.j.imc.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import be.rl.j.imc.utils.InputUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestScan {

	private class ResultRule extends TestWatcher {
		@Override
		protected void succeeded(Description description) {
			System.out.println(String.format("\tOk :) \t (%s)", description));
		}

		@Override
		protected void failed(Throwable e, Description description) {
			System.out.println(String.format("\tKO :( \t (%s)", description));
		}
	}

	@Rule
	public ResultRule simpleRule = new ResultRule();

	private static int count = 0;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private String title;

	@Before
	public void setUp() throws Exception {
		title = String.format("Test #%2d : ", ++count);
		System.out.print(title);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test01() {
		testScanMyNb("1", 1.);
	}

	@Test
	public void test02() {
		testScanMyNb("2", 2.);
	}

	@Test
	public void test03() {
		testScanMyNb("2 ", 2.);
	}

	@Test
	public void test04() {
		testScanMyNb(" 2 ", 2.);
	}

	@Test
	public void test05() {
		testScanMyNb("  2.5 ", 2.5);
	}

	@Test
	public void test06() {
		testScanMyNb(" 2,5 ", 2.5);
	}

	@Test
	public void test07() {
		testScanMyNb(" 2,5, ", 2.5);
	}

	@Test
	public void test08() {
		testScanMyNb("&&ss 2,5, ,,,s", 2.5);
	}

	@Test
	public void test09() {
		testScanMyNb("2,5", 25., true);
	}

	@Test
	public void test10() {
		testScanMyNb("25.1", 25., true);
	}

	private void testScanMyNb(String test, Double expected) {
		testScanMyNb(test, expected, false);
	}

	private void testScanMyNb(String test, Double expected, boolean mustFails) {
		try {
			Double actual = scansMyNb(test);
			int len = 20;
			if (test.length() < len) {
				String testSpaces = String.format("%" + len + "s", test).substring(0, len - test.length())
						.replaceAll(".", ".");
				System.out.print(String.format("Testing (%s) scansMyNb : \"%" + len + "s\" --> result : \"%2.2f\"",
						mustFails ? "invert" : "normal", testSpaces + test, expected));
			}
			if (mustFails) {
				Assert.assertNotEquals(expected, actual);
			} else {
				Assert.assertEquals(expected, actual);
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	private Double scansMyNb(String str) {
		return InputUtils.testInputMyNb(str);
	}
}