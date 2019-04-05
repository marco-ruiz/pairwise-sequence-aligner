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
import com.bop.seqAlign.framework.AlignmentTransition;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class DotterPane extends JPanel {

    private int selectedSolutionNumber = 0;
    private AlignmentMatrix alignment = null;
    private double xRatio, yRatio;

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

        xRatio = 1.0 * getWidth()  / alignment.getSequenceA().length();
        yRatio = 1.0 * getHeight() / alignment.getSequenceB().length();
       	paintSolution(g, getSelectedSolution());
    }
    
    private void paintSolution(Graphics g, AlignmentSolution results) {
       	paintEncapsulatingSquare(g, results);
        for (int count = 1; count < results.getTransitions().size(); count++) 
            drawTransitionLine(g, results, count);
    }

	private void drawTransitionLine(Graphics g, AlignmentSolution solution, int transitionIndex) {
		AlignmentTransition start = solution.getTransition(transitionIndex - 1);
		AlignmentTransition end = solution.getTransition(transitionIndex);
        Color color = (solution.getReferencedScoreIncrement(transitionIndex) > 0) ? Color.black : Color.white;
        
		g.setColor(color);
		g.drawLine(getCoord(start.getIndexA(), xRatio), 
					getCoord(start.getIndexB(), yRatio),
					getCoord(end.getIndexA(), xRatio), 
					getCoord(end.getIndexB(), yRatio));
	}

    private void paintEncapsulatingSquare(Graphics g, AlignmentSolution solution) {
        AlignmentTransition org = solution.getTransition(0);
        AlignmentTransition end = solution.getLastTransition();
        
        g.setColor(Color.gray);
        g.fillRect(getCoord(org.getIndexA(), xRatio), 
        			getCoord(org.getIndexB(), yRatio),
					getCoord(end.getIndexA(), xRatio) - getCoord(org.getIndexA(), xRatio), 
					getCoord(end.getIndexB(), yRatio) - getCoord(org.getIndexB(), yRatio));
    }

    private int getCoord(int orig, double ratio) {
        return new Double(orig * ratio + 0.5).intValue();
    }
    
    private AlignmentSolution getSelectedSolution() {
    	return alignment.getSolutions().get(selectedSolutionNumber);
    }
}
