/*
 * JumpTargetTest.java
 *
 * 08.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the class <CODE>JumpTarget</CODE>.
 *
 * @author ollie
 */

public class JumpTargetTest {

	private static final int ADDRESS = 4711;

	private JumpTarget unitUnderTest = null;

	@Before
	public void setUp() throws Exception {
		this.unitUnderTest = new JumpTarget(ADDRESS);
	}

	@Test
	public void testEqualsReturnsFalsePassingAnObjectWithADifferentAddress() throws Exception {
		assertFalse(this.unitUnderTest.equals(new JumpTarget(ADDRESS + 1)));
	}

	@Test
	public void testEqualsReturnsFalsePassingAnObjectWithTheWrongType() throws Exception {
		assertFalse(this.unitUnderTest.equals(":op"));
	}

	@Test
	public void testEqualsReturnsFalsePassingANullPointer() throws Exception {
		assertFalse(this.unitUnderTest.equals(null));
	}

	@Test
	public void testEqualsReturnsTruePassingAnEqualJumpTarget() throws Exception {
		assertTrue(this.unitUnderTest.equals(new JumpTarget(ADDRESS)));
	}

	@Test
	public void testEqualsReturnsTruePassingTheSameJumpTarget() throws Exception {
		assertTrue(this.unitUnderTest.equals(this.unitUnderTest));
	}

	@Test
	public void testHashCodeReturnsEqualValueForEqualJumpTargets() throws Exception {
		assertEquals(this.unitUnderTest.hashCode(), new JumpTarget(ADDRESS).hashCode());
	}

	@Test
	public void testHashCodeReturnsEqualValueForTheSameJumpTarget() throws Exception {
		assertEquals(this.unitUnderTest.hashCode(), this.unitUnderTest.hashCode());
	}

	@Test
	public void testSetAddressSetsThePassedAddressCorrectly() throws Exception {
		this.unitUnderTest.setAddress(ADDRESS + 1);
		assertEquals(ADDRESS + 1, this.unitUnderTest.getAddress());
	}

	@Test
	public void testSimpleConstructorInitsAddressWithMinusOne() throws Exception {
		this.unitUnderTest = new JumpTarget();
		assertEquals(-1, this.unitUnderTest.getAddress());
	}

	@Test
	public void testToStringReturnsTheRightString() throws Exception {
		assertEquals("Address=" + ADDRESS, this.unitUnderTest.toString());
	}

}