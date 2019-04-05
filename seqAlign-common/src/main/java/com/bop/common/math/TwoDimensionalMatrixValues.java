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
import java.util.Comparator;
import java.util.function.BiFunction;

/**
 * @author Marco Ruiz
 */
public class TwoDimensionalMatrixValues<VAL_T> {
	
	private final int dimX;
	private final int dimY;
    private final Object[][] values;

    public TwoDimensionalMatrixValues(VAL_T[][] arrayValues) {
    	values = arrayValues.clone();
    	dimX = values.length;
    	dimY = Arrays.stream(values).map(ele -> ele.length).max(Comparator.naturalOrder()).orElse(0);
    }
    
    public TwoDimensionalMatrixValues(int dimensionX, int dimensionY) {
    	dimX = dimensionX;
    	dimY = dimensionY;
    	values = new Object[dimX][dimY];
    }

	public VAL_T getValue(TwoDimensionalCoordinates coords) {
		return getValue(coords.getX(), coords.getY());
	}

	@SuppressWarnings("unchecked")
	public VAL_T getValue(int x, int y) {
    	checkDimensions(x, y);
        return (VAL_T) values[x][y];
    }

    public void setValue(int x, int y, VAL_T value) {
    	checkDimensions(x, y);
    	values[x][y] = value;
    }
    
    public void setValues(BiFunction<Integer, Integer, VAL_T> valueProvider) {
        for (int indexX = 0; indexX < dimX; indexX++)
            for (int indexY = 0; indexY < dimY; indexY++)
                setValue(indexX, indexY, valueProvider.apply(indexX, indexY));
    }

    public int getDimensionX() {
    	return dimX;
    }
    
    public int getDimensionY() {
    	return dimY;
    }
    
    private void checkDimensions(int x, int y) {
    	if (x < 0 || x >= dimX || y < 0 || y >= dimY)
    		throw new IndexOutOfBoundsException();
    }
}
