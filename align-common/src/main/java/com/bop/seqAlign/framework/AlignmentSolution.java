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

/**
 * @author Marco Ruiz
 */
public class AlignmentSolution {
	
    public static final char GAP_SYMBOL = '-';
    public static final char POSITIVE_SYMBOL = '+';
    public static final char NEGATIVE_SYMBOL = ' ';

	AlignmentMatrix valuesMatrix;
	private List<Transition> transitions = new ArrayList<>();
	
	// Computed
	private TransitionDelta wholeDelta;
	private List<TransitionDelta> transitionDeltas = new ArrayList<>();

	private final AlignedSequences alignedSequences;
	private final int positives, identities;
	private final int positivesPercentage, identitiesPercentage;
	private int maxScoreContribution;
	
	public AlignmentSolution(AlignmentMatrix matrix, Transition start) {
		this.valuesMatrix = matrix;
        readTraceBack(start);
        
        // Deltas
        wholeDelta = new TransitionDelta(matrix, getTransition(0), getLastTransition());
		for (int index = 1; index < getTransitions().size(); index++)
			transitionDeltas.add(new TransitionDelta(matrix, getTransition(index - 1), getTransition(index)));
		
		maxScoreContribution = transitionDeltas.stream()
				.map(TransitionDelta::getScoreContribution)
				.mapToInt(Math::abs)
				.max().orElse(1);
		
		transitionDeltas.forEach(delta -> delta.setMaxScoreContribution(maxScoreContribution));

        // Stats
		alignedSequences = new AlignedSequences(this);
		positives = (int) transitionDeltas.stream().filter(TransitionDelta::isPositive).count();
		identities = (int) transitionDeltas.stream().filter(TransitionDelta::isIdentity).count();
		positivesPercentage = positives * 100 / alignedSequences.getLength();
		identitiesPercentage = identities * 100 / alignedSequences.getLength();
	}
	
    private void readTraceBack(Transition start) {
        transitions.add(0, start.clone());
        if (!valuesMatrix.isTraceBackStopCondition(start)) 
        	readTraceBack(valuesMatrix.getReferencedTransition(start));
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<TransitionDelta> getTransitionDeltas() {
		return Collections.unmodifiableList(transitionDeltas);
    }

    public double getMaxScoreContribution() {
	    return maxScoreContribution;
    }

	public TransitionDelta getWholeDelta() {
		return wholeDelta;
	}

	public Transition getLastTransition() {
		return getTransition(transitions.size() - 1);
	}

	public Transition getLastTransitionUnscored() {
		return getTransition(transitions.size() - 1).cloneUnscored();
	}

    public boolean isDuplicate(AlignmentSolution other) {
    	return getTransition(0).getCoords().hasSameCoordinates(other.getTransition(0).getCoords());
	}

    public boolean isCellSolution(Transition target) {
    	return transitions.stream().anyMatch(transition -> transition.getCoords().hasSameCoordinates(target.getCoords()));
    }
    
    public final int getScore() {
    	return valuesMatrix.getReferencedTransition(getLastTransition()).getScore();
    }

	// STATS
	
	public AlignedSequences getAlignedSequences() {
		return alignedSequences;
	}

	public int getAlignedStartIndex(SequenceDesignator designator) {
		return getSequenceIndexOfTransition(designator, 0);
	}

	public int getAlignedEndIndex(SequenceDesignator designator) {
		return getSequenceIndexOfTransition(designator, transitions.size() - 1);
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
        return valuesMatrix.getSequenceA().length() * valuesMatrix.getSequenceB().length() * Math.pow(2, -getScore());
	}
	
	// UTILS

	public Transition getTransition(int transitionIndex) {
		return transitions.get(transitionIndex);
	}

	private int getSequenceIndexOfTransition(SequenceDesignator designator, int transitionIndex) {
		return getTransition(transitionIndex).getIndex(designator);
	}
}
