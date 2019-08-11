package de.ollie.codeminion;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import de.ollie.codeminion.gui.InternalFrameClassesExceptions;

@SpringBootApplication
public class CodeMinionApplication extends JFrame implements ActionListener {

	static Logger log = Logger.getLogger(CodeMinionApplication.class);
	static ApplicationCloser applicationCloser = new ApplicationCloser();

	private static ConfigurableApplicationContext ctx = null;

	public static void main(String[] args) {
		ctx = new SpringApplicationBuilder(CodeMinionApplication.class).headless(false).run(args);
	}

	private JDesktopPane desktopPane = new JDesktopPane();
	private JMenuItem menuItemApplicationQuit = null;
	private JMenuItem menuItemClassesException = null;

	public CodeMinionApplication() {
		super("Code Minion");
		this.setJMenuBar(createMenuBar());
		this.setContentPane(this.desktopPane);
		this.setMinimumSize(new Dimension(600, 480));
		this.pack();
		this.setVisible(true);
		centerFrame();
	}

	private JMenuBar createMenuBar() {
		JMenuBar mb = new JMenuBar();
		mb.add(createApplicationMenu());
		mb.add(createClassesMenu());
		return mb;
	}

	private JMenu createApplicationMenu() {
		JMenu m = new JMenu("Application");
		this.menuItemApplicationQuit = new JMenuItem("Quit");
		this.menuItemApplicationQuit.addActionListener(this);
		m.add(this.menuItemApplicationQuit);
		return m;
	}

	private JMenu createClassesMenu() {
		JMenu m = new JMenu("Classes");
		this.menuItemClassesException = new JMenuItem("Exception");
		this.menuItemClassesException.addActionListener(this);
		m.add(this.menuItemClassesException);
		return m;
	}

	private void centerFrame() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.menuItemApplicationQuit) {
			log.info("Menu item selected: Application | Quit");
			closeApplication();
		} else if (e.getSource() == this.menuItemClassesException) {
			log.info("Menu item selected: Classes | Exception");
			InternalFrameClassesExceptions ifce = new InternalFrameClassesExceptions();
			this.desktopPane.add(ifce);
		}
	}

	private void closeApplication() {
		applicationCloser.close(ctx);
	}

}