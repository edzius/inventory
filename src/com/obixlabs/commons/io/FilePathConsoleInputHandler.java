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

import java.io.File;

/**
 * <p>
 * A {@link ConsoleInputHandler} implementation which handles file URI inputs. 
 * Instances of this type can be used in conjunction with {@link InteractiveCommandLineReader} to 
 * prompt users for, read and validate file URIs.
 * </p>
 *    
 * @see ConsoleInputHandler
 * @see InteractiveCommandLineReader
 */
public class FilePathConsoleInputHandler extends AbstractConsoleInputHandler<File>
{
	private static final long serialVersionUID = -4824004200184107351L;
        
        /**
         * <p>
         * A property which indicates that this instance should only accept 
         * input that identifies valid and readable files or directories.
         * </p>
         */
	private boolean forReadableFile;
		
	/**
	 * <p>
	 * Constructs a new instance with the specified initial and repeat prompts, and also
	 * a boolean value which indicates whether or not this instance should only 
	 * accept values that identify existing and readable files.
	 * </p>
	 * 
	 * @param prompt The initial input prompt displayed to the user.
	 * @param repeatPrompt The input prompt to be displayed to the user subsequent 
	 * to at least one validation error in the user input.
	 * @param forReadableFile A value of <code>True</code> indicates that the created instance 
	 * should only accept values that are URI of existing and readable files. A value of 
	 * <code>False</code>, on the other hand, indicates that this instance should treat 
	 * all input values merely as candidate file URIs.
	 */
	public FilePathConsoleInputHandler(String prompt, 
								                String repeatPrompt, 
								                boolean forReadableFile)
	{
		super(prompt,repeatPrompt);
		this.forReadableFile = forReadableFile;
	}
	
	/**
	 * <p>
	 * Indicates if the instance is configured to only accept URI values that identify
	 * valid and readable files.
	 * </p>   
	 * @return <code>True</code> if this instance is configured to only 
	 * accept values that identify existing and readable files. A return value of 
	 * <code>False</code> on the other hand indicates that this instance
	 * will treat any input as a potential file URI.
	 */
	public boolean isForReadableFile(){return forReadableFile;}
	
	@Override
    public boolean setInputValue(String value)
    {
        boolean result;
        
        if (value==null || value.length()==0)
        	result = false;
        else 
        {
        	File candidatePath = new File(value.trim());
        	if (isForReadableFile() && 
        			!(candidatePath.exists() && candidatePath.canRead()))
        		result = false;
        	else
        	{
        		internalSetInputValue(candidatePath);
        		result = true;	        		
        	}
        	
        }
        return result;
    }
}