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
public class TestScan2 {

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
	public ResultRule resultRule = new ResultRule();

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
		test("oui", 1);
	}

	@Test
	public void test02() {
		test("non", -1);
	}

	private void test(String test, int expected) {
		test(test, expected, false);
	}

	private void test(String test, int expected, boolean mustFails) {
		try {
			int actual = InputUtils.testInputMyQuery("==> Voulez-vous continuer oui / non ? ", 1, "oui", "o", "yes",
					"ja", "j", "y", -1, "non", "n", "no", "nee", "neen", test);

			if (mustFails) {
				Assert.assertNotEquals(expected, actual);
			} else {
				Assert.assertEquals(expected, actual);
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}