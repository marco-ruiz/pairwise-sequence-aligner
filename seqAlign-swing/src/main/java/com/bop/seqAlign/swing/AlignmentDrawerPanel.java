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

package com.bop.seqAlign.swing;

import java.awt.Color;
import java.awt.Point;

import com.bop.common.swing.Segment;
import com.bop.common.swing.SegmentsDrawerPanel;
import com.bop.seqAlign.framework.AlignmentMatrix;
import com.bop.seqAlign.framework.AlignmentSolution;
import com.bop.seqAlign.framework.TransitionDelta;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class AlignmentDrawerPanel extends SegmentsDrawerPanel<TransitionDelta> {

	public static Segment createSegment(TransitionDelta delta) {
		Color color = (delta.getReferencedScoreDifference() > 0) ? Color.black : Color.white;
		Point start = new Point(delta.getPrevious().getCoords().getX(), delta.getPrevious().getCoords().getY());
		Point end = new Point(delta.getCurrent().getCoords().getX(), delta.getCurrent().getCoords().getY());
		return new Segment(start, end, color);
	}
	
	public AlignmentDrawerPanel() {
		super(AlignmentDrawerPanel::createSegment, Color.gray);
	}
	
	public void setAlignmentMatrix(AlignmentMatrix matrix) {
		if (matrix == null) return;
		setMaxSegmentableValues(matrix.getSequenceA().length(), matrix.getSequenceB().length());
	}

    public void drawSolution(AlignmentSolution solution) {
		paintSegmentables(solution.getTransitionDeltas());
    }
}
