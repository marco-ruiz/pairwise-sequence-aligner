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

package com.bop.seqAlign.ws.web.resource;

import com.bop.seqAlign.framework.AlignmentDescriptor;

/**
 * @author Marco Ruiz
 */
public class AlignmentDescriptorResource {

	private final String sequenceA;
	private final String sequenceB;
	private final String scoringMatrixName;
	private final String alignmentType;

    private final int maxNumberOfSolutions;
    private final int fixGapPenalty, varGapPenalty;
    private final int minScore;

	public AlignmentDescriptorResource(AlignmentDescriptor descriptor) {
		this.sequenceA = descriptor.getPairwiseScoreMatrix().getSequenceA();
		this.sequenceB = descriptor.getPairwiseScoreMatrix().getSequenceB();
		this.scoringMatrixName = descriptor.getPairwiseScoreMatrix().getScoringMatrixName();
		this.alignmentType = descriptor.getAlignmentType().name();

		this.maxNumberOfSolutions = descriptor.getMaxNumberOfSolutions();
		this.fixGapPenalty = descriptor.getFixGapPenalty();
		this.varGapPenalty = descriptor.getVarGapPenalty();
		this.minScore = descriptor.getMinScore();
	}
	
	public String getSequenceA() {
		return sequenceA;
	}

	public String getSequenceB() {
		return sequenceB;
	}

	public String getScoringMatrixName() {
		return scoringMatrixName;
	}

	public String getAlignmentType() {
		return alignmentType;
	}

	public int getMaxNumberOfSolutions() {
		return maxNumberOfSolutions;
	}

	public int getFixGapPenalty() {
		return fixGapPenalty;
	}

	public int getVarGapPenalty() {
		return varGapPenalty;
	}

	public int getMinScore() {
		return minScore;
	}
}
