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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Marco Ruiz
 */
public class AlignedSequences {
	
	public static final int PREFIX_LENGTH = 4;
	public static final int BRACKET_LENGTH = 1;
	
	public static final String BRACKET_NULL = " ";
	public static final String BRACKET_OPEN = ">";
	public static final String BRACKET_CLOSE = "<";
	
	private static void format(StringBuffer target, int startIndex, int endIndex) {
		String startChar = (startIndex < 0) ? BRACKET_NULL : BRACKET_OPEN;
		String endChar = (endIndex < 0) ? BRACKET_NULL : BRACKET_CLOSE;
		target.insert(0, getPreffixSuffixString(startIndex, true) + startChar);
		target.append(endChar + getPreffixSuffixString(endIndex, false));
	}
    
    private static String getPreffixSuffixString(int number, boolean preffix) {
        String numberStr = (number == -1) ? "" : Integer.toString(number + 1);
        String spaces = new String(new char[PREFIX_LENGTH - BRACKET_LENGTH - numberStr.length()]).replace("\0", " ");
        return (preffix) ? numberStr + spaces : spaces + numberStr;
    }

	private AlignmentSolution solution;
	private final StringBuffer alignedA = new StringBuffer();
	private final StringBuffer alignedB = new StringBuffer();
	private final StringBuffer alignment = new StringBuffer();
	private final List<Integer> scoreContributions = new ArrayList<>();
	private final List<Double> scoreContributionsLevels;
	
	private final String alignedAStr, alignedBStr, alignmentStr;
	
	public AlignedSequences(AlignmentSolution solution) {
		this(solution, true);
	}
	
	public AlignedSequences(AlignmentSolution solution, boolean format) {
		this.solution = solution;
		
		solution.getTransitionDeltas().stream().forEach(this::processSymbolForDelta);
		scoreContributionsLevels = computeScoreContributionLevels();
		
		if (format) format();
		alignedAStr = alignedA.toString();
		alignedBStr = alignedB.toString();
		alignmentStr = alignment.toString();
	}
	
	private void processSymbolForDelta(TransitionDelta delta) {
		alignedA.append(delta.getSymbolA());
		alignedB.append(delta.getSymbolB());
		alignment.append(delta.getSymbolAlignment());
		scoreContributions.add(delta.getReferencedScoreDifference());
	}
	
	public List<Double> getScoreContributionLevels() {
		return scoreContributionsLevels;
	}

	/**
	 * Creates a list of values between -1 and 1 corresponding to each symbol's score contribution level (relative
	 * to the maximum score contribution)
	 * 
	 * @return
	 */
	private List<Double> computeScoreContributionLevels() {
	    double max = scoreContributions.stream().mapToInt(Math::abs).max().orElse(1);
	    return scoreContributions.stream()
		    		.mapToDouble(score -> score / max)
		    		.mapToObj(Double::new)
		    		.collect(Collectors.toList());
	}
	
	private void format() {
		format(alignedA, Transition::getIndexA);
		format(alignedB, Transition::getIndexB);
		format(alignment, -1, -1);
	}
	
	private void format(StringBuffer target, Function<Transition, Integer> indexProvider) {
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

	public List<Integer> getScoreContributions() {
		return Collections.unmodifiableList(scoreContributions);
	}
}

