/*
 * ExceptionClassGenerator.java
 *
 * (c) by Ollie
 *
 * 11.08.2019
 */
package de.ollie.codeminion.generators;

/**
 * A generator for exception classes.
 *
 * @author ollie
 *
 */
public class ExceptionClassGenerator {

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
		String code = "package " + packageName + ";\n\n\n" //
				+ "public class " + className + "Exception extends Exception {\n\n" //
				+ "\tpublic " + className + "Exception (";
		String parameterList = "";
		String parameters = "";
		if (message) {
			parameterList += "String message";
			parameters = "message";
		}
		if (cause) {
			if (!parameterList.isEmpty()) {
				parameterList += ", ";
			}
			if (!parameters.isEmpty()) {
				parameters += ", ";
			}
			parameterList += "Throwable cause";
			parameters += "cause";
		}
		code += parameterList + ") {\n";
		code += "\t\tsuper(" + parameters + ");\n";
		code += "\t}\n\n";
		code += "}";
		return code;
	}

}