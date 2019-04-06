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
import java.awt.Point;

/**
 * @author Marco Ruiz
 */
public class Segment {
	
    public static int getFactored(double orig, double ratio) {
        return new Double(orig * ratio + 0.5).intValue();
    }

	private final Color color;
	private final Point start;
	private final Point end;
	
	private double xRatio;
	private double yRatio;
	
	public Segment(Color color, Point start, Point end) {
		this.color = color;
		this.start = start;
		this.end = end;
		resetRatios();
	}

	public void resetRatios() {
		withRatios(1, 1);
	}
	
	public Segment withRatios(double xRatio, double yRatio) {
		this.xRatio = xRatio;
		this.yRatio = yRatio;
		return this;
	}
	
	public Color getColor() {
		return color;
	}

	public int getStartX() {
		return getFactored(start.x, xRatio);
	}

	public int getStartY() {
		return getFactored(start.y, yRatio);
	}

	public int getEndX() {
		return getFactored(end.x, xRatio);
	}

	public int getEndY() {
		return getFactored(end.y, yRatio);
	}
}