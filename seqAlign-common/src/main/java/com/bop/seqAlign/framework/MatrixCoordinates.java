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

/**
 * @author Marco Ruiz
 */
public class MatrixCoordinates {
	
	protected final int indexA;
	protected final int indexB;
	
	public MatrixCoordinates(int indexA, int indexB) {
		this.indexA = indexA;
		this.indexB = indexB;
	}

	public int getIndex(boolean firstSequence) {
		return firstSequence ? getIndexA() : getIndexB();
	}

	public int getIndexA() {
		return indexA;
	}

	public int getIndexB() {
		return indexB;
	}
	
	public boolean hasSameCoordinates(MatrixCoordinates other) {
		return this.indexA == other.indexA && this.indexB == other.indexB;
	}
	
	public AlignmentTransition clone() {
		return new AlignmentTransition(indexA, indexB);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + indexA;
		result = prime * result + indexB;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatrixCoordinates other = (MatrixCoordinates) obj;
		if (indexA != other.indexA)
			return false;
		if (indexB != other.indexB)
			return false;
		return true;
	}

	public String toString() {
		return String.format("(%d, %d)", indexA, indexB);
	}
}
