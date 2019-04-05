/*
 * Copyright 2002-2003 the original author or authors.
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

package com.bop.common.math;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Marco Ruiz
 */
public class SequenceMatrix<VAL_T> extends TwoDimensionalMatrixValues<VAL_T> {

    private static int getFirstIndexOfCharacter(String target, char symbol) {
        int len = target.length();
        for (int location = 0; location < len; location++) 
            if (target.charAt(location) == symbol) 
            	return location;

        return -1;
    }

    private final String sequenceA;
    private final String sequenceB;

    public SequenceMatrix(String sequenceA, String sequenceB) {
    	super(sequenceA.length(), sequenceB.length());
        this.sequenceA = sequenceA;
        this.sequenceB = sequenceB;
    }

    public SequenceMatrix(String sequenceA, String sequenceB, VAL_T[][] matrixValues) {
    	super(matrixValues);
        this.sequenceA = sequenceA;
        this.sequenceB = sequenceB;
    }
    
    public VAL_T getScore(char symbolA, char symbolB) {
        int indexA = getIndexOfSymbolInSequenceA(symbolA);
        int indexB = getIndexOfSymbolInSequenceB(symbolB);
        return ((indexA == -1) || (indexB == -1)) ? null : getValue(indexA, indexB);
    }
    
    // SEQUENCES API
    
    public String getSequenceA() {
    	return sequenceA;
    }
    
    public String getSequenceB() {
    	return sequenceB;
    }
    
    public int getIndexOfSymbolInSequenceA(char symbol) {
    	return getFirstIndexOfCharacter(sequenceA, symbol);
    }
	
    public int getIndexOfSymbolInSequenceB(char symbol) {
    	return getFirstIndexOfCharacter(sequenceB, symbol);
    }
	
	public char getSymbol(boolean firstSequence, int index) {
		return firstSequence ? getSymbolInSequenceA(index) : getSymbolInSequenceB(index);
	}

	public char getSymbolInSequenceA(int index) {
		return getCharacter(sequenceA, index);
	}

	public char getSymbolInSequenceB(int index) {
		return getCharacter(sequenceB, index);
	}

	private char getCharacter(String target, int index) {
		return target.charAt(index);
	}

    // TO STRING

    public String toString() {
    	return toString(0);
    }
    
    public String toString(int tabs) {
    	return getArrayValuesAsString(sequenceA.length(), indexA -> getRowValue(indexA, createIndentation(tabs)), tabs);
    }

    private String getRowValue(int indexA, String rowPrefix) {
    	return rowPrefix + getArrayValuesAsString(sequenceB.length(), indexB -> getValue(indexA, indexB).toString(), 0);
    }
    
    private String getArrayValuesAsString(int arrayLength, Function<Integer, String> valueProvider, int indentationLevel) {
    	boolean newLinePerElement = indentationLevel > 0;
		String delimiter = newLinePerElement ? ",\n" : ", ";
    	String bracketsCompanion = newLinePerElement ? "\n" : "";
		String content = IntStream.range(0, arrayLength)
    		.mapToObj(index -> valueProvider.apply(index))
    		.collect(Collectors.joining(delimiter));
		return "{" + bracketsCompanion + content + bracketsCompanion + createIndentation(indentationLevel - 1) + "}";
    }
    
	private String createIndentation(int level) {
		return (level > 0) ? IntStream.range(0, level).mapToObj(idx -> "\t").collect(Collectors.joining()) : "";
	}
}
