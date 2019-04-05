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

import java.util.ArrayList;
import java.util.List;

import com.bop.seqAlign.framework.AlignmentDescriptor;
import com.bop.seqAlign.framework.AlignmentMatrix;
import com.bop.seqAlign.framework.Transition;

/**
 * @author Marco Ruiz
 * @since JDK1.2
 */
public class GlobalAlignmentMatrix extends AlignmentMatrix {

	public GlobalAlignmentMatrix(AlignmentDescriptor descriptor) {
		super(descriptor);
	}

	public Transition getInitialTransitionForSequenceA(int index) {
        return new Transition(index - 1, 0, -index * descriptor.getFixGapPenalty());
    }

	public Transition getInitialTransitionForSequenceB(int index) {
		return new Transition(0, index - 1, -index * descriptor.getFixGapPenalty());
	}

    public List<Transition> getTraceBackStarts() {
        List<Transition> result = new ArrayList<>();
        result.add(new Transition(getSequenceA().length() - 1, getSequenceB().length() - 1));
		return result;
    }

	public boolean isTraceBackStopCondition(Transition target) {
        return target.getCoords().isOrigin();
	}
}
