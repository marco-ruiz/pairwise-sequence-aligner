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

import com.bop.common.utils.StringQuoter;

/**
 * @author Marco Ruiz
 */
public class AlignedSequences {
	
    public static final StringQuoter FORMATTER = new StringQuoter(4, '>', '<', ' ');
    
	private final int length;
	private String alignedA = "";
	private String alignedB = "";
	private String alignment = "";
	private final String formattedAlignedA, formattedAlignedB, formattedAlignment;

	private final List<Integer> scoreContributions = new ArrayList<>();
	private final List<Float> scoreContributionLevels = new ArrayList<>();
	
	public AlignedSequences(AlignmentSolution solution) {
		solution.getTransitionDeltas().stream().forEach(this::processSymbolForDelta);
		length = alignedA.length();

		Transition firstTransition = solution.getTransitions().get(0);
		Transition lastTransition = solution.getLastTransition();
		
		formattedAlignedA = FORMATTER.format(alignedA, firstTransition.getIndexA(), lastTransition.getIndexA());
		formattedAlignedB = FORMATTER.format(alignedB, firstTransition.getIndexB(), lastTransition.getIndexB());
		formattedAlignment = FORMATTER.format(alignment, -1, -1);
	}
	
	private void processSymbolForDelta(TransitionDelta delta) {
		alignedA += delta.getSymbolA();
		alignedB += delta.getSymbolB();
		alignment += delta.getSymbolAlignment();
		scoreContributions.add(delta.getScoreContribution());
		scoreContributionLevels.add(delta.getScoreContributionLevel());
	}
	
	public int getAffixLength() {
		return FORMATTER.getQuoteAffixTotalLength();
	}

	public int getLength() {
		return length;
	}
	
	public String getAlignedA() {
		return alignedA;
	}

	public String getAlignedB() {
		return alignedB;
	}

	public String getAlignment() {
		return alignment;
	}

	public String getFormattedAlignedA() {
		return formattedAlignedA;
	}

	public String getFormattedAlignedB() {
		return formattedAlignedB;
	}

	public String getFormattedAlignment() {
		return formattedAlignment;
	}

	public List<Integer> getScoreContributions() {
		return Collections.unmodifiableList(scoreContributions);
	}
	
	public List<Float> getScoreContributionLevels() {
		return Collections.unmodifiableList(scoreContributionLevels);
	}
}

