/*
 * CM4thProgram.java
 *
 * 11.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

import static de.ollie.utils.Check.ensure;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * This structure stores a CodeMinion forth program.
 *
 * @author ollie
 *
 * @changed OLI 11.01.2013 - Added.
 */

public class CM4thProgram {

	private Map<String, List<Object>> words = new Hashtable<String, List<Object>>();

	/**
	 * Creates a new Fantastic forth program.
	 */
	public CM4thProgram() {
		super();
		this.words.put("", new Vector<Object>());
	}

	/**
	 * Adds a command to the words code with passed name.
	 *
	 * @param word    The name of the word whose code is to extend by the command (pass "" for the main word of the
	 *                program.
	 * @param command The command which is to add to the words code.
	 * @throws IllegalArgumentException Passing a null string.
	 */
	public void addCommandToWord(String word, Object command) {
		this.addCommandsToWord(word, command);
	}

	/**
	 * Adds a command to the words code with passed name.
	 *
	 * @param word     The name of the word whose code is to extend by the command (pass "" for the main word of the
	 *                 program.
	 * @param commands A list of commands which are to add to the words code.
	 * @throws IllegalArgumentException Passing a null string.
	 */
	public void addCommandsToWord(String word, Object... commands) {
		ensure(word != null, "word name cannot be null.");
		List<Object> code = this.words.get(word);
		if (code == null) {
			code = new Vector<Object>();
			this.words.put(word, code);
		}
		for (Object command : commands) {
			code.add(command);
		}
	}

	/**
	 * Cuts the last instructions of the word with passed name and returns it.
	 *
	 * @param word The name of the word whose code should be returned (pass "" for main word).
	 * @return The instructions of the word with passed name.
	 */
	public Object cutLastCommandOfWord(String word) {
		List<Object> code = this.words.get(word);
		if (code != null) {
			Object c = code.get(code.size() - 1);
			code.remove(code.size() - 1);
			return c;
		}
		return null;
	}

	/**
	 * Returns the current instruction count of the word with passed name.
	 * 
	 * @param word The name of the word whose code should be returned (pass "" for main word).
	 * @return The instructions of the word with passed name.
	 */
	public int getCurrentInstructionCount(String word) {
		List<Object> code = this.words.get(word);
		if (code != null) {
			return code.size();
		}
		return -1;
	}

	/**
	 * Returns the instructions of the main word of the program.
	 *
	 * @return The instructions of the main word of the program.
	 */
	public Object[] getMainWord() {
		return this.getWordCode("");
	}

	/**
	 * Returns the instructions of the word with passed name.
	 *
	 * @param word The name of the word whose code should be returned (pass "" for main word).
	 * @return The instructions of the word with passed name.
	 */
	public Object[] getWordCode(String word) {
		return this.words.get(word).toArray(new Object[0]);
	}

	/**
	 * Checks if the passed word is defined.
	 *
	 * @param word The word name to check.
	 * @return <CODE>true</CODE> if the word is defined, <CODE>false</CODE> otherwise.
	 */
	public boolean isWordDefined(String word) {
		return this.words.get(word) != null;
	}

}