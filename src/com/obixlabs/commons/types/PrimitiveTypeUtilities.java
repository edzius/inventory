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
 
package com.obixlabs.commons.types;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.obixlabs.commons.ObixRuntimeException;

/**
 * <p>
 * A utility class for performing operations related to primitive Java types.
 * </p>
 */
public class PrimitiveTypeUtilities
{
	/**
	 * <p>
	 * This method loads a given {@link Class class} and creates an instance of it by
	 * invoking the class' default constructor. It is provided to shield
	 * calling applications from the need to explicitly handle
	 * the exceptions raised in the reflections API. However, note that 
	 * this method will fail with a {@link ObixRuntimeException runtime error} if
	 * an exception is thrown by the reflections API during the operation.
	 * </p>
	 * 
	 * @param <T>	The type defined by the {@link Class class}, and, consequently,
	 * the return type of this method.
	 * 
	 * @param className	The name of the {@link Class class} for which an instance 
	 * is to be created
	 * 
	 * @return A new instance of the specified {@link Class class}
	 * 
	 * @throws ObixRuntimeException If an exception is thrown by the underlying reflections 
	 * API in the course of the operation.
	 */
	public static <T> T createObjectFromClass(String className)
	{
		T result;

		try
		{
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) Class.forName(className);
			result = clazz.newInstance();

		}catch (ClassNotFoundException exce){
			String errorMessage = "Unable to load class '"
			                + className
			                + "'. Please see embedded exception for details.";
			throw new ObixRuntimeException(errorMessage, exce);
		}catch (InstantiationException exce){
			String errorMessage = "Unable to instantiate object from class '"
			                + className
			                + "'. Please see embedded exception for details.";
			throw new ObixRuntimeException(errorMessage, exce);
		}catch (IllegalAccessException exce){
			String errorMessage = "Unable to access class '"
			                + className
			                + "'. Please check modifiers!";
			throw new ObixRuntimeException(errorMessage, exce);
		}
		return result;
	}

	/**
	 * This method tests if the specified text is a valid {@link Long}. 
	 * Put differently, it tests of the {@link String string} can be used to obtain a 
	 * long by calling the {@link #toLongValue(String)} method.
	 * 
	 * @param candidateString The {@link String string} to test. Note that the {@link String
	 * string} can be suffixed with the qualifier 'L' to differentiate it from an 
	 * {@link Integer int}.  
	 * @return <code>True</code> if the String is a valid {@link Long long}, else it
	 *  returns <code>False</code>
	 */
	public static boolean isValidLong(String candidateString)
	{
		boolean result = false;
		
		try {
			toLongValue(candidateString);
			result = true;
		} catch (Exception exce) {}
		
		return result;
	}// end method def
	
	/**
	 * <p>
	 * Parses the given candidate string into a {@link Long}.
	 * </p>
	 * @param candidateString	The string to parse.
	 * @return	A {@link Long} representing the given string, 
	 * assuming that it is actually a valid long.
	 * @throws NumberFormatException If unable to parse the given string.
	 */
	public static Long toLongValue(String candidateString)
	{
		Long result;
		if (candidateString==null || candidateString.length()==0)
	        throw new NumberFormatException("Cannot parse zero length string.");
			// remove qualifier first
		if (candidateString.length() > 2
				&& (candidateString.endsWith("l") || candidateString
						.endsWith("L")))
			candidateString = 
				candidateString.substring(0, candidateString.length() - 1);

		result = new Long(candidateString);
		
		return result;
	}

	/**
	 * Tests if the specified {@link String string} is a valid {@link Boolean boolean}. 
	 * This condition is satisfied if the supplied {@link String string} is equivalent to 
	 * either of the string literals <code>"true"</code> or 
	 * <code>"false"</code>, irrespective of case.
	 * 
	 * @param candidateString The candidate {@link String string} to test.
	 * @return <code>True</code> if the {@link String string} is a valid {@link Boolean boolean}, 
	 * and <code>False</code> otherwise.
	 */
	public static boolean isValidBoolean(String candidateString)
	{
		boolean result = false;
		if (candidateString != null){
			if (candidateString.equalsIgnoreCase("true")
			     || candidateString.equalsIgnoreCase("false")) 
			result = true;
		}
		return result;
	}// end method def

	/**
	 * Test if the specified {@link String string} is a valid {@link Integer integer}.
	 * The {@link String string} is considered a valid {@link Integer integer} if, and only if, it 
	 * can be passed as argument to the method {@link Integer#parseInt(String)}.
	 * 
	 * @param candidateString	The candidate {@link String string} to test.
	 * @return <code>True</code> if the argument is a valid {@link Integer integer}, 
	 * and <code>False</code> otherwise.
	 */
	public static boolean isValidInteger(String candidateString)
	{
		boolean result = false;

		try{
			if (candidateString != null){
				Integer.parseInt(candidateString);
				result = true; // if here, then we are
			}
		}catch (Exception exce){}
		return result;
	}// end method def

	/**
	 *<p>
	 *Tests if a {@link String string} is a valid {@link Double double}. A 
	 *{@link String string} is considered a valid double if it can be 
	 *used as argument to the method {@link Double#parseDouble(String)}. 
	 * </p>
	 * 
	 * @param candidateString	The candidate {@link String string} to test.
	 * @return <code>True</code> if the argument is a valid double, 
	 * and <code>False</code> otherwise.
	 */	
	public static boolean isValidDouble(String candidateString)
	{
		boolean result = false;

		try{
			if (candidateString != null){
				Double.parseDouble(candidateString);
				result = true; // if here, then we are
			}
		}catch (Exception exce){}
		return result;
	}// end method def
	

	/**
	 *<p>
	 *Tests if a {@link String string} is a valid {@link Float float}. A 
	 *{@link String string} is considered a valid float if it can be 
	 *used as argument to the method {@link Float#parseFloat(String)}. 
	 * </p>
	 * 
	 * @param candidateString	The candidate {@link String string} to test.
	 * @return <code>True</code> if the argument is a valid float, 
	 * and <code>False</code> otherwise.
	 */		
	public static boolean isValidFloat(String candidateString)
	{
		boolean result = false;

		try{
			if (candidateString != null){
				Float.parseFloat(candidateString);
				result = true; // if here, then we are
			}
		}catch (Exception exce){}
		return result;		
	}

	/**
	 * <p>
	 * Validates a {@link DateFormat format} {@link String string}. 
	 * </p>
	 * 
	 * @param pattern	The candidate {@link String string} to test.
	 * @return <code>True</code> if the argument is a valid 
	 * {@link DateFormat date format}, and <code>False</code> otherwise.
	 */	
	public static boolean isDateFormatValid(String pattern)
	{
		boolean result = false;
		try{
			new SimpleDateFormat(pattern);
			result = true; // if here, then format must be valid
		}catch (Throwable t){}
		return result;
	}

	/**
	 * Validates a {@link DecimalFormat decimal format}. 
	 * 
	 * @param pattern	The candidate {@link String string} to test.
	 * @return <code>True</code> if the argument is a valid {@link DecimalFormat decimal 
	 * format}, and <code>False</code> otherwise.
	 */	
	public static boolean isDecimalFormatValid(String pattern)
	{
		boolean result = false;
		try
		{
			new DecimalFormat(pattern);
			result = true; 	// pattern must be valid not to have
				       		// thrown
						//an exception
		}catch (Throwable e){}
		return result;
	}

	/**
	 * <p>
	 * Tests that the candidate string is not null and contains
	 * at least one non-whitespace character.
	 * </p>
	 * 
	 * @param candidate	The string to test.
	 * 
	 * @return <code>True</code> if the candidate string is null,
	 * is of length zero, or consists of only whitespace characters;
	 * and <code>False</code> otherwise.
	 */
	public static boolean isEmptyString(String candidate)
	{
		boolean result = true;
		
		if (candidate!=null && candidate.trim().length()>0)
			result = false;
		
		return result;
	}
	
}// end class def