/*
 * Copyright 2002-2003 the original author or authors.
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

package com.bop.seqAlign.swing.results;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.bop.seqAlign.framework.AlignmentMatrix;
import com.bop.seqAlign.framework.AlignmentSolution;
import com.bop.seqAlign.framework.ScaledTransitionDelta;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class DotterPane extends JPanel {

    private int selectedSolutionNumber = 0;
    private AlignmentMatrix alignment = null;

	public void setAlignmentMatrix(AlignmentMatrix matrix) {
		alignment = matrix;
        repaint();
	}

    public void highlightSolution(int solNumber) {
        selectedSolutionNumber = solNumber;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //paint background
        if (alignment == null) return;

        double xRatio = 1.0 * getWidth()  / alignment.getSequenceA().length();
        double yRatio = 1.0 * getHeight() / alignment.getSequenceB().length();
        
		AlignmentSolution solution = alignment.getSolutions().get(selectedSolutionNumber);
       	paintEncapsulatingArea(g, new ScaledTransitionDelta(solution.getWholeDelta(), xRatio, yRatio));
		solution.getTransitionDeltas().forEach(delta -> drawTransitionLine(g, new ScaledTransitionDelta(delta, xRatio, yRatio)));
    }
    
	private void paintEncapsulatingArea(Graphics g, ScaledTransitionDelta delta) {
        g.setColor(Color.gray);
        g.fillRect(delta.getScaledPrevious().getX(), delta.getScaledPrevious().getY(), 
        			delta.getScaledDistance().getX(), delta.getScaledDistance().getY());
	}

	private void drawTransitionLine(Graphics g, ScaledTransitionDelta delta) {
        g.setColor((delta.getReferencedScoreDifference() > 0) ? Color.black : Color.white);
		g.drawLine(delta.getScaledPrevious().getX(), delta.getScaledPrevious().getY(), 
					delta.getScaledCurrent().getX(), delta.getScaledCurrent().getY());
	}
}
