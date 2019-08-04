/*
 * CM4thParser.java
 *
 * 07.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.parser;

import static de.ollie.utils.Check.ensure;

import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import de.ollie.codeminion.cm4th.vm.CM4thCommand;
import de.ollie.codeminion.cm4th.vm.CM4thProgram;
import de.ollie.codeminion.cm4th.vm.JumpTarget;
import de.ollie.codeminion.cm4th.vm.MethodCall;

/**
 * A parser which converts strings into lists of CM4th commands.
 *
 * @author ollie
 */

public class CM4thParser {

	/**
	 * Parses the passed string to a list of CM4th commands.
	 *
	 * @param s The string which is to parse.
	 * @return A list of objects which contains data and parsed commands.
	 * @throws IllegalArgumentException Passing a null pointer.
	 */
	public CM4thProgram parse(String s) throws IllegalArgumentException {
		CM4thProgram program = new CM4thProgram();
		Stack<JumpTarget> ifJumps = new Stack<JumpTarget>();
		Stack<JumpTarget> loopJumps = new Stack<JumpTarget>();
		s = this.cleanUpLeadingTabsAndSpaces(s);
		String word = "";
		while (s.length() > 0) {
			Object token = this.getNext(s);
			s = this.removeNextToken(s);
			if ((token instanceof String) && program.isWordDefined(token.toString())) {
				program.addCommandToWord(word, new MethodCall(token.toString()));
				program.addCommandToWord(word, CM4thCommand.JUMPSUB);
			} else if (token.equals("+")) {
				program.addCommandToWord(word, CM4thCommand.ADD);
			} else if (token.equals("DEFINE")) {
				Object c = program.cutLastCommandOfWord(word);
				c = program.cutLastCommandOfWord(word);
				ensure(c instanceof String, new CM4thParseException("no name set for define"));
				word = c.toString();
			} else if (token.equals("ENDDEF")) {
				program.addCommandToWord(word, CM4thCommand.RETURN);
				word = "";
			} else if (token.equals("ENDIF")) {
				program.addCommandToWord(word, CM4thCommand.NOP);
				JumpTarget jt = (JumpTarget) ifJumps.pop();
				jt.setAddress(program.getCurrentInstructionCount(word) - 1);
			} else if (token.equals("ELSE")) {
				JumpTarget jt = (JumpTarget) ifJumps.pop();
				jt.setAddress(program.getCurrentInstructionCount(word) + 2);
				jt = new JumpTarget();
				program.addCommandToWord(word, jt);
				ifJumps.push(jt);
				program.addCommandToWord(word, CM4thCommand.JUMP);
			} else if (token.equals("IFTRUE")) {
				JumpTarget jt = new JumpTarget();
				program.addCommandToWord(word, jt);
				ifJumps.push(jt);
				program.addCommandToWord(word, CM4thCommand.JUMPONFALSE);
			} else if (token.equals("LOOP")) {
				program.addCommandToWord(word, CM4thCommand.NOP);
				loopJumps.push(new JumpTarget(program.getCurrentInstructionCount(word) - 1));
			} else if (token.equals("UNTIL")) {
				JumpTarget jt = (JumpTarget) loopJumps.pop();
				program.addCommandToWord(word, jt);
				program.addCommandToWord(word, CM4thCommand.JUMPONFALSE);
			} else {
				program.addCommandToWord(word, token);
				if (!(token instanceof CM4thCommand)) {
					program.addCommandToWord(word, CM4thCommand.PUSH);
				}
			}
		}
		/*
		 * if (newWords.size() != 0) { int counter = 2; List<Object> allNewWords = new Vector<Object>(); Map<String,
		 * Integer> wordAddresses = new Hashtable<String, Integer>(); for (String name : newWords.keySet()) {
		 * List<Object> newWord = newWords.get(name); wordAddresses.put(name, counter); for (Object command : newWord) {
		 * allNewWords.add(command); counter++; } } for (Object command : currentCode) { if (command instanceof
		 * MethodCall) { allNewWords.add(new JumpTarget(wordAddresses.get(((MethodCall) command ).getMethodName()))); }
		 * else { allNewWords.add(command); } } allNewWords.add(0, CM4thCommand.JUMP); allNewWords.add(0, new
		 * JumpTarget(counter)); currentCode = allNewWords; }
		 */
		return program;
	}

	private String cleanUpLeadingTabsAndSpaces(String s) {
		String r = "";
		s = s.replace("\r", "");
		StringTokenizer st = new StringTokenizer(s, "\n\t");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			r = r.concat(" ").concat(token);
		}
		return r;
	}

	private Object getNext(String s) {
		String token = null;
		int tokenEnd = this.findFirstTokenEndPosition(s);
		token = s.trim().substring(0, tokenEnd);
		if (token.startsWith("\"")) {
			return token.substring(1, token.length() - 1).trim();
		} else if (this.isCommand(token)) {
			return CM4thCommand.valueOf(token.toUpperCase());
		} else if (token.equals("==")) {
			return CM4thCommand.ISEQUAL;
		} else if (token.equals(">")) {
			return CM4thCommand.ISGREATER;
		} else if (token.equals(">=")) {
			return CM4thCommand.ISGREATEROREQUAL;
		} else if (token.equals("<")) {
			return CM4thCommand.ISLESSER;
		} else if (token.equals("<=")) {
			return CM4thCommand.ISLESSEROREQUAL;
		} else if (token.equals("!=")) {
			return CM4thCommand.ISNOTEQUAL;
		} else if (this.isLong(token)) {
			return Long.valueOf(token);
		} else if (this.isDouble(token)) {
			return Double.valueOf(token);
		} else if (this.isBoolean(token)) {
			return token.toUpperCase().equals("TRUE");
		}
		return token;
	}

	private int findFirstTokenEndPosition(String s) {
		int pos = 0;
		s = s.trim();
		if (s.startsWith("\"")) {
			s = s.substring(1);
			pos = s.indexOf("\"");
			ensure(pos >= 0, new CM4thParseException("literal unclosed: " + s));
			pos += 2;
		} else {
			s = s.trim();
			pos = s.indexOf(" ");
			pos = (pos < 0 ? s.length() : pos);
		}
		return pos;
	}

	private boolean isCommand(String token) {
		try {
			CM4thCommand.valueOf(token.toUpperCase());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	private boolean isLong(String token) {
		try {
			Long.valueOf(token);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	private boolean isDouble(String token) {
		try {
			Double.valueOf(token);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	private boolean isBoolean(String token) {
		token = token.toUpperCase();
		return token.equals("FALSE") || token.equals("TRUE");
	}

	private String removeNextToken(String s) {
		int tokenEnd = this.findFirstTokenEndPosition(s.trim());
		if (tokenEnd < s.length()) {
			s = s.substring(tokenEnd + 1, s.length());
		} else if (tokenEnd == s.length()) {
			s = "";
		}
		return s.trim();
	}

	private Object getLastCommand(List<Object> code) {
		if (code.size() < 1) {
			return null;
		}
		return code.get(code.size() - 1);
	}

}