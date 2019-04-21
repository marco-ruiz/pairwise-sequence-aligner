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
	
    public static int getFactored(int orig, double ratio) {
        return (int) Math.round(orig * ratio);
    }

	private final Color color;
	private final Point start;
	private final Point end;
	
	private Point scaledStart;
	private Point scaledEnd;
	
	public Segment(Point start, Point end) {
		this(start, end, null);
	}

	public Segment(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.color = (color == null) ? Color.BLACK : color;
		resetRatios();
	}

	public void resetRatios() {
		withRatios(1, 1);
	}
	
	public Segment withRatios(double xRatio, double yRatio) {
		scaledStart = new Point(getFactored(start.x, xRatio), getFactored(start.y, yRatio));
		scaledEnd = new Point(getFactored(end.x, xRatio), getFactored(end.y, yRatio));
		return this;
	}
	
	public Point getDifference() {
		return new Point(getEndX() - getStartX(), getEndY() - getStartY());
	}
	
	public Color getColor() {
		return color;
	}

	public int getStartX() {
		return scaledStart.x;
	}

	public int getStartY() {
		return scaledStart.y;
	}

	public int getEndX() {
		return scaledEnd.x;
	}

	public int getEndY() {
		return scaledEnd.y;
	}

	public Point getStart() {
		return scaledEnd;
	}

	public Point getEnd() {
		return scaledEnd;
	}
}
