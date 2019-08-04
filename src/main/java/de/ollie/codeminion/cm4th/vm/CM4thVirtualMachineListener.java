/*
 * CM4thVirtualMachineListener.java
 *
 * 09.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

/**
 * Implementations of the interface will be notified about selected events of the CM4th VM.
 *
 * @author ollie
 */

public interface CM4thVirtualMachineListener {

	/**
	 * This method will be called if the VM writes an output to the standard I/O.
	 *
	 * @param s The string which is printed to the standard I/O.
	 */
	void printed(String s);

}