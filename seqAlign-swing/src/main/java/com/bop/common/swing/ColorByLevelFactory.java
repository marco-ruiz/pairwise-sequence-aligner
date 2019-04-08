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

package com.bop.common.swing;

import java.awt.Color;

/**
 * @author Marco Ruiz
 */
public class ColorByLevelFactory {
	
	private final float[] rgbPositive;
	private final float[] rgbNegative;
	private final Color defaultColor;

	public ColorByLevelFactory(int rbValue) {
		this(new Color(rbValue, 0, 0), new Color(0, 0, rbValue));
	}
	
	public ColorByLevelFactory(Color positiveColor, Color negativeColor) {
		rgbPositive = positiveColor.getRGBColorComponents(null);
		rgbNegative = negativeColor.getRGBColorComponents(null);
		defaultColor = createColor(0);
	}
	
	/**
	 * @param colorLevel Value within [-1, 1]
	 * @return
	 */
	public Color createColor(float colorLevel) {
		float[] rgb = (colorLevel > 0) ? rgbPositive : rgbNegative;
		return new Color(rgb[0], rgb[1], rgb[2], Math.abs(colorLevel * 0.5f));
	}

	public Color getDefaultColor() {
		return defaultColor;
	}
}
