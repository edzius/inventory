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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>
 * Utility class which provides a means of reading and validating 
 * user command-line input. 
 * </p>
 */
public class InteractiveCommandLineReader
{	
	/**
	 * <p>
	 * This method utilises an instance of the class {@link ConsoleInputHandler} to 
	 * validate user input. The latter class not only performs the 
	 * validation of the input, but also provides the prompt and 
	 * repeat prompts with which the input is requested. 
	 * </p>
	 * <p>
	 * The prompt taken from the {@link ConsoleInputHandler#getPrompt()} instance 
	 * is used for the initial request prompt. Upon the first input validation 
	 * failure, the repeat prompt, as given by {@link ConsoleInputHandler#getRepeatPrompt()}, 
	 * is used from then onwards. 
	 * </p>
	 * 
	 * @param inputHandler The input handler to be used for validation,
	 * and which provides input prompt values.
	 * @throws IOException If an I/O error occurs while reading the user input.
	 */
	public static void prompt(	ConsoleInputHandler<?> inputHandler) 
							throws IOException
	{		
		String input;
		BufferedReader reader= 
			new BufferedReader(new InputStreamReader(System.in));
		System.out.print(inputHandler.getPrompt());
		input = reader.readLine();
		while (!inputHandler.setInputValue(input))
		{
			System.out.println("\n" + inputHandler.getRepeatPrompt());
			input = reader.readLine();
		}
	}	
	
}