/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bop.common.utils;

/**
 * @author Marco Ruiz
 */
public class StringQuoter {

    private static final int BRACKET_LENGTH = 1;
    
	private final int quoteAffixTotalLength;
    private final char quoteOpen, quoteClose, quoteIgnore;

	public StringQuoter(int quoteAffixTotalLength, char quoteOpen, char quoteClose, char quoteIgnore) {
		this.quoteAffixTotalLength = quoteAffixTotalLength;
		this.quoteOpen = quoteOpen;
		this.quoteClose = quoteClose;
		this.quoteIgnore = quoteIgnore;
	}

	public int getQuoteAffixTotalLength() {
		return quoteAffixTotalLength;
	}

	public char getQuoteOpen() {
		return quoteOpen;
	}

	public char getQuoteClose() {
		return quoteClose;
	}

	public char getQuoteIgnore() {
		return quoteIgnore;
	}
    
	public String format(String target, int openQuoteId, int closeQuoteId) {
		char startChar = getBracket(openQuoteId, quoteOpen);
		char endChar = getBracket(closeQuoteId, quoteClose);
		return getAffixString(openQuoteId, true) + startChar + target + endChar + getAffixString(closeQuoteId, false);
	}

	private char getBracket(int openQuoteId, char bracket) {
		return (openQuoteId < 0) ? quoteIgnore : bracket;
	}
    
    public String getAffixString(int number, boolean prefix) {
        String numberStr = (number < 0) ? "" : Integer.toString(number + 1);
        String spaces = createPaddingSpaces().substring(numberStr.length() + BRACKET_LENGTH);
        return (prefix) ? numberStr + spaces : spaces + numberStr;
    }

	public String createPaddingSpaces() {
		return new String(new char[quoteAffixTotalLength]).replace("\0", " ");
	}
}

