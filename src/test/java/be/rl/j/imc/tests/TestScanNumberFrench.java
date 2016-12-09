package be.rl.j.imc.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import be.rl.j.imc.utils.InputUtils;

@FixMethodOrder(MethodSorters.JVM)
public class TestScanNumberFrench {

	private static int count = 0;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String title = "Test #" + ++count;
		System.out.println(title);
		System.out.println(title.replaceAll(".", "-"));
	}

	@After
	public void tearDown() throws Exception {
		String title = "Test #" + count;
		System.out.println(title.replaceAll(".", "-"));
	}

	@Test
	public void test1() {
		Double actual = scansMyNb("1");
		Double expected = 1.;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test2() {
		Double actual = scansMyNb("2");
		Double expected = 2.;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test3() {
		Double actual = scansMyNb("2 ");
		Double expected = 2.;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test4() {
		Double actual = scansMyNb("  2 ");
		Double expected = 2.;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test5() {
		Double actual = scansMyNb("  2.5 ");
		Double expected = 2.5;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test6() {
		Double actual = scansMyNb(" 2,5 ");
		Double expected = 2.5;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test7() {
		Double actual = scansMyNb(" 2,5, ");
		Double expected = 2.5;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test8() {
		Double actual = scansMyNb("&&ss 2,5, ,,,sss");
		Double expected = 2.5;
		Assert.assertEquals(expected, actual);
	}

	private Double scansMyNb(String str) {
		return InputUtils.testInputMyNb(str);
	}
}
