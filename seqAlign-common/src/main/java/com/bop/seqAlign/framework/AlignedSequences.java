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

package com.bop.seqAlign.framework;

import java.util.function.Function;

/**
 * @author Marco Ruiz
 */
public class AlignedSequences {
	
	private static void format(StringBuffer target, int startIndex, int endIndex) {
		String startChar = (startIndex < 0) ? " " : ">";
		String endChar = (endIndex < 0) ? " " : "<";
		target.insert(0, getPreffixSuffixString(startIndex, true) + startChar);
		target.append(endChar + getPreffixSuffixString(endIndex, false));
	}
    
    private static String getPreffixSuffixString(int number, boolean preffix) {
        int size = 3;
        byte[] spaces = new byte[size];
        for (int count = 0; count < size; count++) spaces[count] = ' ';
        String numberStr = (number == -1) ? "" : Integer.toString(number + 1);
        String spaces2 = new String(spaces, 0, size - numberStr.length());
        return (preffix) ? numberStr + spaces2 : spaces2 + numberStr;
    }

	private AlignmentSolution solution;
	private final StringBuffer alignedA = new StringBuffer();
	private final StringBuffer alignedB = new StringBuffer();
	private final StringBuffer alignment = new StringBuffer();
	
	private final String alignedAStr, alignedBStr, alignmentStr;
	
	public AlignedSequences(AlignmentSolution solution) {
		this(solution, true);
	}
	
	public AlignedSequences(AlignmentSolution solution, boolean format) {
		this.solution = solution;
		
		for (int index = 1; index < solution.getTransitions().size(); index++) 
			processSymbolAt(index);
		
		if (format) format();
		alignedAStr = alignedA.toString();
		alignedBStr = alignedB.toString();
		alignmentStr = alignment.toString();
	}

	private void processSymbolAt(int index) {
		alignedA.append(solution.getSymbolAt(true, index));
		alignedB.append(solution.getSymbolAt(false, index));
		alignment.append(getAlignmentSymbolAt(index));
	}

	private char getAlignmentSymbolAt(int index) {
		return (alignedA.charAt(index - 1) == alignedB.charAt(index - 1)) ? 
			alignedA.charAt(index - 1) :
			(solution.getReferencedScoreIncrement(index) > 0) ? '+' : ' ';
	}
	
	private void format() {
		format(alignedA, AlignmentTransition::getIndexA);
		format(alignedB, AlignmentTransition::getIndexB);
		format(alignment, -1, -1);
	}
	
	private void format(StringBuffer target, Function<AlignmentTransition, Integer> indexProvider) {
		format(target, indexProvider.apply(solution.getTransitions().get(0)), indexProvider.apply(solution.getLastTransition()));
	}

	public String getAlignedA() {
		return alignedAStr;
	}

	public String getAlignedB() {
		return alignedBStr;
	}

	public String getAlignment() {
		return alignmentStr;
	}
}

