/*
 * CM4thStackEvent.java
 *
 * 05.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.stack;

import static de.ollie.utils.Check.ensure;

import lombok.Data;

/**
 * A container for the data of a state changing CM4thStack event.
 *
 * @author ollie
 */
@Data
public class CM4thStackEvent {

	private CM4thStackEventType type = null;
	private Object value;

	/**
	 * Creates a new data container for CM4thStack events with the passed parameters.
	 *
	 * @param type  The type of the event.
	 * @param value The value which have been pushed or popped.
	 * @throws IllegalArgumentException Passing a null pointer as type.
	 */
	public CM4thStackEvent(CM4thStackEventType type, Object value) throws IllegalArgumentException {
		super();
		ensure(type != null, "type cannot be null.");
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Type=" + this.getType() + ",Value=" + String.valueOf(this.getValue());
	}

}