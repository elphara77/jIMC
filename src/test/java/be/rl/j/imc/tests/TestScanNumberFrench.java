package be.rl.j.imc.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import be.rl.j.imc.main.JIMC;
import junit.framework.Assert;

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
		Double actual = new JIMC().scanMyNumber("1");
		Double expected = 1.;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test2() {
		Double actual = new JIMC().scanMyNumber("2");
		Double expected = 2.;
		assertTrue(actual.equals(expected));
	}

	@Test
	public void test3() {
		Double actual = new JIMC().scanMyNumber("2 ");
		Double expected = 2.;
		assertTrue(actual.equals(expected));
	}

	@Test
	public void test4() {
		Double actual = new JIMC().scanMyNumber("  2 ");
		Double expected = 2.;
		assertTrue(actual.equals(expected));
	}

	@Test
	public void test5() {
		Double actual = new JIMC().scanMyNumber("  2.5 ");
		Double expected = 2.5;
		assertTrue(actual.equals(expected));
	}

	// @Test
	// public void test6() {
	// Double actual = new JIMC().scanMyNumber(" 2,5 ");
	// Double expected = 2.5;
	// assertTrue(actual.equals(expected));
	// }
	//
	// @Test
	// public void test7() {
	// Double actual = new JIMC().scanMyNumber(" 2,5, ");
	// Double expected = 2.5;
	// assertTrue(actual.equals(expected));
	// }
}
