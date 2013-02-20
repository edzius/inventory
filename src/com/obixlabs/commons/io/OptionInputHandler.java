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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * A {@link ConsoleInputHandler} implementation which can validate user 
 * input against a predefined list of allowed values. Where the options 
 * list is empty, instances of this class will accept any non-empty and non-null
 * value input by the user.
 * </p>
 *  
 *  <p>
 * Instances of this type can be used in conjunction with 
 * {@link InteractiveCommandLineReader} for multi-choice user input.
 * </p>
 *    
 * @see ConsoleInputHandler
 * @see InteractiveCommandLineReader
 */
public class OptionInputHandler extends AbstractConsoleInputHandler<String>
{
	private static final long serialVersionUID = -4863562162851449313L;

    /**
     * <p>
     * The set of input options which are acceptable to the handler.
     * </p>
     */
    private Set<String> restrictions;        
                
	/** 
	 * <p>
	 * Constructs a new instance with the specified initial and repeat prompts, and 
	 * list of options.
	 * </p>
	 * 
	 * @param prompt The initial input prompt displayed to the user.
	 * @param repeatPrompt The input prompt to be displayed to the user subsequent 
	 * to at least one input validation error.
	 * @param validOptions The list of allowed input values. Where this list is 
	 * null, validation will be restricted to ensuring that the input is not null
	 * and not empty. 
	 */
	public OptionInputHandler(	String prompt,  String repeatPrompt, 
							        Set<String> validOptions)
	{
		super(prompt,repeatPrompt);
		restrictions = validOptions;
	}        
        
        
	/** 
	 * <p>
	 * Constructs a new instance with the specified initial and repeat prompts, and 
	 * list of options.
	 * </p>
	 * 
	 * @param prompt The initial input prompt displayed to the user.
	 * @param repeatPrompt The input prompt to be displayed to the user subsequent 
	 * to at least one input validation error.
	 * @param validOptions The list of allowed input values. Where this list is 
	 * null, validation will be restricted to ensuring that input is not null
	 * and not empty.
	 */
	public OptionInputHandler(	String prompt, 
							        String repeatPrompt, 
							        String... validOptions)
	{
		super(prompt,repeatPrompt);
		if (validOptions!=null)
			restrictions = new HashSet<String>(Arrays.asList(validOptions));
		else restrictions = new HashSet<String>();
	}
		
	@Override
	public boolean setInputValue(String value)
	{
		boolean result=true;
		if (restrictions.size()>0 && 
					restrictions.contains(value))
			internalSetInputValue(value);
		else if (restrictions.size()==0 && 
				value!=null && 
				value.length()>0)
			internalSetInputValue(value);
		else result = false;		
		return result;
	}
}
