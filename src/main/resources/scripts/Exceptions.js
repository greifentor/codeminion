function getMenu() {
	return "Classes | Exceptions";
}


function getProperties() {
	return { "String", "class.name", 
			"String", "package.name",
			"Boolean", "set.attribute.message",
			"Boolean", "set.attribute.cause" };
	}
}


function getClassCount() {
	return 2;
}


function getClassSourceCode0(Properties props) {
	var parameters = "";
	var parameterlist = "";
	var s = "package ${package.name};\n"
			+ "\n"
			+ "public class ${class.name} {\n"
			+ "\n"
			+ "\tpublic ${class.name}(${parameterlist}) {\n"
			+ "\t\tsuper(${parameters})";
	if (props.get("set.attribute.message").equals("true")) {
		parameterlist += "String message";
	}
	if (props.get("set.attribute.cause").equals("true")) {
		parameterlist += "String message";
	}
	return s;
}