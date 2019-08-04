/*
 * CM4thVirtualMachineTest.java
 *
 * 07.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the CM4th virtual machine.
 *
 * @author ollie
 *
 */

public class CM4thVirtualMachineTest {

	private CM4thVirtualMachine unitUnderTest = null;
	private CM4thVirtualMachineListener listener = null;

	@Before
	public void setUp() throws Exception {
		this.listener = mock(CM4thVirtualMachineListener.class);
		this.unitUnderTest = new CM4thVirtualMachine();
	}

	@Test
	public void testInterpretsAddsADoubleAndALongNumber() throws Exception {
		this.unitUnderTest.interpret(new Double(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ADD);
		assertEquals(new Double(5), this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsAddsADoubleAndAString() throws Exception {
		this.unitUnderTest.interpret(new Double(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret("3");
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ADD);
		assertEquals("2.03", this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsAddsALongAndADoubleNumber() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Double(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ADD);
		assertEquals(new Double(5), this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsAddsALongAndAString() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret("3");
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ADD);
		assertEquals("23", this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsAddsTwoDoubleNumbers() throws Exception {
		this.unitUnderTest.interpret(new Double(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Double(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ADD);
		assertEquals(new Double(5), this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsAddsTwoLongNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ADD);
		assertEquals(new Long(5), this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsDivideADoubleAndALongNumber() throws Exception {
		this.unitUnderTest.interpret(new Double(6));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.DIVIDE);
		assertEquals(new Double(2), this.unitUnderTest.getTop());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInterpretsDivideADoubleAndAString() throws Exception {
		this.unitUnderTest.interpret(new Double(6));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret("3");
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.DIVIDE);
	}

	@Test
	public void testInterpretsDivideALongAndADoubleNumber() throws Exception {
		this.unitUnderTest.interpret(new Long(6));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Double(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.DIVIDE);
		assertEquals(new Double(2), this.unitUnderTest.getTop());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInterpretsDivideALongAndAString() throws Exception {
		this.unitUnderTest.interpret(new Long(6));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret("3");
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.DIVIDE);
	}

	@Test
	public void testInterpretsDivideTwoDoubleNumbers() throws Exception {
		this.unitUnderTest.interpret(new Double(6));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Double(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.DIVIDE);
		assertEquals(new Double(2), this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsDivideTwoLongNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(6));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.DIVIDE);
		assertEquals(new Long(2), this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsDupDuplicatesTheTopOfTheStackTypeDouble() throws Exception {
		this.unitUnderTest.interpret(new Double(3.0));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.DUP);
		this.unitUnderTest.interpret(CM4thCommand.ADD);
		assertEquals(new Double(6.0), this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsDupDuplicatesTheTopOfTheStackTypeString() throws Exception {
		this.unitUnderTest.interpret("#");
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.DUP);
		this.unitUnderTest.interpret(CM4thCommand.ADD);
		assertEquals("##", this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsDupDuplicatesTheTopOfTheStackTypeLong() throws Exception {
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.DUP);
		this.unitUnderTest.interpret(CM4thCommand.ADD);
		assertEquals(new Long(6), this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsEqualComparesTwoDifferentNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISEQUAL);
		assertEquals(Boolean.FALSE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsEqualComparesTwoEqualNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISEQUAL);
		assertEquals(Boolean.TRUE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsGreaterComparesTwoDifferentNumbers1() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISGREATER);
		assertEquals(Boolean.FALSE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsGreaterComparesTwoDifferentNumbers2() throws Exception {
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISGREATER);
		assertEquals(Boolean.TRUE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsGreaterComparesTwoEqualNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISGREATER);
		assertEquals(Boolean.FALSE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsGreaterOrEqualComparesTwoDifferentNumbers1() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISGREATEROREQUAL);
		assertEquals(Boolean.FALSE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsGreaterOrEqualComparesTwoDifferentNumbers2() throws Exception {
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISGREATEROREQUAL);
		assertEquals(Boolean.TRUE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsIsGreaterOrEqualComparesTwoEqualNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISGREATEROREQUAL);
		assertEquals(Boolean.TRUE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsLesserComparesTwoDifferentNumbers1() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISLESSER);
		assertEquals(Boolean.TRUE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsLesserComparesTwoDifferentNumbers2() throws Exception {
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISLESSER);
		assertEquals(Boolean.FALSE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsLesserComparesTwoEqualNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISLESSER);
		assertEquals(Boolean.FALSE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsLesserOrEqualComparesTwoDifferentNumbers1() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISLESSEROREQUAL);
		assertEquals(Boolean.TRUE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsLesserOrEqualComparesTwoDifferentNumbers2() throws Exception {
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISLESSEROREQUAL);
		assertEquals(Boolean.FALSE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsLesserOrEqualComparesTwoEqualNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISLESSEROREQUAL);
		assertEquals(Boolean.TRUE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsNotEqualComparesTwoDifferentNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISNOTEQUAL);
		assertEquals(Boolean.TRUE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsIsNotEqualComparesTwoEqualNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.ISNOTEQUAL);
		assertEquals(Boolean.FALSE, this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsMultipliesADoubleAndALongNumber() throws Exception {
		this.unitUnderTest.interpret(new Double(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.MULTIPLY);
		assertEquals(new Double(6), this.unitUnderTest.getTop());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInterpretsMultipliesADoubleAndAString() throws Exception {
		this.unitUnderTest.interpret(new Double(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret("3");
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.MULTIPLY);
	}

	@Test
	public void testInterpretsMultipliesALongAndADoubleNumber() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Double(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.MULTIPLY);
		assertEquals(new Double(6), this.unitUnderTest.getTop());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInterpretsMultipliesALongAndAString() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret("3");
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.MULTIPLY);
	}

	@Test
	public void testInterpretsMultipliesTwoDoubleNumbers() throws Exception {
		this.unitUnderTest.interpret(new Double(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Double(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.MULTIPLY);
		assertEquals(new Double(6), this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsMultipliesTwoLongNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.MULTIPLY);
		assertEquals(new Long(6), this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsPrintPrintsTheTopObject() throws Exception {
		this.unitUnderTest.addCM4thVirtualMachineListener(this.listener);
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.PRINT);
		verify(this.listener, times(1)).printed("2");
	}

	@Test
	public void testInterpretsSubtractADoubleAndALongNumber() throws Exception {
		this.unitUnderTest.interpret(new Double(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.SUBTRACT);
		assertEquals(new Double(-1), this.unitUnderTest.getTop());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInterpretsSubtractADoubleAndAString() throws Exception {
		this.unitUnderTest.interpret(new Double(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret("3");
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.SUBTRACT);
	}

	/**
	 * @changed OLI 07.01.2013 - Added.
	 */
	@Test
	public void testInterpretsSubtractALongAndADoubleNumber() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Double(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.SUBTRACT);
		assertEquals(new Double(-1), this.unitUnderTest.getTop());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInterpretsSubtractALongAndAString() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret("3");
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.SUBTRACT);
	}

	@Test
	public void testInterpretsSubtractTwoDoubleNumbers() throws Exception {
		this.unitUnderTest.interpret(new Double(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Double(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.SUBTRACT);
		assertEquals(new Double(-1), this.unitUnderTest.getTop());
	}

	@Test
	public void testInterpretsSubtractTwoLongNumbers() throws Exception {
		this.unitUnderTest.interpret(new Long(2));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(new Long(3));
		this.unitUnderTest.interpret(CM4thCommand.PUSH);
		this.unitUnderTest.interpret(CM4thCommand.SUBTRACT);
		assertEquals(new Long(-1), this.unitUnderTest.getTop());
	}

	@Test(expected = EmptyStackException.class)
	public void testGetTopThrowsAnExceptionOnAnEmptyStack() throws Exception {
		this.unitUnderTest.getTop();
	}

	@Test
	public void testStartProgramRunsAListOfCommandsCorrectly() throws Exception {
		CM4thProgram program = new CM4thProgram();
		program.addCommandsToWord("", new Long(4), CM4thCommand.PUSH, new Long(3), CM4thCommand.PUSH,
				CM4thCommand.MULTIPLY);
		this.unitUnderTest.startProgram(program);
		assertEquals(new Long(12), this.unitUnderTest.getTop());
	}

	@Test
	public void testStartProgramRunsAProgrammWithALoop() throws Exception {
		CM4thProgram program = new CM4thProgram();
		program.addCommandsToWord("", new Long(1), CM4thCommand.PUSH, CM4thCommand.NOP, CM4thCommand.DUP,
				CM4thCommand.ADD, CM4thCommand.DUP, new Long(10), CM4thCommand.PUSH, CM4thCommand.ISGREATER,
				new JumpTarget(2), CM4thCommand.JUMPONFALSE);
		this.unitUnderTest.startProgram(program);
		assertEquals(new Long(16), this.unitUnderTest.getTop());
	}

	@Test
	public void testStartProgramRunsAProgrammWithJumpOnCondition() throws Exception {
		CM4thProgram program = new CM4thProgram();
		program.addCommandsToWord("", Boolean.FALSE, CM4thCommand.PUSH, new JumpTarget(8), CM4thCommand.JUMPONFALSE,
				new Long(3), CM4thCommand.PUSH, new JumpTarget(10), CM4thCommand.JUMP, new Long(4), CM4thCommand.PUSH,
				CM4thCommand.NOP);
		this.unitUnderTest.startProgram(program);
		assertEquals(new Long(4), this.unitUnderTest.getTop());
	}

	@Test
	public void testStartProgramRunsAProgrammWithJumpNotOnCondition() throws Exception {
		CM4thProgram program = new CM4thProgram();
		program.addCommandsToWord("", Boolean.TRUE, CM4thCommand.PUSH, new JumpTarget(8), CM4thCommand.JUMPONFALSE,
				new Long(3), CM4thCommand.PUSH, new JumpTarget(10), CM4thCommand.JUMP, new Long(4), CM4thCommand.PUSH,
				CM4thCommand.NOP);
		this.unitUnderTest.startProgram(program);
		assertEquals(new Long(3), this.unitUnderTest.getTop());
	}

	@Test
	public void testStartProgramRunsAProgrammWithSubRoutine() throws Exception {
		CM4thProgram program = new CM4thProgram();
		program.addCommandsToWord("", new Long(4), CM4thCommand.PUSH, new MethodCall("double"), CM4thCommand.JUMPSUB);
		program.addCommandsToWord("double", CM4thCommand.DUP, CM4thCommand.ADD, CM4thCommand.RETURN);
		this.unitUnderTest.startProgram(program);
		assertEquals(new Long(8), this.unitUnderTest.getTop());
	}

	@Test
	public void testStartProgramRunsAProgrammWithUnconditionalJumps() throws Exception {
		CM4thProgram program = new CM4thProgram();
		program.addCommandsToWord("", new Long(4), CM4thCommand.PUSH, new JumpTarget(6), CM4thCommand.JUMP, new Long(3),
				CM4thCommand.PUSH, CM4thCommand.NOP);
		this.unitUnderTest.startProgram(program);
		assertEquals(new Long(4), this.unitUnderTest.getTop());
	}

}