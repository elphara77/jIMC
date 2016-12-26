package be.rl.j.imc.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class ATestJIMC {

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
		System.out.println(title);
	}

	@After
	public void tearDown() throws Exception {
	}

	void checkFails(Object expected, Object actual, boolean mustFails) {
		if (mustFails) {
			Assert.assertNotEquals(expected, actual);
		} else {
			Assert.assertEquals(expected, actual);
		}
	}

	void checkNear(Double actual, boolean mustFails) {
		if (mustFails) {
			Assert.assertFalse(Math.abs(actual) <= .1);
		} else {
			Assert.assertTrue(Math.abs(actual) <= .1);
		}
	}

	void checkFails(Exception e, boolean mustFails) {
		if (mustFails) {
			System.out.println(
					String.format("Exception %s (%s) catched but must fails", e.getClass().getName(), e.getMessage()));
		} else {
			Assert.fail(e.getMessage());
		}
	}
}