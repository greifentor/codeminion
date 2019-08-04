/*
 * CM4thParserTest.java
 *
 * 08.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import de.ollie.codeminion.cm4th.vm.CM4thCommand;
import de.ollie.codeminion.cm4th.vm.JumpTarget;
import de.ollie.codeminion.cm4th.vm.MethodCall;

/**
 * Tests of the class "CM4thParser".
 *
 * @author ollie
 */

public class CM4thParserTest {

	private static final String MESSAGE = "This is a test :o)";
	private static final String TEST_COND_0 = "FALSE IFTRUE :o) PRINT ELSE :o( PRINT ENDIF";
	private static final String TEST_COND_1 = "FALSE IFTRUE TRUE IFTRUE ELSE :o( PRINT ENDIF " + "ENDIF";
	private static final String TEST_LOOP = "1 LOOP DUP ADD DUP 10 > UNTIL";
	private static final String TEST_PRG = "FALSE\tDoof 42 47.11 ADD\n\"" + MESSAGE + "\" PRINT";
	private static final String TEST_PRG_WITH_NEW_WORD = "\"double\" DEFINE\n\tDUP ADD\nENDDEF"
			+ "\n\n1\nLOOP\n\tdouble\n\tDUP\n\tPRINT\n\t256 >=\nUNTIL\n\"READY.\" PRINT";
	private static final String TEST_PRG_WITH_TABS = "1\nLOOP\n\t1 ADD\n\tDUP\n\tPRINT\n\t"
			+ "DUP 10 >\nUNTIL\n\"READY.\" PRINT";

	private CM4thParser unitUnderTest = null;

	@Before
	public void setUp() throws Exception {
		this.unitUnderTest = new CM4thParser();
	}

	@Test
	public void testParseReturnsTheObjectListCorrectly() throws Exception {
		Object[] result = this.unitUnderTest.parse(TEST_PRG).getMainWord();
		assertNotNull(result);
		assertEquals(Boolean.FALSE, result[0]);
		assertEquals(CM4thCommand.PUSH, result[1]);
		assertEquals("Doof", result[2]);
		assertEquals(CM4thCommand.PUSH, result[3]);
		assertEquals(new Long(42), result[4]);
		assertEquals(CM4thCommand.PUSH, result[5]);
		assertEquals(new Double(47.11), result[6]);
		assertEquals(CM4thCommand.PUSH, result[7]);
		assertEquals(CM4thCommand.ADD, result[8]);
		assertEquals(MESSAGE, result[9]);
		assertEquals(CM4thCommand.PUSH, result[10]);
		assertEquals(CM4thCommand.PRINT, result[11]);
	}

	@Test
	public void testParseReturnsTheObjectListForConditionalProgramCorrectly() throws Exception {
		Object[] result = this.unitUnderTest.parse(TEST_COND_0).getMainWord();
		assertNotNull(result);
		assertEquals(Boolean.FALSE, result[0]);
		assertEquals(CM4thCommand.PUSH, result[1]);
		assertEquals(new JumpTarget(9), result[2]);
		assertEquals(CM4thCommand.JUMPONFALSE, result[3]);
		assertEquals(":o)", result[4]);
		assertEquals(CM4thCommand.PUSH, result[5]);
		assertEquals(CM4thCommand.PRINT, result[6]);
		assertEquals(new JumpTarget(12), result[7]);
		assertEquals(CM4thCommand.JUMP, result[8]);
		assertEquals(":o(", result[9]);
		assertEquals(CM4thCommand.PUSH, result[10]);
		assertEquals(CM4thCommand.PRINT, result[11]);
		assertEquals(CM4thCommand.NOP, result[12]);
	}

	@Test
	public void testParseReturnsTheObjectListForConditionalProgramWithNestingCorrectly() throws Exception {
		Object[] result = this.unitUnderTest.parse(TEST_COND_1).getMainWord();
		assertNotNull(result);
		assertEquals(Boolean.FALSE, result[0]);
		assertEquals(CM4thCommand.PUSH, result[1]);
		assertEquals(new JumpTarget(14), result[2]);
		assertEquals(CM4thCommand.JUMPONFALSE, result[3]);
		assertEquals(Boolean.TRUE, result[4]);
		assertEquals(CM4thCommand.PUSH, result[5]);
		assertEquals(new JumpTarget(10), result[6]);
		assertEquals(CM4thCommand.JUMPONFALSE, result[7]);
		assertEquals(new JumpTarget(13), result[8]);
		assertEquals(CM4thCommand.JUMP, result[9]);
		assertEquals(":o(", result[10]);
		assertEquals(CM4thCommand.PUSH, result[11]);
		assertEquals(CM4thCommand.PRINT, result[12]);
		assertEquals(CM4thCommand.NOP, result[13]);
		assertEquals(CM4thCommand.NOP, result[14]);
	}

	@Test
	public void testParseReturnsTheObjectListForConditionalSymbolEqual() throws Exception {
		assertEquals(CM4thCommand.ISEQUAL, this.unitUnderTest.parse("==").getMainWord()[0]);
	}

	@Test
	public void testParseReturnsTheObjectListForConditionalSymbolGreater() throws Exception {
		assertEquals(CM4thCommand.ISGREATER, this.unitUnderTest.parse(">").getMainWord()[0]);
	}

	@Test
	public void testParseReturnsTheObjectListForConditionalSymbolGreaterOrEquals() throws Exception {
		assertEquals(CM4thCommand.ISGREATEROREQUAL, this.unitUnderTest.parse(">=").getMainWord()[0]);
	}

	@Test
	public void testParseReturnsTheObjectListForConditionalSymbolLesser() throws Exception {
		assertEquals(CM4thCommand.ISLESSER, this.unitUnderTest.parse("<").getMainWord()[0]);
	}

	@Test
	public void testParseReturnsTheObjectListForConditionalSymbolLesserOrEquals() throws Exception {
		assertEquals(CM4thCommand.ISLESSEROREQUAL, this.unitUnderTest.parse("<=").getMainWord()[0]);
	}

	@Test
	public void testParseReturnsTheObjectListForConditionalSymbolNotEqual() throws Exception {
		assertEquals(CM4thCommand.ISNOTEQUAL, this.unitUnderTest.parse("!=").getMainWord()[0]);
	}

	@Test
	public void testParseReturnsTheObjectListForLoops() throws Exception {
		Object[] result = this.unitUnderTest.parse(TEST_LOOP).getMainWord();
		assertNotNull(result);
		assertEquals(new Long(1), result[0]);
		assertEquals(CM4thCommand.PUSH, result[1]);
		assertEquals(CM4thCommand.NOP, result[2]);
		assertEquals(CM4thCommand.DUP, result[3]);
		assertEquals(CM4thCommand.ADD, result[4]);
		assertEquals(CM4thCommand.DUP, result[5]);
		assertEquals(new Long(10), result[6]);
		assertEquals(CM4thCommand.PUSH, result[7]);
		assertEquals(CM4thCommand.ISGREATER, result[8]);
		assertEquals(new JumpTarget(2), result[9]);
		assertEquals(CM4thCommand.JUMPONFALSE, result[10]);
	}

	@Test
	public void testParseReturnsTheObjectListForProgramWithNewWordDefinition() throws Exception {
//        "double" DEFINE
//            DUP ADD
//        ENDDEF
//    
//        1
//        LOOP
//            DOUBLE
//            DUP
//            PRINT
//            256 >=
//        UNTIL
//        "READY." PRINT
		Object[] result = this.unitUnderTest.parse(TEST_PRG_WITH_NEW_WORD).getMainWord();
		assertNotNull(result);
		assertEquals(1L, result[0]);
		assertEquals(CM4thCommand.PUSH, result[1]);
		assertEquals(CM4thCommand.NOP, result[2]);
		assertEquals("double", ((MethodCall) result[3]).getMethodName());
		assertEquals(CM4thCommand.JUMPSUB, result[4]);
		assertEquals(CM4thCommand.DUP, result[5]);
		assertEquals(CM4thCommand.PRINT, result[6]);
		assertEquals(256L, result[7]);
		assertEquals(CM4thCommand.PUSH, result[8]);
		assertEquals(CM4thCommand.ISGREATEROREQUAL, result[9]);
		assertEquals(new JumpTarget(2), result[10]);
		assertEquals(CM4thCommand.JUMPONFALSE, result[11]);
		assertEquals("READY.", result[12]);
		assertEquals(CM4thCommand.PUSH, result[13]);
		assertEquals(CM4thCommand.PRINT, result[14]);
		result = this.unitUnderTest.parse(TEST_PRG_WITH_NEW_WORD).getWordCode("double");
		assertNotNull(result);
		assertEquals(CM4thCommand.DUP, result[0]);
		assertEquals(CM4thCommand.ADD, result[1]);
		assertEquals(CM4thCommand.RETURN, result[2]);
	}

	@Test
	public void testParseReturnsTheObjectListForProgramWithTabs() throws Exception {
		Object[] result = this.unitUnderTest.parse(TEST_PRG_WITH_TABS).getMainWord();
		assertNotNull(result);
		assertEquals(new Long(1), result[0]);
		assertEquals(CM4thCommand.PUSH, result[1]);
		assertEquals(CM4thCommand.NOP, result[2]);
		assertEquals(new Long(1), result[3]);
		assertEquals(CM4thCommand.PUSH, result[4]);
		assertEquals(CM4thCommand.ADD, result[5]);
		assertEquals(CM4thCommand.DUP, result[6]);
		assertEquals(CM4thCommand.PRINT, result[7]);
		assertEquals(CM4thCommand.DUP, result[8]);
		assertEquals(new Long(10), result[9]);
		assertEquals(CM4thCommand.PUSH, result[10]);
		assertEquals(CM4thCommand.ISGREATER, result[11]);
		assertEquals(new JumpTarget(2), result[12]);
		assertEquals(CM4thCommand.JUMPONFALSE, result[13]);
		assertEquals("READY.", result[14]);
		assertEquals(CM4thCommand.PUSH, result[15]);
		assertEquals(CM4thCommand.PRINT, result[16]);
	}

	@Test(expected = CM4thParseException.class)
	public void testParseThrowsAnExceptionOnUnclosedLiteral() throws Exception {
		this.unitUnderTest.parse("\"Kaputnik PRINT");
	}

}