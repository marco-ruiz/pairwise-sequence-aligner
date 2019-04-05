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
import java.util.List;

/**
 * @author Marco Ruiz
 */
public class AlignmentSolution {
	
    public static final char GAP_SYMBOL = '-';

	private AlignmentMatrix source;
	private List<AlignmentTransition> transitions = new ArrayList<>();

	private final AlignedSequences alignedSequences;
	private final int positives, identities;
	private final int positivesPercentage, identitiesPercentage;
	
	public AlignmentSolution(AlignmentMatrix source, AlignmentTransition start) {
		this.source = source;
        readTraceBack(start);
        
        // Stats
		alignedSequences = new AlignedSequences(this);
		positives = AlignmentUtils.parseIdentities(alignedSequences.getAlignment(), false);
		identities = AlignmentUtils.parseIdentities(alignedSequences.getAlignment(), true);
		positivesPercentage = positives * 100 / alignedSequences.getAlignment().length();
		identitiesPercentage = identities * 100 / alignedSequences.getAlignment().length();
	}
	
    private void readTraceBack(AlignmentTransition start) {
        transitions.add(0, start.clone());
        if (!source.isTraceBackStopCondition(start)) 
        	readTraceBack(getReferencedTransition(start));
    }

    public List<AlignmentTransition> getTransitions() {
        return transitions;
    }

	public AlignmentTransition getLastTransition() {
		return getTransition(transitions.size() - 1);
	}

	public AlignmentTransition getLastTransitionUnscored() {
		return getTransition(transitions.size() - 1).cloneUnscored();
	}

    public boolean isDuplicate(AlignmentSolution other) {
    	return getTransition(0).hasSameCoordinates(other.getTransition(0));
	}

    public boolean isCellSolution(AlignmentTransition target) {
    	return transitions.stream().anyMatch(transition -> transition.hasSameCoordinates(target));
    }
    
    public final int getScore() {
    	return getReferencedScore(getLastTransition());
    }

	public int getReferencedScoreIncrement(int transitionIndex) {
		return getReferencedScore(transitionIndex) - 
				((transitionIndex == 0) ? 0 : getReferencedScore(transitionIndex - 1));
	}

	public int getReferencedScore(int transitionIndex) {
		return getReferencedScore(getTransition(transitionIndex));
	}

	public char getSymbolAt(boolean firstSequence, int transitionIndex) {
		return isSameAsPrevious(firstSequence, transitionIndex) ? 
			source.getSymbol(firstSequence, getSequenceIndexOfTransition(firstSequence, transitionIndex)) : GAP_SYMBOL;
	}
	
	public boolean isSameAsPrevious(boolean firstSequence, int transitionIndex) {
		return getSequenceIndexOfTransition(firstSequence, transitionIndex) 
				== getSequenceIndexOfTransition(firstSequence, transitionIndex - 1) + 1;
	}
	
	public int getSequenceIndexOfLastTransition(boolean firstSequence) {
		return getSequenceIndexOfTransition(firstSequence, transitions.size() - 1);
	}
	
	public int getSequenceIndexOfTransition(boolean firstSequence, int transitionIndex) {
		return getTransition(transitionIndex).getIndex(firstSequence);
	}

	// UTILS
	
	private int getReferencedScore(AlignmentTransition transition) {
		return getReferencedTransition(transition).getScore();
	}
	
	private AlignmentTransition getReferencedTransition(AlignmentTransition transition) {
		return source.getValue(transition.getIndexA(), transition.getIndexB());
	}

	public AlignmentTransition getTransition(int transitionIndex) {
		return transitions.get(transitionIndex);
	}

	// STATS
	
	public AlignedSequences getAlignedSequences() {
		return alignedSequences;
	}

	public int getAlignedStartIndex(boolean firstSequence) {
		return getSequenceIndexOfTransition(firstSequence, 0);
	}

	public int getAlignedEndIndex(boolean firstSequence) {
		return getSequenceIndexOfLastTransition(firstSequence);
	}

	public int getPositives() {
		return positives;
	}

	public int getIdentities() {
		return identities;
	}

	public int getPositivesPercentage() {
		return positivesPercentage;
	}

	public int getIdentitiesPercentage() {
		return identitiesPercentage;
	}
	
	public double getEValue() {
        return source.getSequenceA().length() * source.getSequenceB().length() * Math.pow(2, -getScore());
	}
}
