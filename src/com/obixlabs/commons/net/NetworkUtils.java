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

package com.obixlabs.commons.net;

import static com.obixlabs.commons.net.ObixNetAPIConstants.EMAIL_ADDRESS_SEPERATOR_CHAR;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.obixlabs.commons.ObixException;
import com.obixlabs.commons.ObixRuntimeException;
import com.obixlabs.commons.io.IOResourceCloser;
import com.obixlabs.commons.io.StreamReadWriteUtilities;

/**
 * <p>
 * A utility class for network related operations.
 * </p>
 */
public final class NetworkUtils
{
	public static String[] VALID_URL_SCHEMES = 
					{ "HTTP:", "GOPHER:", "FTP:", "FILE:" };

	/**
	 * <p>
	 * Private default constructor to prevent accidental initialisation.
	 * </p>
	 */
	private NetworkUtils() {}

	/**
	 * <p>
	 * Checks that the specified string meets the requirements of the
	 * {@link URL} specification.
	 * </p>
	 * 
	 * @param candidateString	The string to be validated.
	 * @return <code>True</code> if the specified string qualifies as a
	 * {@link URL} and <code>False</code> otherwise.
	 */
	public static boolean isValidURL(String candidateString)
	{
		boolean result;
		try
		{
			new URL(candidateString);
			result = true;
		}
		catch (Throwable throwable)
		{
			result = false;
		}
		return result;
	}

	/**
	 * <p>
	 * Reads the data at the specified {@link URL} or times 
	 * out if unable to connect to the location within 
	 * the specified interval. 
	 * </p>
	 * 
	 * @param location The {@link URL} to be read.
	 * @param timeout The timeout for accessing the location. <b>Note</b> that a
	 * timeout of zero is interpreted as infinity, and that negative
	 * timeouts are not permitted.
	 * @return A byte array which contains the data read from the {@link URL}.
	 * @throws ObixException If an error occurs reading the data at the specified
	 * {@link URL}.
	 * 
	 * @see StreamReadWriteUtilities#readStreamContentsAsBytes(InputStream)
	 */
	public static byte[] downloadRemoteData(URL location, 
											int timeout)
											throws ObixException
	{
		byte[] result;
		result = downloadRemoteData(location, null,timeout);		
		return result;
	}

	
	/**
	 * <p>
	 * Reads the data at the specified {@link URL}, using the given {@link Proxy} 
	 * or times out if unable to connect to the location within the 
	 * specified interval. 
	 * </p>
	 * 
	 * @param location The {@link URL} to be read.
	 * @param proxy The {@link Proxy} 
	 * @param timeout The timeout for accessing the location. <b>Note</b> that a
	 * timeout of zero is interpreted as infinity, and that negative
	 * timeouts are not permitted.
	 * @return A byte array which contains the data read from the {@link URL}.
	 * @throws ObixException If an error occurs reading the data at the specified
	 * {@link URL}.
	 * 
	 * @see StreamReadWriteUtilities#readStreamContentsAsBytes(InputStream)
	 */	
	public static byte[] downloadRemoteData(
										URL location, Proxy proxy,
										int timeout) throws ObixException
	{
		byte[] result;

		URLConnection dataConnection;
		BufferedInputStream bufferedStream = null;
		try
		{
			if (proxy != null)
				dataConnection = location.openConnection(proxy);
			else
				dataConnection = location.openConnection();

			dataConnection.setConnectTimeout(timeout);
			dataConnection.connect();
			InputStream downloadStream 
								= dataConnection.getInputStream();
			bufferedStream = 
				StreamReadWriteUtilities.bufferStream(downloadStream);

			result = 
					StreamReadWriteUtilities
										.readStreamContentsAsBytes(
																		bufferedStream);
		}
		catch (IOException exce)
		{
			throw new ObixException(
					"Connection to specified location '" + location
					+ "' failed. Consequently unable to download remote data.",
					exce);
		}
		finally 
		{ 
			IOResourceCloser.close(bufferedStream);
			
		}

		return result;
	}	
	
	
	/**
	 * <p>
	 * Method which determines if a candidate input string is a valid email
	 * address.
	 * </p>
	 * 
	 * <p>
	 * It accepts the supplied string as an email address, provided that it
	 * consists of a name and a domain part, both of which should be separated
	 * by the "@" symbol . <b>Note</b> that technically, this is the most basic
	 * requirement that an email address has to fulfil. As a consequence, you
	 * should consider the alternative of using a regular expression to test
	 * address validity.
	 * </p>
	 * 
	 * @param emailAddress The candidate email address string.
	 * @return <code>True</code> if the email address has a valid name and
	 * domain part, else <code>False</code>.
	 * 
	 * @see #hasNameAndDomain(String)
	 */
	public static boolean isValidEmailAddress(String emailAddress)
	{
		if (emailAddress == null) return false;

		boolean result = true;
		try
		{
			new InternetAddress(emailAddress); // trigger exception on bad value
			if (!hasNameAndDomain(emailAddress)) result = false;
		}
		catch (AddressException addressException)
		{
			result = false;
		}
		return result;
	}

	/**
	 * <p>
	 * Parses a concatenation of email addresses into a list of strings. It
	 * assumes that the addresses are delimited by the ";" character.
	 * </p>
	 * 
	 * @param addresses The addresses to be parsed.
	 * @return A {@link List} of strings, where each element corresponds to a
	 * single email address as obtained by splitting the concatenated
	 * addresses.
	 */
	public static List<String> parseEmailAddresses(String addresses)
	{
		List<String> result = new ArrayList<String>();

		StringTokenizer splitter = new StringTokenizer(addresses,
				EMAIL_ADDRESS_SEPERATOR_CHAR);

		String nextToken;
		while (splitter.hasMoreElements())
		{
			nextToken = splitter.nextToken();
			if (nextToken != null) result.add(nextToken.trim());
		}
		return result;
	}

	/**
	 * <p>
	 * Returns the first available proxy for the given URL.  
	 * </p>
	 * 
	 * @param url	The URL for which to return a proxy.
	 * @return	A proxy which can be used to reach the given URL if one
	 * exists, and false otherwise.
	 */
	public static Proxy getProxy(URL url)
	{
		Proxy result;
		
		try
		{
			ProxySelector selector = ProxySelector.getDefault();
			List<Proxy> proxies = selector.select(url.toURI());
			
			if (proxies!=null && !proxies.isEmpty())				
				result = proxies.get(0);
			else result = null;
		}
		catch (URISyntaxException syntaxException)
		{ throw ObixRuntimeException.wrapException(syntaxException); }
		
		return result;
	}
	
	/**
	 * <p>
	 * A delegate method which verifies that the given string consists of a name
	 * and domain portion, joined by an '@' symbol.
	 * </p>
	 * 
	 * @param emailAddress The candidate string to be tested.
	 * @return <code>True</code> if the candidate string meets the expected
	 * format and <code>False</code> otherwise.
	 */
	private static boolean hasNameAndDomain(String emailAddress)
	{
		String[] tokens = emailAddress.split("@");
		return (tokens.length == 2 && tokens[0].length() > 0 && tokens[1]
				.length() > 0);
	}
}

// XXX: stub for javax.* imports
class InternetAddress {

    public InternetAddress(String email) throws AddressException {}

}

class AddressException extends Exception {

    public AddressException() {}

}

