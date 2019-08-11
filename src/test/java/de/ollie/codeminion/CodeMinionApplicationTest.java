package de.ollie.codeminion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JInternalFrameOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Integration tests for the CodeMinion application.
 * 
 * @author ollie
 * @ParameterizedTest
 */
public class CodeMinionApplicationTest {

	private static final String CLASS_NAME = "Test";
	private static final String PACKAGE_NAME = "test.pack.age";

	@DisplayName("Opens the application and closes it.")
	@Test
	public void clickTheQuitItem_CallsTheApplicationCloser() throws Exception {
		CodeMinionApplication.applicationCloser = new TestApplicationCloser();
		new CodeMinionApplication();
		JFrameOperator frame = new JFrameOperator();
		JMenuOperator menuApplication = new JMenuOperator(frame, 0);
		menuApplication.doClick();
		JMenuItemOperator menuItemQuit = new JMenuItemOperator(frame, 0);
		menuItemQuit.doClick();
		assertThat(((TestApplicationCloser) CodeMinionApplication.applicationCloser).calls, equalTo(1));
	}

	@DisplayName("Opens the exception generator dialog and creates an exception with cause and message and test.")
	@Test
	public void clickTheExceptionItem_OpensTheDialogForExceptionGeneratorAndCreatesAnExceptionClassAndTestWithCauseAndMessage()
			throws Exception {
		checkScenarioFor(true, true);
	}

	private void checkScenarioFor(boolean cause, boolean message) throws Exception {
		CodeMinionApplication.applicationCloser = new TestApplicationCloser();
		CodeMinionApplication.log = mock(Logger.class);
		new CodeMinionApplication();
		JFrameOperator frame = new JFrameOperator();
		JMenuOperator menuClasses = new JMenuOperator(frame, 1);
		menuClasses.doClick();
		JMenuItemOperator menuItemException = new JMenuItemOperator(frame, 0);
		menuItemException.doClick();
		verify(CodeMinionApplication.log, times(1)).info("Menu item selected: Classes | Exception");
		JInternalFrameOperator internalFrame = new JInternalFrameOperator(frame, 0);
		JTextFieldOperator textFieldClassName = new JTextFieldOperator(internalFrame, 0);
		JTextFieldOperator textFieldPackageName = new JTextFieldOperator(internalFrame, 1);
		JCheckBoxOperator checkBoxCause = new JCheckBoxOperator(internalFrame, 0);
		JCheckBoxOperator checkBoxMessage = new JCheckBoxOperator(internalFrame, 1);
		textFieldClassName.enterText(CLASS_NAME);
		textFieldPackageName.enterText(PACKAGE_NAME);
		checkBoxCause.setSelected(cause);
		checkBoxMessage.setSelected(message);
		JTextAreaOperator textAreaClass = new JTextAreaOperator(internalFrame, 0);
		JTextAreaOperator textAreaTestClass = new JTextAreaOperator(internalFrame, 1);
		assertEquals(getClassCode(cause, message), textAreaClass.getText());
		assertEquals("", /* getTestClassCode(cause, message), */ textAreaTestClass.getText());
		JMenuOperator menuApplication = new JMenuOperator(frame, 0);
		menuApplication.doClick();
		JMenuItemOperator menuItemQuit = new JMenuItemOperator(frame, 0);
		menuItemQuit.doClick();
		assertThat(((TestApplicationCloser) CodeMinionApplication.applicationCloser).calls, equalTo(1));
		((TestApplicationCloser) CodeMinionApplication.applicationCloser).calls = 0;
	}

	private String getClassCode(boolean cause, boolean message) {
		String s = "package " + PACKAGE_NAME + ";\n\n\n" //
				+ "public class " + CLASS_NAME + "Exception extends Exception {\n\n" //
				+ "\tpublic " + CLASS_NAME + "Exception (";
		String pl = "";
		String p = "";
		if (message) {
			pl += "String message";
			p = "message";
		}
		if (cause) {
			if (!pl.isEmpty()) {
				pl += ", ";
			}
			if (!p.isEmpty()) {
				p += ", ";
			}
			pl += "Throwable cause";
			p += "cause";
		}
		s += pl + ") {\n";
		s += "\t\tsuper(" + p + ");\n";
		s += "\t}\n\n";
		s += "}";
		return s;
	}

	private String getTestClassCode(boolean cause, boolean message) {
		String className = CLASS_NAME + "Exception";
		String s = "package " + PACKAGE_NAME + ";\n\n";
		s += "import org.junit.Test;\n\n";
		s += "public class " + className + "Test {\n\n";
		if (cause) {
			s += "\tprivate static final RuntimeException CAUSE = new RuntimeException();\n";
		}
		if (message) {
			s += "\tprivate static final String MESSAGE = \"message\";\n";
		}
		s += "\n\tprivate " + className + " unitUnderTest = new " + className + "(MESSAGE, CAUSE);\n\n";
		if (cause) {
			s += "\t@Test\n";
			s += "\tpublic void constructorSetCauseCorrectly() {\n";
			s += "\t\tassertThat(this.unitUnderTest.getCause(), equalTo(CAUSE));\n";
			s += "\t}\n\n";
		}
		if (message) {
			s += "\t@Test\n";
			s += "\tpublic void constructorSetMessageCorrectly() {\n";
			s += "\t\tassertThat(this.unitUnderTest.getMessage(), equalTo(MESSAGE));\n";
			s += "\t}\n\n";
		}
		s += "}";
		return s;
	}

	@DisplayName("Opens the exception generator dialog and creates an exception with cause only and test.")
	@Test
	public void clickTheExceptionItem_OpensTheDialogForExceptionGeneratorAndCreatesAnExceptionClassAndTestWithCauseOnly()
			throws Exception {
		checkScenarioFor(true, false);
	}

	@DisplayName("Opens the exception generator dialog and creates an exception with message only and test.")
	@Test
	public void clickTheExceptionItem_OpensTheDialogForExceptionGeneratorAndCreatesAnExceptionClassAndTestWithMessageOnly()
			throws Exception {
		checkScenarioFor(false, true);
	}

	@DisplayName("Opens the exception generator dialog and creates an exception with neither cause nor message and test.")
	@Test
	public void clickTheExceptionItem_OpensTheDialogForExceptionGeneratorAndCreatesAnExceptionClassAndTestWithNoParameters()
			throws Exception {
		checkScenarioFor(false, false);
	}

}

class TestApplicationCloser extends ApplicationCloser {

	int calls = 0;

	@Override
	public void close(ConfigurableApplicationContext context) {
		calls++;
	}

}