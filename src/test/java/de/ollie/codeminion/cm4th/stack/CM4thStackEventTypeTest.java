/*
 * CM4thStackEventTypeTest.java
 *
 * 05.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.stack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests of the class "CM4thStackEventType".
 *
 * @author ollie
 */

public class CM4thStackEventTypeTest {

	@Test
	public void testElementCount() throws Exception {
		assertEquals(2, CM4thStackEventType.values().length);
	}

	@Test
	public void testValueOf() throws Exception {
		assertEquals(CM4thStackEventType.POP, CM4thStackEventType.valueOf("POP"));
		assertEquals(CM4thStackEventType.PUSH, CM4thStackEventType.valueOf("PUSH"));
	}

}