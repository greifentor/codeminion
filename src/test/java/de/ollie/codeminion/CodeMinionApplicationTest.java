package de.ollie.codeminion;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Integration tests for the CodeMinion application.
 * 
 * @author ollie
 *
 */
public class CodeMinionApplicationTest {

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

	@Test
	public void clickTheExceptionItem_CallsXXXXXXX() throws Exception {
		CodeMinionApplication.applicationCloser = new TestApplicationCloser();
		CodeMinionApplication.log = mock(Logger.class);
		new CodeMinionApplication();
		JFrameOperator frame = new JFrameOperator();
		JMenuOperator menuClasses = new JMenuOperator(frame, 1);
		menuClasses.doClick();
		JMenuItemOperator menuItemException = new JMenuItemOperator(frame, 0);
		menuItemException.doClick();
		verify(CodeMinionApplication.log, times(1)).info("Menu item selected: Classes | Exception");
	}

}

class TestApplicationCloser extends ApplicationCloser {

	int calls = 0;

	@Override
	public void close(ConfigurableApplicationContext context) {
		calls++;
	}

}