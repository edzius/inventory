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

package com.obixlabs.commons;

/**
 * <p>
 * Root class of all unchecked obix-labs exceptions.
 * </p>
 * @see ObixException
 */
public class ObixRuntimeException extends RuntimeException 
{
	private static final long serialVersionUID = 4958445697761705625L;

	/**
	 * <p>
	 * Default constructor.
	 * </p>
	 */
	public ObixRuntimeException() {}

	/**
	 * <p>
	 * Constructs an instance with the specified description.
	 * </p>
	 * 
	 * @param message A description of the error condition.
	 */
	public ObixRuntimeException(String message){super(message);}

	/**
	 * <p>
	 * Wraps the specified exception with an instance of this class.
	 * Generally used to encapsulate an error triggered or detected by an 
	 * underlying API. 
	 * </p>
	 * 
	 * @param cause The exception to be wrapped.
	 * @see ExceptionUtil#getStackTrace(Throwable)
	 */
	public ObixRuntimeException(Throwable cause){super(cause);}

	/**
	 * <p>
	 * Wraps an underlying exception as an instance of this type, but also masks the 
	 * message with that specified in the constructor arguments.
	 * </p> 
	 * <p>
	 * Although constructor {@link #ObixRuntimeException(Throwable)} can be used to wrap 
	 * an underlying exception, it does not mask the underlying's message. So a call 
	 * to the method {@link #getMessage()} will still return the message of the underlying
	 * exception.
	 * </p>
	 *  <p>
	 * This constructor allows for a substitute message (or mask) to be defined, 
	 * which is the returned by {@link #getMessage()} instead of that of the wrapped 
	 * exception.
	 * </p>
	 * @param message The message mask, also referred to as the description of the exception.
	 * @param cause The exception to be wrapped by this instance.
	 */
	public ObixRuntimeException(String message, Throwable cause) 
	{super(message, cause);}

	
	/**
	 * <p>
	 * Convenience method for transforming the instance's stack trace 
	 * to a {@link String}.
	 * </p>
	 * @return A {@link String} representation of the instance's stack trace.
	 */
	public String getStackTraceAsString() 
	{ return ExceptionUtil.getStackTrace(this); }
	
	/**
	 * <p>
	 * Wraps the specified exception with an {@link ObixRuntimeException}
	 * if it is not already one.  
	 * </p>
	 * 
	 * @param exce The Exception to wrap.
	 * 
	 * @return	If the specified Exception is an {@link ObixRuntimeException},
	 * this method will simply return a reference to it. Else,
	 * it will return a new {@link ObixRuntimeException}
	 * {@link ObixRuntimeException#ObixRuntimeException(Throwable) initialised} with
	 * the specified parameter. 
	 */
	public static ObixRuntimeException wrapException(Exception exce)
	{
		if (exce instanceof ObixRuntimeException)
			return (ObixRuntimeException)exce;
		else return new ObixRuntimeException(exce);
	}
	
}
