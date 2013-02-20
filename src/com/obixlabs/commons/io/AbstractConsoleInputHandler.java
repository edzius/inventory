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

/**
 * <p>
 * Base implementation of {@link ConsoleInputHandler} which provides
 * boiler-plate functionality to facilitate the implementation of other
 * handlers.
 * </p>
 */
public abstract class AbstractConsoleInputHandler<T> implements ConsoleInputHandler<T>
{
        private static final long serialVersionUID = 8148894618974113883L;

        /**
         * <p>
         * The value to be used as the input prompt. This the value that is
         * displayed on the command line to prompt the user to specify an input
         * value.
         * </p>
         * 
         * @see ConsoleInputHandler#getPrompt()
         */
        private String prompt;

    	/**
    	 * <p>
    	 * The last value {@link #setInputValue(String) accepted} by the 
    	 * encappsulating instance.
    	 * </p>
    	 */        
        private T inputValue;
        
        /**
         * <p>
         * The value to be used as the repeat input prompt. As per the contract
         * of {@link ConsoleInputHandler}, this value is only displayed to the
         * user post the first input failure.
         * </p>
         * 
         * @see ConsoleInputHandler#getRepeatPrompt()
         * @see ConsoleInputHandler#setInputValue(String)
         */
        private String repeatPrompt;

        /**
         * <p>
         * Builds an instance with the specified prompt and repeat-prompt.
         * </p>
         * 
         * @param prompt    The default(initial) input prompt.
         * @param repeatPrompt  The input prompt to be used after the first (or any) failure to validate user input.
         */
        public AbstractConsoleInputHandler(String prompt, String repeatPrompt)
        {
                super();
                this.prompt = prompt;
                this.repeatPrompt = repeatPrompt;
        }

        @Override
        public String getPrompt() {return prompt;}

        @Override
        public String getRepeatPrompt() {return repeatPrompt;}

        /**
         * Internal delegate method which is used to set the
         * {@link #inputValue value} encapsulated by an instance 
         * of this type. 
         * 
         * @param value Given that this method should be invoked 
         * from {@link #setInputValue(String)}, this value 
         * should be the same as that with which the delegating method 
         * is invooked.
         */
        protected void internalSetInputValue(T value)
        {this.inputValue = value;}

        @Override
    	public T getInputValue() {return inputValue;}        
}// end class def
