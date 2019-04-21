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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Ruiz
 */
public class MultiDimensionalArray<VAL_T> {
	
    private Map<CellCoordinates, VAL_T> values = new HashMap<>();

	public VAL_T getValue(int... coords) {
        return values.get(new CellCoordinates(coords));
    }

    public void setValue(VAL_T value, int... coords) {
    	values.put(new CellCoordinates(coords), value);
    }
    
    private static class CellCoordinates {
    	private int[] coords;
    	
    	private CellCoordinates(int... coords) {
    		this.coords = Arrays.copyOf(coords, coords.length);
    	}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(coords);
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
			CellCoordinates other = (CellCoordinates) obj;
			if (!Arrays.equals(coords, other.coords))
				return false;
			return true;
		}
    }
}
