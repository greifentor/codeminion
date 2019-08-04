/*
 * CM4thVirtualMachine.java
 *
 * 07.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

import java.util.Stack;

import de.ollie.codeminion.cm4th.stack.CM4thStack;

/**
 * The virtual machine which interprets the CM4th commands.
 *
 * @author ollie
 */

public class CM4thVirtualMachine {

	private CM4thCloneCreator cloneCreator = new CM4thCloneCreator();
	private Stack<Object> executionStack = new Stack<Object>();
	private CM4thStack dataStack = new CM4thStack();
	private CM4thVirtualMachineEventManager vmEventManager = new CM4thVirtualMachineEventManager();

	/**
	 * Adds the passed listener to the list of the listeners waiting to be notified of events of the VM.
	 *
	 * @param listener The listener which is to add to the list of the managed listeners.
	 * @throws IllegalArgumentException In case of passing a null pointer.
	 */
	public void addCM4thVirtualMachineListener(CM4thVirtualMachineListener listener) throws IllegalArgumentException {
		this.vmEventManager.addCM4thVirtualMachineListener(listener);
	}

	/**
	 * Removes the passed listener from the list of the listeners waiting to be notified of events of the VM.
	 *
	 * @param listener The listener which is to remove from the list of the managed listeners.
	 * @throws IllegalArgumentException In case of passing a null pointer.
	 */
	public void removeCM4thVirtualMachineListener(CM4thVirtualMachineListener listener) throws IllegalArgumentException {
		this.vmEventManager.removeCM4thVirtualMachineListener(listener);
	}

	/**
	 * Interprets a list of commands.
	 *
	 * @param fthProgram The list of commands to interpret.
	 */
	public void startProgram(CM4thProgram fthProgram) {
		this.startWord("", fthProgram);
	}

	/*
	 * "x2" DEFINE DUP ADD ENDDEF
	 * 
	 * "4x" DEFINE x2 x2 ENDDEF
	 * 
	 * "Starting" PRINT 1 LOOP x4 DUP PRINT DUP 256 > UNTIL "READY." PRINT
	 */
	/**
	 * Interprets a single defined word.
	 *
	 * @param word       The defined word to run ("" for the main program).
	 * @param fthProgram The list of commands to interpret.
	 */
	public void startWord(String word, CM4thProgram fthProgram) {
		int programCounter = 0;
		Object[] program = fthProgram.getWordCode(word);
		while (programCounter < program.length) {
			Object command = program[programCounter];
			System.out.println("> " + (word == "" ? "MAIN" : word) + "." + command);
			this.printProgram(program, programCounter);
			if ((command == CM4thCommand.JUMP) || (command == CM4thCommand.JUMPONFALSE)) {
				int pc = ((JumpTarget) this.executionStack.pop()).getAddress();
				if ((command == CM4thCommand.JUMP) || !((Boolean) this.dataStack.pop()).booleanValue()) {
					programCounter = pc;
				} else {
					programCounter++;
				}
			} else if ((command == CM4thCommand.JUMPSUB)) {
				String jumpWord = ((MethodCall) this.executionStack.pop()).getMethodName();
				this.startWord(jumpWord, fthProgram);
				programCounter++;
			} else if ((command == CM4thCommand.RETURN)) {
				programCounter++;
			} else {
				this.interpret(command);
				programCounter++;
			}
			System.out.println("E: " + this.executionStack);
			System.out.println("D: " + this.dataStack);
		}
	}

	private void printProgram(Object[] comands, int pc) {
		for (int i = 0; i < comands.length; i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			if (i == pc) {
				System.out.print("[");
			}
			System.out.print(comands[i]);
			if (i == pc) {
				System.out.print("]");
			}
		}
		System.out.println();
	}

	/**
	 * Interprets the passed string as a command or a data to put on the stack.
	 *
	 * @param o An object which could contain a command or data.
	 */
	void interpret(Object o) {
		if (o instanceof CM4thCommand) {
			this.executeCommand((CM4thCommand) o);
		} else {
			this.executionStack.push(o);
		}
	}

	private void executeCommand(CM4thCommand command) {
		if ((command == CM4thCommand.ADD) || (command == CM4thCommand.DIVIDE) || (command == CM4thCommand.MULTIPLY)
				|| (command == CM4thCommand.SUBTRACT)) {
			Object o0 = this.dataStack.pop();
			Object o1 = this.dataStack.pop();
			if (this.areBothNumbersAndOfTheSameType(o0, o1)) {
				Number n0 = (Number) o0;
				Number n1 = (Number) o1;
				if (this.isOneDouble(n0, n1)) {
					if (command == CM4thCommand.ADD) {
						this.dataStack.push(n1.doubleValue() + n0.doubleValue());
					} else if (command == CM4thCommand.DIVIDE) {
						this.dataStack.push(n1.doubleValue() / n0.doubleValue());
					} else if (command == CM4thCommand.MULTIPLY) {
						this.dataStack.push(n1.doubleValue() * n0.doubleValue());
					} else if (command == CM4thCommand.SUBTRACT) {
						this.dataStack.push(n1.doubleValue() - n0.doubleValue());
					}
				} else {
					if (command == CM4thCommand.ADD) {
						this.dataStack.push(n1.longValue() + n0.longValue());
					} else if (command == CM4thCommand.DIVIDE) {
						this.dataStack.push(n1.longValue() / n0.longValue());
					} else if (command == CM4thCommand.MULTIPLY) {
						this.dataStack.push(n1.longValue() * n0.longValue());
					} else if (command == CM4thCommand.SUBTRACT) {
						this.dataStack.push(n1.longValue() - n0.longValue());
					}
				}
			} else {
				if (command == CM4thCommand.ADD) {
					this.dataStack.push(o1.toString() + o0.toString());
				} else {
					throw new IllegalArgumentException("illegal command for strings: " + command);
				}
			}
		} else if (command == CM4thCommand.DUP) {
			this.dataStack.push(this.cloneCreator.createClone(this.dataStack.top()));
		} else if (command == CM4thCommand.ISEQUAL) {
			Comparable o0 = (Comparable) this.dataStack.pop();
			Comparable o1 = (Comparable) this.dataStack.pop();
			this.dataStack.push(o1.compareTo(o0) == 0);
		} else if (command == CM4thCommand.ISGREATER) {
			Comparable o0 = (Comparable) this.dataStack.pop();
			Comparable o1 = (Comparable) this.dataStack.pop();
			this.dataStack.push(o1.compareTo(o0) > 0);
		} else if (command == CM4thCommand.ISGREATEROREQUAL) {
			Comparable o0 = (Comparable) this.dataStack.pop();
			Comparable o1 = (Comparable) this.dataStack.pop();
			this.dataStack.push(o1.compareTo(o0) >= 0);
		} else if (command == CM4thCommand.ISLESSER) {
			Comparable o0 = (Comparable) this.dataStack.pop();
			Comparable o1 = (Comparable) this.dataStack.pop();
			this.dataStack.push(o1.compareTo(o0) < 0);
		} else if (command == CM4thCommand.ISLESSEROREQUAL) {
			Comparable o0 = (Comparable) this.dataStack.pop();
			Comparable o1 = (Comparable) this.dataStack.pop();
			this.dataStack.push(o1.compareTo(o0) <= 0);
		} else if (command == CM4thCommand.ISNOTEQUAL) {
			Comparable o0 = (Comparable) this.dataStack.pop();
			Comparable o1 = (Comparable) this.dataStack.pop();
			this.dataStack.push(o1.compareTo(o0) != 0);
		} else if (command == CM4thCommand.PUSH) {
			Object o = this.executionStack.pop();
			this.dataStack.push(o);
		} else if (command == CM4thCommand.PRINT) {
			Object o = this.dataStack.pop();
			// this.vmEventManager.fireStandardIOOutput(o != null ? o.toString() : "null");
			this.vmEventManager.fireStandardIOOutput(o.toString());
		}
	}

	private boolean areBothNumbersAndOfTheSameType(Object o0, Object o1) {
		return ((o0 instanceof Long) || (o0 instanceof Double)) && ((o1 instanceof Long) || (o1 instanceof Double));
	}

	private boolean isOneDouble(Number n0, Number n1) {
		return (n0 instanceof Double) || (n1 instanceof Double);
	}

	/**
	 * Returns the topmost object on the stack.
	 *
	 * @return The topmost object on the stack.
	 */
	public Object getTop() {
		return this.dataStack.top();
	}

}