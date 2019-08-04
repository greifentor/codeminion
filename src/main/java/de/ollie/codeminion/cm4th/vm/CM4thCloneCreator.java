/*
 * CM4thCloneCreator.java
 *
 * 10.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

import static de.ollie.utils.Check.ensure;

/**
 * This class creates clones for selected classes.
 *
 * @author ollie
 */

public class CM4thCloneCreator {

	/**
	 * Creates a clone if this is intended for the class.
	 *
	 * @param o The object which is to clone.
	 * @return The clone of the class if this is intended for the objects class or the object otherwise.
	 * @throws IllegalArgumentException In case of passing a null pointer.
	 */
	public Object createClone(Object o) throws IllegalArgumentException {
		ensure(o != null, "object to clone cannot be null.");
		if (o instanceof Double) {
			return new Double(((Double) o).doubleValue());
		} else if (o instanceof Long) {
			return new Long(((Long) o).longValue());
		} else if (o instanceof String) {
			return new String(o.toString());
		}
		return o;
	}

}