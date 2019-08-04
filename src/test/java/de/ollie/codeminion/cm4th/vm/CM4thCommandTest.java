/*
 * CM4thCommand.java
 *
 * 07.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests of the enum "CM4thCommand".
 *
 * @author ollie
 *
 */

public class CM4thCommandTest {

	@Test
	public void testElementCount() throws Exception {
		assertEquals(19, CM4thCommand.values().length);
	}

	@Test
	public void testValueOfADDReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.ADD, CM4thCommand.valueOf("ADD"));
	}

	@Test
	public void testValueOfDIVIDEReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.DIVIDE, CM4thCommand.valueOf("DIVIDE"));
	}

	@Test
	public void testValueOfDUPReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.DUP, CM4thCommand.valueOf("DUP"));
	}

	@Test
	public void testValueOfISEQUALReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.ISEQUAL, CM4thCommand.valueOf("ISEQUAL"));
	}

	@Test
	public void testValueOfISGREATERReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.ISGREATER, CM4thCommand.valueOf("ISGREATER"));
	}

	@Test
	public void testValueOfISGREATEROREQUALReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.ISGREATEROREQUAL, CM4thCommand.valueOf("ISGREATEROREQUAL"));
	}

	@Test
	public void testValueOfISNOTEQUALReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.ISNOTEQUAL, CM4thCommand.valueOf("ISNOTEQUAL"));
	}

	@Test
	public void testValueOfISLESSERReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.ISLESSER, CM4thCommand.valueOf("ISLESSER"));
	}

	@Test
	public void testValueOfISLESSEROREQUALReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.ISLESSEROREQUAL, CM4thCommand.valueOf("ISLESSEROREQUAL"));
	}

	@Test
	public void testValueOfJUMPReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.JUMP, CM4thCommand.valueOf("JUMP"));
	}

	@Test
	public void testValueOfJUMPONFALSEReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.JUMPONFALSE, CM4thCommand.valueOf("JUMPONFALSE"));
	}

	@Test
	public void testValueOfJUMPSUBReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.JUMPSUB, CM4thCommand.valueOf("JUMPSUB"));
	}

	@Test
	public void testValueOfMULTIPLYReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.MULTIPLY, CM4thCommand.valueOf("MULTIPLY"));
	}

	@Test
	public void testValueOfNOPReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.NOP, CM4thCommand.valueOf("NOP"));
	}

	@Test
	public void testValueOfPOPReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.POP, CM4thCommand.valueOf("POP"));
	}

	@Test
	public void testValueOfPRINTReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.PRINT, CM4thCommand.valueOf("PRINT"));
	}

	@Test
	public void testValueOfPUSHReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.PUSH, CM4thCommand.valueOf("PUSH"));
	}

	@Test
	public void testValueOfRETURNReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.RETURN, CM4thCommand.valueOf("RETURN"));
	}

	@Test
	public void testValueOfSUBTRACTReturnsTheRightEnum() throws Exception {
		assertEquals(CM4thCommand.SUBTRACT, CM4thCommand.valueOf("SUBTRACT"));
	}

}