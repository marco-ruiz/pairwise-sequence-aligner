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

import com.bop.seqAlign.framework.AlignedSequences;
import com.bop.seqAlign.framework.AlignmentSolution;
import com.bop.seqAlign.framework.SequenceDesignator;

/**
 * @author Marco Ruiz
 */
public class AlignmentSolutionResource {
	
	private AlignmentSolution solution;
	
	private final int startIndexSequenceA;
	private final int endIndexSequenceA;
	private final int startIndexSequenceB;
	private final int endIndexSequenceB;
	
	public AlignmentSolutionResource(AlignmentSolution source) {
		solution = source;
		startIndexSequenceA	= solution.getAlignedStartIndex(SequenceDesignator.SEQUENCE_A);
		endIndexSequenceA	= solution.getAlignedEndIndex(SequenceDesignator.SEQUENCE_A);
		startIndexSequenceB	= solution.getAlignedStartIndex(SequenceDesignator.SEQUENCE_B);
		endIndexSequenceB	= solution.getAlignedEndIndex(SequenceDesignator.SEQUENCE_B);
	}

	public AlignedSequences getAlignedSequences() {
		return solution.getAlignedSequences();
	}

	public int getStartIndexSequenceA() {
		return startIndexSequenceA;
	}

	public int getEndIndexSequenceA() {
		return endIndexSequenceA;
	}

	public int getStartIndexSequenceB() {
		return startIndexSequenceB;
	}

	public int getEndIndexSequenceB() {
		return endIndexSequenceB;
	}

	public int getScore() {
		return solution.getScore();
	}

	public int getPositives() {
		return solution.getPositives();
	}

	public int getIdentities() {
		return solution.getIdentities();
	}
	
	public double getPositivesPercentage() {
		return solution.getPositivesPercentage();
	}
	
	public double getIdentitiesPercentage() {
		return solution.getIdentitiesPercentage();
	}
}
