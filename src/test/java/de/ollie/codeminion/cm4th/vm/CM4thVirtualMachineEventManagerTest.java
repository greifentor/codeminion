/*
 * CM4thVirtualMachineEventManagerTest.java
 *
 * 09.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the class "CM4thVirtualMachineEventManager".
 *
 * @author ollie
 */

public class CM4thVirtualMachineEventManagerTest {

	private static final String MESSAGE = "message";

	private CM4thVirtualMachineListener listener0 = null;
	private CM4thVirtualMachineListener listener1 = null;
	private Logger logger = null;
	private CM4thVirtualMachineEventManager unitUnderTest = null;

	@Before
	public void setUp() throws Exception {
		this.listener0 = mock(CM4thVirtualMachineListener.class);
		this.listener1 = mock(CM4thVirtualMachineListener.class);
		this.logger = CM4thVirtualMachineEventManager.log;
		CM4thVirtualMachineEventManager.log = mock(Logger.class);
		this.unitUnderTest = new CM4thVirtualMachineEventManager();
	}

	@After
	public void tearDown() throws Exception {
		CM4thVirtualMachineEventManager.log = this.logger;
	}

	@Test
	public void testAddCM4thVirtualMachineListenerAddsThePassedListenerCorrectly() throws Exception {
		this.unitUnderTest.addCM4thVirtualMachineListener(this.listener0);
		this.unitUnderTest.fireStandardIOOutput(MESSAGE);
		verify(this.listener0, times(1)).printed(MESSAGE);
	}

	@Test
	public void testAddCM4thVirtualMachineListenerAddsThePassedListenerOnceIfCalledTwice() throws Exception {
		this.unitUnderTest.addCM4thVirtualMachineListener(this.listener0);
		this.unitUnderTest.addCM4thVirtualMachineListener(this.listener0);
		this.unitUnderTest.fireStandardIOOutput(MESSAGE);
		verify(this.listener0, times(1)).printed(MESSAGE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddCM4thVirtualMachineListenerThrowsAnExceptionPassingANullPointer() throws Exception {
		this.unitUnderTest.addCM4thVirtualMachineListener(null);
	}

	@Test
	public void testFireStandardIOOutputProceedsIfAnErrorOccursWhileListenerNotification() throws Exception {
		RuntimeException e = new RuntimeException(":op");
		doThrow(e).when(this.listener0).printed(MESSAGE);
		this.unitUnderTest.addCM4thVirtualMachineListener(this.listener0);
		this.unitUnderTest.addCM4thVirtualMachineListener(this.listener1);
		this.unitUnderTest.fireStandardIOOutput(MESSAGE);
		verify(CM4thVirtualMachineEventManager.log, times(1)).error("while executing CM4thVirtualMachineListener.", e);
		verify(this.listener1, times(1)).printed(MESSAGE);
	}

	@Test
	public void testRemoveCM4thVirtualMachineListenerRemovesThePassedListenerCorrectly() throws Exception {
		this.unitUnderTest.addCM4thVirtualMachineListener(this.listener0);
		this.unitUnderTest.fireStandardIOOutput(MESSAGE);
		verify(this.listener0, times(1)).printed(MESSAGE);
		this.unitUnderTest.removeCM4thVirtualMachineListener(this.listener0);
		this.unitUnderTest.fireStandardIOOutput(MESSAGE);
		verify(this.listener0, times(1)).printed(MESSAGE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveCM4thVirtualMachineListenerThrowsAnExceptionPassingANullPointer() throws Exception {
		this.unitUnderTest.removeCM4thVirtualMachineListener(null);
	}

}