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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.obixlabs.commons.ObixException;

/**
 * <p>
 * A utility class for performing file I/O operations.
 * </p>
 * 
 * @see StreamReadWriteUtilities
 */
public final class FileUtilities
{
	private static final String TMP_FOLDER_PROPERTY = "java.io.tmpdir";

	private FileUtilities()
	{}

	/**
	 * <p>
	 * Returns a reference to the system folder designated for storing temporary
	 * files. This differs across platforms, but is identified by the value of
	 * the Java property {@value #TMP_FOLDER_PROPERTY}.
	 * </p>
	 * 
	 * @return A {@link File} handle to the folder identified by the Java
	 *         property {@value #TMP_FOLDER_PROPERTY}.
	 */
	public static File getSystemTempFolder()
	{
		String tempFolder = System.getProperty(TMP_FOLDER_PROPERTY);
		return new File(tempFolder);
	}

	/**
	 * <p>
	 * Recursively deletes the contents of the specified folder, and, if
	 * successful, the folder itself. This method circumvents the Java
	 * limitation of not being able to delete non-empty directories.
	 *</p>
	 *<p>
	 * <b>Note:</b> That if this method is applied to a file, the file is simply
	 * deleted.
	 *</p>
	 * 
	 * @param candidateFolder
	 *            The folder to delete.
	 * 
	 * @return <code>True</code> if the folder is successfully deleted, and
	 *         <code>False</code> otherwise.
	 */
	public static boolean tryAndDeleteFolder(File candidateFolder)
	{
		boolean result;

		if (candidateFolder.isDirectory())
		{
			File[] contents = candidateFolder.listFiles();
			if (contents != null && contents.length > 0)
			{
				result = true;
				for (File file : contents) // delete children first
				{
					result = FileUtilities.tryAndDeleteFolder(file);
					if (!result) break;
				}

				// then delete parent
				if (result) result = candidateFolder.delete();
			}
			else
				result = candidateFolder.delete();
		}
		else
			result = candidateFolder.delete();

		return result;
	}

	/**
	 * <p>
	 * Reads the contents of the specified file into a list of strings, where
	 * each entry in the list represents a line in the input file. Line-breaks
	 * are identified by the value of the <code>delimiter</code> parameter.
	 * </p>
	 * 
	 * @param inputFile
	 *            The file to be read.
	 * @param delimiter
	 *            A character which denotes the line delimiter used in the input
	 *            file.
	 * @return A list of strings, where each element in the list corresponds to
	 *         a line in the input file.
	 * @throws IOException
	 *             If an error occurred opening or reading the specified file.
	 * @see StreamReadWriteUtilities#readLinesFromStream(InputStream,String,
	 *      int)
	 */
	public static List<String> readLinesFromFile(File inputFile,
			String delimiter) throws ObixException
	{
		List<String> results;
		FileInputStream fileStream = null;
		try
		{
			fileStream = new FileInputStream(inputFile);
			results = StreamReadWriteUtilities.readLinesFromStream(fileStream,
					delimiter, (int) inputFile.length());
		}
		catch (IOException exce)
		{
			throw new ObixException(exce);
		}
		finally
		{
			IOResourceCloser.close(fileStream);
		}
		return results;
	}

	/**
	 * <p>
	 * Reads the contents of the specified file into a byte array.
	 * </p>
	 * 
	 * @param inputFile The file to be read.
	 * 
	 * @return The contents of the specified file, as a byte array.
	 * @throws ObixException
	 *             If an error occurs while reading the file.
	 */
	public static byte[] readFileContentsAsBytes(File inputFile)
			throws ObixException
	{
		byte[] result = null;
		FileInputStream fileInputStream = null;

		try
		{
			if (inputFile.exists() && inputFile.isFile())
			{
				fileInputStream = new FileInputStream(inputFile);
				result = StreamReadWriteUtilities.readStreamContentsAsBytes(
						fileInputStream, (int) inputFile.length());
			}
			else
				throw new FileNotFoundException("The specified path '"
						+ inputFile.getAbsolutePath()
						+ "' does not appear to be a valid configuration-file");
		}
		catch (IOException io_exce)
		{
			throw new ObixException(io_exce);
		}
		finally
		{
			IOResourceCloser.close(fileInputStream);
		}
		return result;
	}

	/**
	 * <p>
	 * Appends a list of strings to a file, where each element in the list is
	 * treated as a separate line in the destination file. Each line is
	 * terminated with the specified end-of-line character, with the exception
	 * of the last line in the list.
	 * </p>
	 * 
	 * </p> Note that if the specified file does not exist, this method will
	 * create the file and all missing parent directories. </p>
	 * 
	 * @param file The file to which the data is to be written.
	 * @param lines The lines to be written to file.
	 * @param eolDelimeter The line delimiter to be used in formatting 
	 * the output data.
	 * @throws ObixException If an error occurs performing I/O operations 
	 * against the specified file.
	 * 
	 * @see #writeBytesToFile(File, byte[])
	 * @see #writeBytesToFile(File, byte[], boolean)
	 */
	public static void writeLinesToFile(File file, List<String> lines,
			String eolDelimeter) throws ObixException
	{
		StringBuffer dataBuffer = new StringBuffer();

		String line;
		int size = lines.size();
		for (int i = 0; i < size; i++)
		{
			line = lines.get(i);
			dataBuffer.append(line);

			// if not the last line, then add eol
			if (i != (size - 1)) dataBuffer.append(eolDelimeter);
		}

		writeBytesToFile(file, dataBuffer.toString().getBytes());
	}

	/**
	 * <p>
	 * Writes the specified byte array to the specified file. It will create the
	 * file and any parent directories if it does not already exist. Where the
	 * file does exist, it will simply append the data to it.
	 * </p>
	 * 
	 * @param file The file to which to write data to.
	 * @param data The data to be written to file.
	 * @throws ObixException If an I/O error occurs accessing or writing 
	 * to the file.
	 * 
	 * @see #writeBytesToFile(File, byte[], boolean)
	 * @see #writeLinesToFile(File, List, String)
	 */

	public static void writeBytesToFile(File file, byte[] data)
			throws ObixException
	{ writeBytesToFile(file, data, false); }

	/**
	 * <p>
	 * Writes the contents of the byte array to the specified file. Unlike
	 * {@link #writeBytesToFile(File, byte[])}, this method allows you to
	 * control whether or not the target file is overwritten with the new data
	 * or if the data is simply appended to it. In all other respects, it is
	 * similar to {@link #writeBytesToFile(File, byte[])}. Specifically, it will
	 * also create the file and any non-existent parent directories where these
	 * do not already exist.
	 * </p>
	 * 
	 * @param file The file to which the data is to be written.
	 * @param data The data to write to the output file.
	 * @param append 	Indicates if the data should be appended to the file, 
	 * 					should the file already exist, or if its contents should be
	 * 					overwritten.
	 * 
	 * @throws ObixException 	If an exception occurs while writing the data to 
	 * 							the output file.
	 * 
	 * @see StreamReadWriteUtilities#writeBytesToStream(java.io.OutputStream,
	 *      byte[])
	 */
	public static void writeBytesToFile(File file, byte[] data, boolean append)
			throws ObixException
	{
		FileOutputStream outputStream = null;
		try
		{
			if (!file.exists())
			{
				// create directory tree first
				if (file.getParentFile() != null)
					file.getParentFile().mkdirs();
			}

			outputStream = new FileOutputStream(file, append);
			StreamReadWriteUtilities.writeBytesToStream(outputStream, data);
		}
		catch (IOException exce)
		{
			throw new ObixException(exce);
		}
		finally
		{
			IOResourceCloser.close(outputStream);
		}
	}// end method def

}