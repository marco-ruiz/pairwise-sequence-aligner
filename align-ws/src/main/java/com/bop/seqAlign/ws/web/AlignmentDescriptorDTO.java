/*
 * Copyright 2017 by Agena Bioscience, Inc. All rights reserved.
 */

package com.bop.seqAlign.ws.web;

import java.util.function.Supplier;

import com.bop.seqAlign.framework.AlignmentDescriptor;
import com.bop.seqAlign.framework.AlignmentType;
import com.bop.seqAlign.framework.ScoringMatrix;

public class AlignmentDescriptorDTO extends AlignmentDescriptor.Builder {

	private static <T> T getSafely(Supplier<T> supplier) {
		try {
			return supplier.get();
		} catch(Exception e) {}
		return null;
	}
	
	
	public AlignmentDescriptorDTO() {
		super("", "");
	}

	public void setSequenceA(String sequenceA) {
		this.sequenceA = sequenceA;
	}

	public void setSequenceB(String sequenceB) {
		this.sequenceB = sequenceB;
	}

	public void setScoringMatrixName(String scoringMatrixName) {
		withScoringMatrix(getSafely(() -> ScoringMatrix.valueOf(scoringMatrixName)));
	}

	public void setMaxNumberOfSolutions(int maxNumberOfSolutions) {
		withMaxNumberOfSolutions(maxNumberOfSolutions);
	}

	public void setFixGapPenalty(int penalty) {
		withFixGapPenalty(penalty);
	}

	public void setVarGapPenalty(int penalty) {
		withVariableGapPenalty(penalty);
	}

	public void setMinScore(int score) {
		withMinScore(score);
	}

	public void setAlignmentType(String alignmentType) {
		withAlignmentType(getSafely(() -> AlignmentType.valueOf(alignmentType)));
	}
}
