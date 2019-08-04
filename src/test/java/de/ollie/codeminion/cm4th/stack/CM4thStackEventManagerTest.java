/*
 * CM4thStackEventManagerTest.java
 *
 * 07.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.stack;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the class <CODE>CM4thStackEventManager</CODE>.
 *
 * @author ollie
 *
 */

public class CM4thStackEventManagerTest {

	private static final Object VALUE = 42L;

	private CM4thStackListener listener0 = null;
	private CM4thStackListener listener1 = null;
	private CM4thStackEventManager unitUnderTest = null;
	private Logger logger = null;

	/**
	 * @changed OLI 07.01.2013 - Added.
	 */
	@Before
	public void setUp() throws Exception {
		this.listener0 = mock(CM4thStackListener.class);
		this.listener1 = mock(CM4thStackListener.class);
		this.logger = CM4thStackEventManager.log;
		CM4thStackEventManager.log = mock(Logger.class);
		this.unitUnderTest = new CM4thStackEventManager();
	}

	/**
	 * @changed OLI 07.01.2013 - Added.
	 */
	@After
	public void tearDown() throws Exception {
		CM4thStackEventManager.log = this.logger;
	}

	/**
	 * @changed OLI 07.01.2013 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testAddCM4thStackListenerAddsTheListenerToTheManager() throws Exception {
		this.unitUnderTest.firePushEvent(VALUE);
		verify(this.listener0, times(0)).stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, VALUE));
		this.unitUnderTest.addCM4thStackListener(this.listener0);
		this.unitUnderTest.firePushEvent(VALUE);
		verify(this.listener0, times(1)).stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, VALUE));
	}

	/**
	 * @changed OLI 07.01.2013 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testAddCM4thStackListenerAddsTheSameListenerOnceOnly() throws Exception {
		this.unitUnderTest.firePushEvent(VALUE);
		verify(this.listener0, times(0)).stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, VALUE));
		this.unitUnderTest.addCM4thStackListener(this.listener0);
		this.unitUnderTest.addCM4thStackListener(this.listener0);
		this.unitUnderTest.addCM4thStackListener(this.listener0);
		this.unitUnderTest.firePushEvent(VALUE);
		verify(this.listener0, times(1)).stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, VALUE));
	}

	/**
	 * @changed OLI 07.01.2013 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddCM4thStackListenerThrowsAnExceptionPassingANullPointer() throws Exception {
		this.unitUnderTest.addCM4thStackListener(null);
	}

	/**
	 * @changed OLI 07.01.2013 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testFirePopEventProceedsExecutionIfAnErrorOccursWhileCallingAListener() throws Exception {
		Exception e = new Exception(":op");
		doThrow(e).when(this.listener0).stackChanged(new CM4thStackEvent(CM4thStackEventType.POP, VALUE));
		this.unitUnderTest.addCM4thStackListener(this.listener0);
		this.unitUnderTest.addCM4thStackListener(this.listener1);
		this.unitUnderTest.firePopEvent(VALUE);
		verify(CM4thStackEventManager.log, times(1)).error("while executing CM4thStackListener.", e);
		verify(this.listener1, times(1)).stackChanged(new CM4thStackEvent(CM4thStackEventType.POP, VALUE));
	}

	/**
	 * @changed OLI 07.01.2013 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFirePopEventThrowsAnExceptionPassingANullPointer() throws Exception {
		this.unitUnderTest.firePopEvent(null);
	}

	/**
	 * @changed OLI 07.01.2013 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testFirePushEventProceedsExecutionIfAnErrorOccursWhileCallingAListener() throws Exception {
		Exception e = new Exception(":op");
		doThrow(e).when(this.listener0).stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, VALUE));
		this.unitUnderTest.addCM4thStackListener(this.listener0);
		this.unitUnderTest.addCM4thStackListener(this.listener1);
		this.unitUnderTest.firePushEvent(VALUE);
		verify(CM4thStackEventManager.log, times(1)).error("while executing CM4thListener.", e);
		verify(this.listener1, times(1)).stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, VALUE));
	}

	/**
	 * @changed OLI 07.01.2013 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFirePushEventThrowsAnExceptionPassingANullPointer() throws Exception {
		this.unitUnderTest.firePushEvent(null);
	}

	/**
	 * @changed OLI 07.01.2013 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testRemoveCM4thStackListenerDeletesTheListenerFromTheManager() throws Exception {
		this.unitUnderTest.addCM4thStackListener(this.listener0);
		this.unitUnderTest.firePushEvent(VALUE);
		verify(this.listener0, times(1)).stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, VALUE));
		this.unitUnderTest.removeCM4thStackListener(this.listener0);
		this.unitUnderTest.firePushEvent(VALUE);
		verify(this.listener0, times(1)).stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, VALUE));
	}

	/**
	 * @changed OLI 07.01.2013 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveCM4thStackListenerThrowsAnExceptionPassingANullPointer() throws Exception {
		this.unitUnderTest.removeCM4thStackListener(null);
	}

}