/*
 * CM4thStackEventTest.java
 *
 * 05.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the class "CM4thStackEvent".
 *
 * @author ollie
 */

public class CM4thStackEventTest {

	private static final CM4thStackEventType TYPE = CM4thStackEventType.POP;
	private static final CM4thStackEventType TYPE_DIFF = CM4thStackEventType.PUSH;
	private static final Object VALUE = new Long(4711L);

	private CM4thStackEvent unitUnderTest = null;

	@Before
	public void setUp() throws Exception {
		this.unitUnderTest = new CM4thStackEvent(TYPE, VALUE);
	}

	@Test
	public void testConstructorSetsTheTypeCorrectly() throws Exception {
		assertEquals(TYPE, this.unitUnderTest.getType());
	}

	@Test
	public void testConstructorSetsTheValueCorrectly() throws Exception {
		assertEquals(VALUE, this.unitUnderTest.getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsAnExceptionPassingANullPointerAsType() throws Exception {
		new CM4thStackEvent(null, VALUE);
	}

	@Test
	public void testEqualsReturnsFalsePassingAnObjectWithDifferentType() throws Exception {
		assertFalse(this.unitUnderTest.equals(new CM4thStackEvent(TYPE_DIFF, VALUE)));
	}

	@Test
	public void testEqualsReturnsFalsePassingAnObjectWithDifferentValue() throws Exception {
		assertFalse(this.unitUnderTest.equals(new CM4thStackEvent(TYPE, "" + VALUE)));
	}

	@Test
	public void testEqualsReturnsFalsePassingAnObjectWithNullValue() throws Exception {
		assertFalse(this.unitUnderTest.equals(new CM4thStackEvent(TYPE, null)));
	}

	@Test
	public void testEqualsReturnsFalsePassingANullPointer() throws Exception {
		assertFalse(this.unitUnderTest.equals(null));
	}

	@Test
	public void testEqualsReturnsTruePassingAnEqualsObject() throws Exception {
		assertTrue(this.unitUnderTest.equals(new CM4thStackEvent(TYPE, VALUE)));
	}

	@Test
	public void testEqualsReturnsTruePassingObjectsWithNullValues() throws Exception {
		assertTrue(new CM4thStackEvent(TYPE, null).equals(new CM4thStackEvent(TYPE, null)));
	}

	@Test
	public void testEqualsReturnsFalsePassingTheSameObject() throws Exception {
		assertTrue(this.unitUnderTest.equals(this.unitUnderTest));
	}

	@Test
	public void testEqualsReturnsFalsePassingAParameterOfAWrongClass() throws Exception {
		assertFalse(this.unitUnderTest.equals(":op"));
	}

	@Test
	public void testHashCodeReturnsEqualValuesOnEqualObjects() throws Exception {
		assertEquals(this.unitUnderTest.hashCode(), new CM4thStackEvent(TYPE, VALUE).hashCode());
	}

	@Test
	public void testHashCodeReturnsEqualValuesOnEqualObjectsWithNullValues() throws Exception {
		assertEquals(new CM4thStackEvent(TYPE, null).hashCode(), new CM4thStackEvent(TYPE, null).hashCode());
	}

	@Test
	public void testHashCodeReturnsEqualValuesOnSameObject() throws Exception {
		assertEquals(this.unitUnderTest.hashCode(), this.unitUnderTest.hashCode());
	}

	@Test
	public void testToStringReturnsTheCorrectStringPresentation() throws Exception {
		assertEquals("Type=" + TYPE + ",Value=" + VALUE, this.unitUnderTest.toString());
	}

	@Test
	public void testToStringReturnsTheCorrectStringPresentationForNullValue() throws Exception {
		assertEquals("Type=" + TYPE + ",Value=null", new CM4thStackEvent(TYPE, null).toString());
	}

}