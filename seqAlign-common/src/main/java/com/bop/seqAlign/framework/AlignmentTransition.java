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

import java.util.Comparator;

/**
 * @author Marco Ruiz
 */
public class AlignmentTransition extends MatrixCoordinates {
	
	public static final Comparator<AlignmentTransition> SCORE_COMPARATOR = Comparator.comparing(AlignmentTransition::getScore);

	private int score;
	
	public AlignmentTransition() {
		this(0, 0, 0);
	}

	public AlignmentTransition(int parentIndexA, int parentIndexB) {
		this(parentIndexA, parentIndexB, 0);
	}

	public AlignmentTransition(int parentIndexA, int parentIndexB, int score) {
		super(parentIndexA, parentIndexB);
		this.score = score;
	}

	public int getScore() {
		return score;
	}
	
	public AlignmentTransition clone() {
		return new AlignmentTransition(indexA, indexB, score);
	}
	
	public AlignmentTransition cloneUnscored() {
		return new AlignmentTransition(indexA, indexB, 0);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + score;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlignmentTransition other = (AlignmentTransition) obj;
		if (score != other.score)
			return false;
		return true;
	}

	public String toString() {
//		return "" + score;
//		return String.format("(%d, %d)", indexA, indexB, score);
		return String.format("(%d, %d, %d)", indexA, indexB, score);
	}
}
