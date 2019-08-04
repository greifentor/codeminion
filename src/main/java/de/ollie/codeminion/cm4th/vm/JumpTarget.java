/*
 * JumpTarget.java
 *
 * 08.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

/**
 * This class represents a jump target.
 *
 * @author ollie
 */

public class JumpTarget {

	private int address = -1;

	/**
	 * Creates a new uninitialized jump target.
	 */
	public JumpTarget() {
		super();
	}

	/**
	 * Creates a new uninitialized jump target.
	 *
	 * @param address The target address of the jump.
	 */
	public JumpTarget(int address) {
		super();
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof JumpTarget)) {
			return false;
		}
		JumpTarget jt = (JumpTarget) o;
		return this.getAddress() == jt.getAddress();
	}

	/**
	 * Returns the address of the target.
	 *
	 * @return The address of the target.
	 */
	public int getAddress() {
		return this.address;
	}

	@Override
	public int hashCode() {
		return this.getAddress();
	}

	/**
	 * Returns the address of the target.
	 *
	 * @param addess The new address of the target.
	 */
	public void setAddress(int address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Address=" + this.getAddress();
	}

}