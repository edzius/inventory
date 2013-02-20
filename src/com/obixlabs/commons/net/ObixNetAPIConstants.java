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

import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;

/**
 * <p>
 * Definitions of constants which are 
 * applicable within the obix network utilities package.
 * </p> 
 */
interface ObixNetAPIConstants 
{        

	/**
	 * <p>
	 * A separator that can be used to delineate multiple email addresses, 
	 * specified as a single contiguous string. For an 
	 * example of its application see {@link NetworkUtils#parseEmailAddresses(String)}.
	 * </p>
	 */
	String EMAIL_ADDRESS_SEPERATOR_CHAR = ";";
	
	/**
	 * <p>
	 * An encoding type that can be applied to email attachments. Popular desktop software
	 * should generally attempt to open up attachments, with this encoding type, using 
	 * the appropriate software for the file type--as defined in the operating system 
	 * file-to-application mappings.
	 * </p>
	 *  
	 * @see ObixEmailSender#addAttachment(java.io.File)
	 * @see ObixEmailSender#addAttachment(String, byte[])
	 * @see ObixEmailSender#addAttachment(String, String) 
	 */
	String APPLICATION_OCTET_STREAM_MIME_CONTENT_TYPE="application/octet-stream";
	
	/**
	 * <p>
	 * The content type to be applied to mail messages to indicate that 
	 * the message can consist of multiple {@link BodyPart}s  (e.g. plain-text/HTML),
	 * and attachments.
	 * </p>
	 */
	String MIME_MIXED_CONTENT_HEADER="mixed";
	
        /**
         * <p>
         * The content-type that is applied to an email {@link MimeBodyPart}  to indicate 
         * that the email consists of a plain text portion, and also (possibly) a 
         * HTML part. Where the {@link MimeBodyPart} consists of a {@link #MIME_CONTENT_TYPE_TEXT_HTML}
         * part, it would be used as the preferred format by all mail readers capable of interpreting HTML.
         * </p> 
         */
        String MIME_MULTIPART_ALTERNATIVE_HEADER="alternative";
        	
	/**
	 * <p>
	 * Content type which is applied to HTML message {@link BodyPart}s. 
	 * </p>
	 * 
	 * @see #MIME_MIXED_CONTENT_HEADER
	 */
	String MIME_CONTENT_TYPE_TEXT_HTML="text/html";

	/**
	 * <p>
	 * Content type which is applied to plain-text message {@link BodyPart}s.
	 * </p>
	 * 
         * @see #MIME_MIXED_CONTENT_HEADER 
	 */
	String MIME_CONTENT_TYPE_TEXT="text/plain";      	
}
