package be.rl.j.imc.tests;

import static be.rl.j.imc.main.JIMC.scanMyNumber;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TestScanNumberFrench {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1() {
		Double actual = scanMyNumber("1");
		Double expected = 1.;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test2() {
		Double actual = scanMyNumber("2");
		Double expected = 2.;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test3() {
		Double actual = scanMyNumber("2 ");
		Double expected = 2.;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test4() {
		Double actual = scanMyNumber("  2 ");
		Double expected = 2.;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test5() {
		Double actual = scanMyNumber("  2.5 ");
		Double expected = 2.5;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test6() {
		Double actual = scanMyNumber(" 2,5 ");
		Double expected = 2.5;
		Assert.assertEquals(expected, actual);
	}

//	@Test
	public void test7() {
		Double actual = scanMyNumber(" 2,5, ");
		Double expected = 2.5;
		Assert.assertEquals(expected, actual);
	}
}
