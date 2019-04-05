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

package com.bop.seqAlign.framework.algorithm;

import java.util.List;
import java.util.stream.IntStream;

import com.bop.seqAlign.framework.AlignmentDescriptor;
import com.bop.seqAlign.framework.Transition;

/**
 * @author Marco Ruiz
 */
public class RepeatedLocalAlignmentMatrix extends LocalAlignmentMatrix {

	public RepeatedLocalAlignmentMatrix(AlignmentDescriptor descriptor) {
		super(descriptor);
    }

    public Transition getInitialTransitionForSequenceA(int indexA) {
    	return IntStream.range(0, getSequenceB().length())
    		.mapToObj(indexB -> createInitialTransitionCandidate(indexA - 1, indexB))
    		.max(Transition.SCORE_COMPARATOR)
    		.get();
    }

	private Transition createInitialTransitionCandidate(int indexA, int indexB) {
		return new Transition(indexA, indexB, getValue(indexA, indexB).getScore() - traceBackStartsProvider.getCutScore());
	}
    
    public Transition getInitialTransitionForSequenceB(int index) {
    	return new Transition();
    }

    public boolean isTraceBackStopCondition(int indexA, int indexB) {
        Transition parent = getValue(indexA, indexB);
        return ((parent.getIndexB() == 0) || (getValue(parent.getIndexA(), parent.getIndexB()).getIndexB() == 0));
    }

    protected List<Transition> createCandidateTransitions(int indexA, int indexB) {
    	List<Transition> result = super.createCandidateTransitions(indexA, indexB);
    	result.add(new Transition(indexA, 0, getValue(indexA, 0).getScore()));
    	return result;
    }
}
