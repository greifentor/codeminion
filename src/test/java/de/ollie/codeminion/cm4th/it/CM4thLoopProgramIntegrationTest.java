/*
 * CM4thLoopProgramIntegrationTest.java
 *
 * 04.08.2019
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.it;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.ollie.codeminion.cm4th.parser.CM4thParser;
import de.ollie.codeminion.cm4th.vm.CM4thProgram;
import de.ollie.codeminion.cm4th.vm.CM4thVirtualMachine;

/**
 * An integration test an CM4th program with a loop.
 *
 * @author ollie
 *
 */
public class CM4thLoopProgramIntegrationTest {

	@Test
	public void shouldLeafAnIntegerOfValue128OnStack() {
		// Prepare
		String cm4thProgram = "" //
				+ "1\n" //
				+ "LOOP\n" //
				+ "    DUP\n" //
				+ "    ADD\n" //
				+ "    DUP 128 ==\n" //
				+ "UNTIL\n" //
				+ "\"Ready.\" PRINT";
		CM4thProgram program = new CM4thParser().parse(cm4thProgram);
		CM4thVirtualMachine vm = new CM4thVirtualMachine();
		// Run
		vm.startProgram(program);
		// Check
		assertEquals(128L, ((Long) vm.getTop()).intValue());
	}

}
