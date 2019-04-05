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

package com.bop.seqAlign.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bop.common.math.TwoDimensionalCoordinates;

/**
 * @author Marco Ruiz
 */
public abstract class AlignmentMatrix extends SequenceMatrix<Transition> implements IAlignmentMatrix {

    protected AlignmentDescriptor descriptor;
    protected List<AlignmentSolution> solutions;

    public AlignmentMatrix(AlignmentDescriptor descriptor) {
    	super(" " + descriptor.getPairwiseScoreMatrix().getSequenceA(), " " + descriptor.getPairwiseScoreMatrix().getSequenceB());
    	this.descriptor = descriptor;
    }

	public Transition getReferencedTransition(Transition transition) {
		return getReferencedTransition(transition.getCoords());
	}

	public Transition getReferencedTransition(TwoDimensionalCoordinates coords) {
		return getValue(coords);
	}

	public char getReferencedSymbol(SequenceDesignator designator, Transition transition) {
		return getSymbol(designator, transition.getIndex(designator));
	}
	
    public AlignmentDescriptor getDescriptor() {
    	return descriptor;
    }
    
	public void computeAlignments() {
		setValues(this::computeInnerAlignmentTransition);
        solutions = createSolutions();
	}

	private Transition computeInnerAlignmentTransition(int indexA, int indexB) {
		if (indexA == 0 && indexB == 0) return new Transition();
		if (indexA == 0) return getInitialTransitionForSequenceB(indexB);
		if (indexB == 0) return getInitialTransitionForSequenceA(indexA);
		
		return Collections.max(createCandidateTransitions(indexA, indexB), Transition.SCORE_COMPARATOR);
	}

    protected List<Transition> createCandidateTransitions(int indexA, int indexB) {
		List<Transition> result = new ArrayList<>();
		result.add(createCandidateTransition(indexA, indexB - 1, true));
		result.add(createCandidateTransition(indexA - 1, indexB, true));
		result.add(createCandidateTransition(indexA - 1, indexB - 1, false));
		return result;
	}
    
    private Transition createCandidateTransition(int indexA, int indexB, boolean gapScore) {
    	TwoDimensionalCoordinates coords = new TwoDimensionalCoordinates(indexA, indexB);
		int candidateScore = gapScore ? -getGapPenalty(coords) : descriptor.getPairwiseScoreMatrix().getValue(coords);
		return new Transition(coords, getValue(coords).getScore() + candidateScore);
    }

	private int getGapPenalty(TwoDimensionalCoordinates coords) {
		Transition parent = coords.isInPositiveSpace() ? getValue(coords) : new Transition();
        boolean isParentAGap = parent.getCoords().equals(coords);
		return descriptor.getGapPenalty(isParentAGap);
	}
    
    //============
    // SOLUTIONS
    //============
    public List<AlignmentSolution> createSolutions() {
        Map<Transition, List<AlignmentSolution>> solutionsByFirstTransition = getTraceBackStarts().stream()
					.map(start -> new AlignmentSolution(this, start))
//					.collect(Collectors.groupingBy(sol -> sol.getTransitions().get(0).cloneUnscored()));
        			.collect(Collectors.groupingBy(sol -> sol.getTransitions().get(0)));
        
		return solutionsByFirstTransition
		        	.values().stream()
	    			.map(solList -> Collections.max(solList, Comparator.comparing(AlignmentSolution::getScore)))
	    			.sorted(Comparator.comparing(AlignmentSolution::getScore).reversed())
	    			.limit(descriptor.getMaxNumberOfSolutions())
	    			.collect(Collectors.toList());
    }

	public List<AlignmentSolution> getSolutions() {
		return solutions;
	}
}
