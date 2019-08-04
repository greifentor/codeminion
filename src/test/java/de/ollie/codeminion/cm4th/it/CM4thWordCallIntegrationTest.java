/*
 * CM4thWordCallIntegrationTest.java
 *
 * 04.08.2019
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.it;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import de.ollie.codeminion.cm4th.parser.CM4thParser;
import de.ollie.codeminion.cm4th.vm.CM4thProgram;
import de.ollie.codeminion.cm4th.vm.CM4thVirtualMachine;

/**
 * An integration test for single word calls.
 *
 * @author ollie
 *
 */
public class CM4thWordCallIntegrationTest {

	@Test
	public void shouldLeafTheValueStringOnTheStack() {
		// Prepare
		String word = "word";
		String value = "value";
		String cm4thProgram = "\"" + word + "\" DEFINE \"" + value + "\" ENDDEF\n";
		CM4thProgram program = new CM4thParser().parse(cm4thProgram);
		CM4thVirtualMachine vm = new CM4thVirtualMachine();
		// Run
		vm.startWord(word, program);
		// Check
		assertEquals(value, (String) vm.getTop());
	}

	@Test
	public void shouldLeafTheValueStringWithLineEndOnTheStack() {
		// Prepare
		String word = "word";
		String value = "value<|bla";
		String cm4thProgram = "\"" + word + "\" DEFINE \"" + value + "\" ENDDEF\n";
		CM4thProgram program = new CM4thParser().parse(cm4thProgram);
		CM4thVirtualMachine vm = new CM4thVirtualMachine();
		// Run
		vm.startWord(word, program);
		// Check
		assertEquals(value, (String) vm.getTop());
	}

	@Ignore
	@Test
	public void shouldLeafTheValueStringWithLineEndAndCodedLineEndsOnTheStack() {
		// Prepare
		String word = "word";
		String value = "public class AClass {<|\n" //
				+ "<|\n" //
				+ "\tpublic void AMethod() {<|\n" //
				+ "\t}<|\n" //
				+ "<|\n" //
				+ "}\n";
		String cm4thProgram = "\"" + word + "\" DEFINE \"" + value + "\" ENDDEF\n";
		CM4thProgram program = new CM4thParser().parse(cm4thProgram);
		CM4thVirtualMachine vm = new CM4thVirtualMachine();
		// Run
		vm.startWord(word, program);
		// Check
		assertEquals(value.replace("\n", "").replace("<|", "\n"), ((String) vm.getTop()).replace("<|", "\n"));
	}

}