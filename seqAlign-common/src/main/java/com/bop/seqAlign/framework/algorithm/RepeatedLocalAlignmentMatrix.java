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
import com.bop.seqAlign.framework.AlignmentTransition;

/**
 * @author Marco Ruiz
 */
public class RepeatedLocalAlignmentMatrix extends LocalAlignmentMatrix {

	public RepeatedLocalAlignmentMatrix(AlignmentDescriptor descriptor) {
		super(descriptor);
    }

    public AlignmentTransition getInitialTransitionForSequenceA(int indexA) {
    	return IntStream.range(0, getSequenceB().length())
    		.mapToObj(indexB -> createInitialTransitionCandidate(indexA - 1, indexB))
    		.max(AlignmentTransition.SCORE_COMPARATOR)
    		.get();
    }

	private AlignmentTransition createInitialTransitionCandidate(int indexA, int indexB) {
		return new AlignmentTransition(indexA, indexB, getValue(indexA, indexB).getScore() - traceBackStartsProvider.getCutScore());
	}
    
    public AlignmentTransition getInitialTransitionForSequenceB(int index) {
    	return new AlignmentTransition();
    }

    public boolean isTraceBackStopCondition(int indexA, int indexB) {
        AlignmentTransition parent = getValue(indexA, indexB);
        return ((parent.getIndexB() == 0) || (getValue(parent.getIndexA(), parent.getIndexB()).getIndexB() == 0));
    }

    protected List<AlignmentTransition> createCandidateTransitions(int indexA, int indexB) {
    	List<AlignmentTransition> result = super.createCandidateTransitions(indexA, indexB);
    	result.add(new AlignmentTransition(indexA, 0, getValue(indexA, 0).getScore()));
    	return result;
    }
}
