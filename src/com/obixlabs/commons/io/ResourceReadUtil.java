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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import com.obixlabs.commons.ObixException;
import com.obixlabs.commons.ObixRuntimeException;
import com.obixlabs.commons.net.NetworkUtils;

/**
 * <p>
 * Resource loading utilities.
 * </p>
 */
public final class ResourceReadUtil
{
	/**
	 * <p>
	 * Private default constructor 
	 * for utility class.
	 * </p>
	 */
	private ResourceReadUtil(){}
	
	/**
	 * <p>
	 * Reads the resource at the given location. The location
	 * can either be a URL, 
	 * {@link ClasspathResourceUtils#isClasspathURL(String) 
	 * classpath URL}, or file.
	 * </p>
	 * 
	 * @param location	The location from which the resource should 
	 * be read.
	 * @param requester	The object which is requesting the resource.
	 * @return	The data at the specified location in raw byte form.
	 * @throws ObixException	If an error occurs reading the 
	 * specified resource.
	 */
	public static byte[] loadResourceData(	String location,
											Object requester) 
											throws 	ObixException
	{
		byte[] result;
		
		try
		{
			if (NetworkUtils.isValidURL(location))
			{
				URL locationAsURL = new URL(location);
				result = NetworkUtils.downloadRemoteData(
							locationAsURL, 0);
			}
			else if (ClasspathResourceUtils.isClasspathURL(location))
				result = ClasspathResourceUtils.getClasspathResource(
							location, requester); 
			else result = FileUtilities.readFileContentsAsBytes(
													new File(location)); 
		}
		catch (MalformedURLException exception)
		{
			String error = 
				MessageFormat.format(
				"URL parsing \"{0}\" failed despite URL having been validated." , 
				location);
			throw new ObixRuntimeException(error);
		}
		
		return result;
	}
}