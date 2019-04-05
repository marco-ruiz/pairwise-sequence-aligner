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

package com.bop.common.math;

/**
 * @author Marco Ruiz
 */
public class TwoDimensionalCoordinates {
	
    public static int getFactored(int orig, double ratio) {
        return new Double(orig * ratio + 0.5).intValue();
    }

	protected final int x;
	protected final int y;
	
	public TwoDimensionalCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getCoordinate(boolean firstCoordinate) {
		return firstCoordinate ? getX() : getY();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean isOrigin() {
		return (x == 0) && (y == 0);
	}

	public boolean isInPositiveSpace() {
		return (x > 0) || (y > 0);
	}

	public TwoDimensionalCoordinates substract(TwoDimensionalCoordinates other) {
		return new TwoDimensionalCoordinates(x - other.x, y - other.y);
	}
	
	public TwoDimensionalCoordinates cloneScaled(double xRatio, double yRatio) {
		return new TwoDimensionalCoordinates(getFactored(x, xRatio), getFactored(y, yRatio));
	}
	
	public boolean hasSameCoordinates(TwoDimensionalCoordinates other) {
		return this.x == other.x && this.y == other.y;
	}

	public String toString() {
		return String.format("(%d, %d)", x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		TwoDimensionalCoordinates other = (TwoDimensionalCoordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
