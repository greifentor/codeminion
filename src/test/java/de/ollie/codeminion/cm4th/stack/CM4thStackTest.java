/*
 * CM4thStackTest.java
 *
 * 07.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.stack;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the class "CM4thStack".
 *
 * @author ollie
 */

public class CM4thStackTest {

	private static final String VALUE0 = ":op";
	private static final Long VALUE1 = 42L;
	private static final Boolean VALUE2 = true;

	private CM4thStack unitUnderTest = null;
	private CM4thStackListener listener = null;

	@Before
	public void setUp() throws Exception {
		this.listener = mock(CM4thStackListener.class);
		this.unitUnderTest = new CM4thStack();
	}

	@Test
	public void testPopNotifiesAnEventToARegisteredListener() throws Exception {
		this.unitUnderTest.push(VALUE0);
		this.unitUnderTest.addCM4thStackListener(this.listener);
		this.unitUnderTest.pop();
		verify(this.listener, times(1)).stackChanged(new CM4thStackEvent(CM4thStackEventType.POP, VALUE0));
	}

	@Test
	public void testPopReturnsTheTopMostObjectFromTheStackAnDeletesIdThere() throws Exception {
		this.unitUnderTest.push(VALUE0);
		this.unitUnderTest.push(VALUE1);
		this.unitUnderTest.push(VALUE2);
		assertEquals(VALUE2, this.unitUnderTest.pop());
		assertEquals(VALUE1, this.unitUnderTest.pop());
		assertEquals(VALUE0, this.unitUnderTest.pop());
	}

	@Test(expected = EmptyStackException.class)
	public void testPopThrowsAnExceptionCalledForAnEmptyStack() throws Exception {
		this.unitUnderTest.pop();
	}

	@Test
	public void testPushNotifiesAnEventToARegisteredListener() throws Exception {
		this.unitUnderTest.addCM4thStackListener(this.listener);
		this.unitUnderTest.push(VALUE0);
		verify(this.listener, times(1)).stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, VALUE0));
	}

	@Test
	public void testPushPushesThePassedObjectOnTheTopOfTheStack() throws Exception {
		this.unitUnderTest.push(VALUE0);
		assertEquals(VALUE0, this.unitUnderTest.top());
		this.unitUnderTest.push(VALUE1);
		assertEquals(VALUE1, this.unitUnderTest.top());
		this.unitUnderTest.push(VALUE2);
		assertEquals(VALUE2, this.unitUnderTest.top());
	}

	@Test
	public void testRemovingAListenerEndsEventNotification() throws Exception {
		this.unitUnderTest.addCM4thStackListener(this.listener);
		this.unitUnderTest.push(VALUE0);
		verify(this.listener, times(1)).stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, VALUE0));
		this.unitUnderTest.removeCM4thStackListener(this.listener);
		this.unitUnderTest.push(VALUE0);
		verify(this.listener, times(1)).stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, VALUE0));
	}

	@Test
	public void testSizeCountsTheObjectsOnTheStack() throws Exception {
		this.unitUnderTest.push(VALUE0);
		this.unitUnderTest.push(VALUE1);
		this.unitUnderTest.push(VALUE2);
		assertEquals(3, this.unitUnderTest.size());
	}

	@Test(expected = EmptyStackException.class)
	public void testTopThrowsAnExceptionCalledForAnEmptyStack() throws Exception {
		this.unitUnderTest.top();
	}

	@Test
	public void testToStringReturnsTheRightValue() throws Exception {
		assertEquals("[]", this.unitUnderTest.toString());
	}

}