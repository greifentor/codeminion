/*
 * CM4thCloneCreatorTest.java
 *
 * 10.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the class "CM4thCloneCreator".
 *
 * @author ollie
 *
 * @changed OLI 10.01.2013 - Added.
 */

public class CM4thCloneCreatorTest {

	private CM4thCloneCreator unitUnderTest = null;

	/**
	 * @changed OLI 21.11.2011 - Added.
	 */
	@Before
	public void setUp() throws Exception {
		this.unitUnderTest = new CM4thCloneCreator();
	}

	/**
	 * @changed OLI 10.01.2013 - Added.
	 */
	@Test
	public void testCreateCloneReturnsACloneForADoubleValue() throws Exception {
		Object o = new Double(47.11);
		Object c = this.unitUnderTest.createClone(o);
		assertTrue(o.equals(c));
		assertNotSame(o, c);
	}

	/**
	 * @changed OLI 10.01.2013 - Added.
	 */
	@Test
	public void testCreateCloneReturnsACloneForALongValue() throws Exception {
		Object o = new Long(4711);
		Object c = this.unitUnderTest.createClone(o);
		assertTrue(o.equals(c));
		assertNotSame(o, c);
	}

	/**
	 * @changed OLI 10.01.2013 - Added.
	 */
	@Test
	public void testCreateCloneReturnsACloneForAStringValue() throws Exception {
		Object o = ":op";
		Object c = this.unitUnderTest.createClone(o);
		assertTrue(o.equals(c));
		assertNotSame(o, c);
	}

	/**
	 * @changed OLI 10.01.2013 - Added.
	 */
	@Test
	public void testCreateCloneReturnsTheValueForAllOtherClasses() throws Exception {
		Object o = new Short((short) 42);
		Object c = this.unitUnderTest.createClone(o);
		assertSame(o, c);
	}

	/**
	 * @changed OLI 10.01.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateCloneThrowsAnExceptionPassingANullPointer() throws Exception {
		this.unitUnderTest.createClone(null);
	}

}