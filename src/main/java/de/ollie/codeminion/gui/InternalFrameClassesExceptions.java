/*
 * InternalFrameClassesExceptions.java
 *
 * (c) by Ollie
 *
 * 06.08.2019
 */
package de.ollie.codeminion.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.ollie.codeminion.generators.ExceptionClassGenerator;
import de.ollie.codeminion.generators.ExceptionTestClassGenerator;

/**
 * An internal frame for the classes / exceptions generation tool.
 * 
 * @author ollie
 *
 */
public class InternalFrameClassesExceptions extends JInternalFrame implements ActionListener, ItemListener {

	private static final int HGAP = 3;
	private static final int VGAP = 3;

	private JCheckBox checkBoxCause = new JCheckBox();
	private JCheckBox checkBoxMessage = new JCheckBox();
	private JTextArea textAreaClassSourceCode = new JTextArea();
	private JTextArea textAreaTestClassSourceCode = new JTextArea();
	private JTextField textFieldClassName = new JTextField(30);
	private JTextField textFieldPackageName = new JTextField(30);

	private ExceptionClassGenerator classGenerator = new ExceptionClassGenerator();
	private ExceptionTestClassGenerator testClassGenerator = new ExceptionTestClassGenerator();

	public InternalFrameClassesExceptions() {
		super("Exceptions", true, true);
		this.setContentPane(createContentPanel());
		this.pack();
		this.setVisible(true);
		try {
			this.setMaximum(true);
		} catch (Exception e) {
			// NOP
		}
	}

	private JPanel createContentPanel() {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		p.add(createPropertiesPanel());
		p.add(createClassPanel());
		p.add(createTestClassPanel());
		return p;
	}

	private JPanel createPropertiesPanel() {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		p.add(createPropertiesLabelPanel(), BorderLayout.WEST);
		p.add(createPropertiesFieldPanel(), BorderLayout.CENTER);
		return p;
	}

	private JPanel createPropertiesLabelPanel() {
		JPanel p = new JPanel(new GridLayout(4, 1, HGAP, VGAP));
		p.add(new JLabel("Class Name"));
		p.add(new JLabel("Package Name"));
		p.add(new JLabel("Create Cause Parameter"));
		p.add(new JLabel("Create Message Parameter"));
		return p;
	}

	private JPanel createPropertiesFieldPanel() {
		JPanel p = new JPanel(new GridLayout(4, 1, HGAP, VGAP));
		p.add(this.textFieldClassName);
		p.add(this.textFieldPackageName);
		p.add(this.checkBoxCause);
		p.add(this.checkBoxMessage);
		this.textFieldClassName.addActionListener(this);
		this.textFieldPackageName.addActionListener(this);
		this.checkBoxCause.addItemListener(this);
		this.checkBoxMessage.addItemListener(this);
		return p;
	}

	private JPanel createClassPanel() {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		this.textAreaClassSourceCode.setFont(new Font("monospaced", Font.PLAIN, 12));
		p.add(new JScrollPane(this.textAreaClassSourceCode), BorderLayout.CENTER);
		return p;
	}

	private JPanel createTestClassPanel() {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		this.textAreaTestClassSourceCode.setFont(new Font("monospaced", Font.PLAIN, 12));
		p.add(new JScrollPane(this.textAreaTestClassSourceCode), BorderLayout.CENTER);
		return p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateTextAreas();
	}

	private void updateTextAreas() {
		System.out.println("##### " + this.textFieldClassName.getText() + ", " + this.textFieldPackageName.getText()
				+ ", " + this.checkBoxCause.isSelected() + ", " + this.checkBoxMessage.isSelected());
		this.textAreaClassSourceCode.setText(
				this.classGenerator.generate(this.textFieldClassName.getText(), this.textFieldPackageName.getText(),
						this.checkBoxCause.isSelected(), this.checkBoxMessage.isSelected()));
		this.textAreaTestClassSourceCode.setText(
				this.testClassGenerator.generate(this.textFieldClassName.getText(), this.textFieldPackageName.getText(),
						this.checkBoxCause.isSelected(), this.checkBoxMessage.isSelected()));
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		updateTextAreas();
	}

}