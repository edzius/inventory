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

import com.obixlabs.commons.types.PrimitiveTypeUtilities;

/**
 * <p>
 * A {@link ConsoleInputHandler} implementation which handles integer input. 
 * Instances of this type can be used in conjunction with {@link InteractiveCommandLineReader}
 * to prompt users to for, read and validate integer values.   
 * </p>
 * 
 * @see InteractiveCommandLineReader
 * @see PrimitiveTypeUtilities#isValidInteger(String)
 */
public class IntegerConsoleInputHandler extends AbstractConsoleInputHandler<Integer>
{
	
	private static final long serialVersionUID = -7879031778926761053L;
	
	/**
	 * <p>
	 * Builds an instance using the specified initial and repeat prompts.
	 * </p>
	 * 
	 * @param prompt The default(initial) input prompt.
	 * @param repeatPrompt The input prompt to be used after the first (or any) 
	 * user entry that is not a valid integer.
	 * 
	 * @see AbstractConsoleInputHandler#AbstractConsoleInputHandler(String, String)
	 */		
	public IntegerConsoleInputHandler(String prompt, String repeatPrompt)
	{ super(prompt, repeatPrompt); }

	@Override
	public boolean setInputValue(String value)
	{
		boolean result = true;		
		if (PrimitiveTypeUtilities.isValidInteger(value))
			internalSetInputValue(new Integer(value));
		else result = false;		
		return result;
	}
}