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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;



/**
 * <p>
 * A utility class which is used for closing open I/O resources such as streams, readers and writers.
 * It is intended to minimise the amount of code that has to be written when making use of 
 * I/O resources, especially with regards to:
 * <ul>
 * 	<li>trapping and logging the exceptions that can occur within finally 
 * 		blocks as a result of closing resources.
 * 	</li>
 * 	<li> testing for null (or already closed) resources.
 * 	</li>
 * </ul>
 * </p>
 * 
 * <p>
 * 	The following example illustrates a typical use for the methods provided by this class:
 * 	<pre>
 * 		Reader reader=null;
 * 		try
 * 		{
 * 			//do some operations
 * 		}
 * 		finally
 * 		{
 * 			//note that reader may still be null prior to this 
 * 			//point
 * 			IOResourceCloser.close(reader);
 * 		}
 * 	</pre>
 * </p>
 * <p>
 * <b>Note:</b> The methods in this class trap and ignore all raised exceptions.
 * </p> 
 */
public final class IOResourceCloser
{	
        /**
         * <p>
         * Default constructor to prevent accidental instantiation.
         * </p>
         */
	private IOResourceCloser() { }
	
	/**
	 * <p>
	 * Closes the specified {@link InputStream}.
	 * </p>
	 * 
	 * @param inputStream The stream to close.
	 */
	public static void close(InputStream inputStream)
	{
		try{
			if (inputStream != null) inputStream.close();
		}catch (Throwable exce){}
	}// end method def

	/**
	 * <p>
	 * Closes the specified {@link Reader} handle. 
	 * </p>
	 * 
	 * @param reader The reader to close.
	 */
	public static void close(Reader reader)
	{
		try{
			if (reader != null) reader.close();
		}catch (Throwable t){}
	}// end method def

	/**
	 * Flushes and closes the specified {@link OutputStream} handle.
	 * 
	 * @param outputStream The stream to close.
	 */
	public static void close(OutputStream outputStream)
	{
		try{
			if (outputStream != null){
				outputStream.flush();
				outputStream.close();
			}
		}catch (Throwable t){}
	}// end method def

	/**
	 * <p>
	 * Flushes and closes the specified {@link Writer}.
	 * </p>
	 * @param writer The writer to close.
	 */
	public static void close(Writer writer)
	{
		try{
			if (writer != null){
				writer.flush();
				writer.close();
			}
		}catch (Throwable t){}
	}// end method def
    
}//end class def