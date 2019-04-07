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

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * @author Marco Ruiz
 */
public class TextProfileFactory {
	
	private final float[] rgbPositive;
	private final float[] rgbNegative;
	private final SimpleAttributeSet defaultProfile;

	public TextProfileFactory(int rbValue) {
		this(new Color(rbValue, 0, 0), new Color(0, 0, rbValue));
	}
	
	public TextProfileFactory(Color positiveColor, Color negativeColor) {
		rgbPositive = positiveColor.getRGBColorComponents(null);
		rgbNegative = negativeColor.getRGBColorComponents(null);
		defaultProfile = createTextProfile(0);
	}
	
	/**
	 * @param colorLevel Value within [-1, 1]
	 * @return
	 */
	public SimpleAttributeSet createTextProfile(float colorLevel) {
		SimpleAttributeSet result = new SimpleAttributeSet();
		float[] rgb = (colorLevel > 0) ? rgbPositive : rgbNegative;
		StyleConstants.setBackground(result, new Color(rgb[0], rgb[1], rgb[2], Math.abs(colorLevel * 0.5f)));
        return result;
	}

	public SimpleAttributeSet getDefaultProfile() {
		return defaultProfile;
	}
}
