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

import com.bop.common.math.TwoDimensionalCoordinates;

/**
 * @author Marco Ruiz
 */
public class Transition {
	
	public static final Comparator<Transition> SCORE_COMPARATOR = Comparator.comparing(Transition::getScore);

	private TwoDimensionalCoordinates coordinates; 
	private int score;

	public Transition(int parentIndexA, int parentIndexB) {
		this(new TwoDimensionalCoordinates(parentIndexA, parentIndexB));
	}

	public Transition(int parentIndexA, int parentIndexB, int score) {
		this(new TwoDimensionalCoordinates(parentIndexA, parentIndexB), score);
	}

	public Transition() {
		this(new TwoDimensionalCoordinates(0, 0), 0);
	}

	public Transition(TwoDimensionalCoordinates coordinates) {
		this(coordinates, 0);
	}

	public Transition(TwoDimensionalCoordinates coordinates, int score) {
		this.coordinates = coordinates;
		this.score = score;
	}

	public int getIndex(SequenceDesignator designator) {
		switch (designator) {
			case SEQUENCE_A: return getIndexA();
			case SEQUENCE_B: return getIndexB();
		}
		return -1;
	}

	public int getIndexA() {
		return coordinates.getX();
	}

	public int getIndexB() {
		return coordinates.getY();
	}
	
	public TwoDimensionalCoordinates getCoords() {
		return coordinates;
	}

	public int getScore() {
		return score;
	}

	public Transition clone() {
		return new Transition(coordinates, score);
	}
	
	public Transition cloneUnscored() {
		return new Transition(coordinates, 0);
	}

	public String toString() {
		return String.format("(%d, %d, %d)", getIndexA(), getIndexB(), score);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinates == null) ? 0 : coordinates.hashCode());
		result = prime * result + score;
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
		Transition other = (Transition) obj;
		if (coordinates == null) {
			if (other.coordinates != null)
				return false;
		} else if (!coordinates.equals(other.coordinates))
			return false;
		if (score != other.score)
			return false;
		return true;
	}
}
