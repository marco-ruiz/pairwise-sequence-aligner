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

import java.util.Objects;

import com.bop.seqAlign.framework.ScoringMatrix.PairWiseScoringMatrix;

/**
 * @author Marco Ruiz
 */
public class AlignmentDescriptor {
	
	private final String sequenceA, sequenceB;
	private final ScoringMatrix scoringMatrix;
	private final AlignmentType alignmentType;

    private final int maxNumberOfSolutions, fixGapPenalty, varGapPenalty;
    private final int minScore;
    
	private final PairWiseScoringMatrix pairwiseScoreMatrix;

    private AlignmentDescriptor(String sequenceA, String sequenceB, ScoringMatrix scoreMatrix, 
			AlignmentType alignmentType, int maxSolutions, int fixPenalty, int varPenalty, int minScore) {
    	
		this.sequenceA = sequenceA;
		this.sequenceB = sequenceB;
		this.scoringMatrix = scoreMatrix;
		this.alignmentType = alignmentType;
        this.maxNumberOfSolutions = maxSolutions;
        this.fixGapPenalty = fixPenalty;
		this.varGapPenalty = varPenalty;
		this.minScore = minScore;
		
		this.pairwiseScoreMatrix = scoringMatrix.createPairWiseScoringMatrix(sequenceA, sequenceB);
    }

	public String getSequenceA() {
		return sequenceA;
	}

	public String getSequenceB() {
		return sequenceB;
	}

	public ScoringMatrix getScoringMatrix() {
		return scoringMatrix;
	}

	public AlignmentType getAlignmentType() {
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

	public int getGapPenalty(boolean varPenalty) {
		return varPenalty ? varGapPenalty : fixGapPenalty;
	}

	public PairWiseScoringMatrix getPairwiseScoreMatrix() {
		return pairwiseScoreMatrix;
	}

	public AlignmentMatrix createAlignment() {
		return alignmentType.createAlignment(this);
	}
	
	public static class Builder {
		protected String sequenceA, sequenceB;
		
		private ScoringMatrix scoringMatrix = ScoringMatrix.BLOSUM50;
		private int maxNumberOfSolutions = -1;
	    private int fixGapPenalty = 12;
	    private int varGapPenalty = 2;
	    private int cutScore = -1;
		private AlignmentType alignmentType = AlignmentType.LOCAL;

		public Builder(String sequenceA, String sequenceB) {
			Objects.requireNonNull(sequenceA);
			Objects.requireNonNull(sequenceB);

			this.sequenceA = sequenceA;
			this.sequenceB = sequenceB;
		}
		
	    public Builder withScoringMatrix(ScoringMatrix matrix) {
	    	if (matrix != null) 
	    		scoringMatrix = matrix;
	    	return this;
	    }
		
	    public Builder withMaxNumberOfSolutions(int quantity) {
	    	maxNumberOfSolutions = quantity;
	    	return this;
	    }
		
	    public Builder withFixGapPenalty(int penalty) {
	    	fixGapPenalty = penalty;
	    	return this;
	    }
		
	    public Builder withVariableGapPenalty(int penalty) {
	    	varGapPenalty = penalty;
	    	return this;
	    }
		
	    public Builder withMinScore(int score) {
	    	cutScore = score;
	    	return this;
	    }
	    
	    public Builder withAlignmentType(AlignmentType type) {
	    	if (type != null) 
	    		alignmentType = type;
	    	return this;
	    }
	    
	    public AlignmentDescriptor build() {
	    	return new AlignmentDescriptor(sequenceA, sequenceB, scoringMatrix, alignmentType, 
	    									maxNumberOfSolutions, fixGapPenalty, varGapPenalty, cutScore);
	    }
	}
}

