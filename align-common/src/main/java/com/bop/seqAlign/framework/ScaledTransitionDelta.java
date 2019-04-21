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

import com.bop.common.math.TwoDimensionalCoordinates;

/**
 * @author Marco Ruiz
 */
public class ScaledTransitionDelta extends TransitionDelta {

	private final TwoDimensionalCoordinates scaledPrevious, scaledCurrent, scaledDistance;
	
	public ScaledTransitionDelta(TransitionDelta target, double xRatio, double yRatio) {
		super(target);
		
		this.scaledPrevious = target.getPrevious().getCoords().cloneScaled(xRatio, yRatio);
		this.scaledCurrent = target.getCurrent().getCoords().cloneScaled(xRatio, yRatio);
		this.scaledDistance = scaledCurrent.substract(scaledPrevious);
	}

	public TwoDimensionalCoordinates getScaledPrevious() {
		return scaledPrevious;
	}

	public TwoDimensionalCoordinates getScaledCurrent() {
		return scaledCurrent;
	}

	public TwoDimensionalCoordinates getScaledDistance() {
		return scaledDistance;
	}
}
