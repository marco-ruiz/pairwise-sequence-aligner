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

import com.bop.seqAlign.framework.AlignmentDescriptor;
import com.bop.seqAlign.framework.AlignmentMatrix;
import com.bop.seqAlign.framework.Transition;
import com.bop.seqAlign.framework.CutScoreTraceBackStartsProvider;

/**
 * @author Marco Ruiz
 */
public class LocalAlignmentMatrix extends AlignmentMatrix {

    protected final CutScoreTraceBackStartsProvider traceBackStartsProvider;

	public LocalAlignmentMatrix(AlignmentDescriptor descriptor) {
		super(descriptor);
        traceBackStartsProvider = new CutScoreTraceBackStartsProvider(descriptor.getMinScore(), this);
    }

    public Transition getInitialTransitionForSequenceA(int index) {
        return new Transition(index, 0, 0);
    }

	public Transition getInitialTransitionForSequenceB(int index) {
		return new Transition(0, index, 0);
	}

    public List<Transition> getTraceBackStarts() {
    	return traceBackStartsProvider.getTraceBackStarts();
	}

	public boolean isTraceBackStopCondition(Transition target) {
		return (getReferencedTransition(target).getScore() == 0);
	}
	
    protected List<Transition> createCandidateTransitions(int indexA, int indexB) {
    	List<Transition> result = super.createCandidateTransitions(indexA, indexB);
    	result.add(new Transition(indexA, indexB, 0));
    	return result;
    }
}
