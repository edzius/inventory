/*
 * Copyright (c) 2011 Obix Labs Limited
 * Redistribution and use in source and binary forms, 
 * with or without modification, are permitted provided 
 * that the following conditions are met:
 * 
 * 		Redistribution of source code must retain the above 
 * 		copyright notice, this list of conditions and the 
 * 		following disclaimer.
 *
 * 		Redistribution in binary form must reproduce the 
 * 		above copyright notice, this list of conditions 
 * 		and the following disclaimer in the documentation 
 * 		and/or other materials provided with the distribution.
 * 
 * 	THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT A WARRANTY OF 
 * 	ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS 
 * 	AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, 
 * 	FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, 
 * 	ARE HEREBY EXCLUDED. OBIX LABS LIMITED ("Obix Labs") AND ITS 
 * 	LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE 
 * 	AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR 
 * 	ITS DERIVATIVES. IN NO EVENT WILL Obix Labs OR ITS LICENSORS BE 
 * 	LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, 
 * 	INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE 
 * 	DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF 
 * 	LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS 
 * 	SOFTWARE, EVEN IF Obix Labs HAS BEEN ADVISED OF THE POSSIBILITY OF 
 * 	SUCH DAMAGES.
 */

package com.obixlabs.commons.io;

import java.io.Serializable;

/**
 * <p>
 * A utility interface for handling console character input. This class, in conjunction 
 * with {@link InteractiveCommandLineReader}, provides a structured means of reading 
 * and validating user character input.
 * </p>
 * 
 * <p>
 * For example, to read a valid email address from the command-line, you 
 * could employ the following snippet of code.
 * 
 * <pre>
 * String prompt = "Please enter a file-URI";
 * String repeatPrompt = "Invalid input. Please re-enter file-URI";
 * FilePathConsoleInputHandler inputHandler = 
 * 	new FilePathConsoleInputHandler(prompt, repeatPrompt,true);
 * 
 *  InteractiveCommandLineReader.prompt(inputHandler);
 * </pre>
 * </p>
 * 
 * @see EmailAddressConsoleInputHandler
 * @see FilePathConsoleInputHandler
 */
public interface ConsoleInputHandler<T> extends Serializable
{
	/**
	 * <p>
	 * Call-back method which is used to pass the user's input to the 
	 * handler. Implementations of this method should also validate the 
	 * specified input and return a boolean value indicating its validity. 
	 * </p>
	 * 
	 * <p>
	 * The {@link InteractiveCommandLineReader#prompt(ConsoleInputHandler)}
	 * method will not return until this method returns a value of true i.e. until this method
	 * indicates that a satisfactory input-value has been found.
	 * </p>
	 * 
	 * @return True if the input value is valid and acceptable, and False otherwise. A value of 
	 * False will result in the user being prompted with the value returned by method 
	 * {@link #getRepeatPrompt()}. 
	 */
	boolean setInputValue(String value);
	
	/**
	 * <p>
	 * Returns the input prompt initially displayed to the user. An example of a prompt is 
	 * &nbsp;"please enter a number between 1 and 10>.&nbsp;". 
	 * </p>
	 * 
	 * <p>
	 * Please note that this prompt is used only before the user specifies the first input value. If this or subsequent
	 * values are rejected by the {@link #setInputValue(String)} method, then the prompt
	 * given by method {@link #getRepeatPrompt()} is used from that point onwards.
	 *   </p>
	 *   
	 * @return The input prompt initially displayed to the user.
	 */
	String getPrompt();
	
	/**
	 * <p>
	 * Returns the prompt displayed to the user for repeat input, where at least one 
	 * input has failed validation as implemented  by the {@link #setInputValue(String)} method.
	 * </p>
	 * 
	 * @return The prompt displayed to the user, post the failure of at least one input value.
	 */
	String getRepeatPrompt();
	
	/**
	 * <p>
	 * Returns the last value {@link #setInputValue(String) accepted} by the 
	 * instance on which this method is invoked.
	 * </p>
	 * @return The last value accepted by an instance. This
	 * is the last value for which the method {@link #setInputValue(String)}
	 * returned true.
	 */		
	public T getInputValue();        
	
}
