/*
 * ExceptionTestClassGenerator.java
 *
 * (c) by Ollie
 *
 * 11.08.2019
 */
package de.ollie.codeminion.generators;

/**
 * A generator for exception test classes.
 *
 * @author ollie
 *
 */
public class ExceptionTestClassGenerator {

	/**
	 * Creates a exception class source code for the passed parameters.
	 * 
	 * @param className   The name of the exception class.
	 * @param packageName The name of the package of the exception class.
	 * @param cause       Set this flag to create a cause parameter for the constructor.
	 * @param message     Set this flag to create a message parameter for the constructor.
	 * @return The source code for the exception class.
	 */
	public String generate(String className, String packageName, boolean cause, boolean message) {
		String code = "package " + packageName + ";\n\n";
		code += "import static org.hamcrest.MatcherAssert.assertThat;\n";
		code += "import static org.hamcrest.Matchers.equalTo;\n\n";
		code += "import org.junit.Test;\n\n";
		code += "public class " + className + "Test {\n\n";
		if (cause) {
			code += "\tprivate static final RuntimeException CAUSE = new RuntimeException();\n";
		}
		if (message) {
			code += "\tprivate static final String MESSAGE = \"message\";\n";
		}
		code += "\n\tprivate " + className + " unitUnderTest = new " + className + "(MESSAGE, CAUSE);\n\n";
		if (cause) {
			code += "\t@Test\n";
			code += "\tpublic void constructorSetCauseCorrectly() {\n";
			code += "\t\tassertThat(this.unitUnderTest.getCause(), equalTo(CAUSE));\n";
			code += "\t}\n\n";
		}
		if (message) {
			code += "\t@Test\n";
			code += "\tpublic void constructorSetMessageCorrectly() {\n";
			code += "\t\tassertThat(this.unitUnderTest.getMessage(), equalTo(MESSAGE));\n";
			code += "\t}\n\n";
		}
		code += "}";
		return code;
	}

}