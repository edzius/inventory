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

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>
 * A utility class for manipulating exceptions.
 * </p>
 */
public final class ExceptionUtil
{
	/**
	 * <p>
	 * Default private constructor to prevent accidental initialisation.
	 * </p>
	 */
	private ExceptionUtil(){}
	
    /**
     * <p>
     * Returns the stack trace of the given exception as a string.
     * </p>
     * 
     * @param t The exception whose stack trace is to be unwrapped and returned as a string.
     * @return A string representation of the exception's stack trace
     */
    public static String getStackTrace(Throwable t)
    {
        StringBuffer result = new StringBuffer();

        try
        {
            StringWriter writeBuffer=new StringWriter();
            PrintWriter printBuffer=new PrintWriter(writeBuffer);

            //write the stack buffer into the StringWriter
            t.printStackTrace(printBuffer);

            printBuffer.flush();
            printBuffer.close();

            result.append(writeBuffer.toString());
        }
        catch (Throwable t2)
        {
            //ignore all
        }
        return result.toString();
    }    
}//end class def