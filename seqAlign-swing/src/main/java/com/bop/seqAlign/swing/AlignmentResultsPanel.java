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

package com.bop.seqAlign.swing;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.bop.common.swing.SectionPanel;
import com.bop.seqAlign.framework.AlignmentMatrix;
import com.bop.seqAlign.framework.AlignmentSolution;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class AlignmentResultsPanel extends JPanel {

    private AlignmentMatrix alignment;
    private JComboBox<String> solutionChoice = new JComboBox<>();
    private JLabel identities = new JLabel();
    private JButton goBack = new JButton("Back");

    private AlignmentDrawerPanel drawerPanel = new AlignmentDrawerPanel();
	private AlignmentSequencesPanel sequencesPanel = new AlignmentSequencesPanel();

    public AlignmentResultsPanel(Runnable restarter) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        goBack.addActionListener(e -> restarter.run());
        solutionChoice.addActionListener(e -> displaySolution(solutionChoice.getSelectedIndex()));

        add(new SectionPanel("Alignment Plot", drawerPanel), BorderLayout.CENTER);
    	add(createStatsPanel(), BorderLayout.SOUTH);
    }

	private JPanel createStatsPanel() {
		JPanel result = new JPanel();
        result.setLayout(new BorderLayout());
        result.setBorder(BorderFactory.createEtchedBorder());
        result.add(createSummaryPanel(), BorderLayout.NORTH);
        result.add(sequencesPanel, BorderLayout.CENTER);
		return result;
	}

    private JPanel createSummaryPanel() {
        JPanel result = new JPanel(new BorderLayout());
        result.add(new JLabel("   Alignment Number: "), BorderLayout.WEST);
        result.add(solutionChoice, BorderLayout.CENTER);
        result.add(goBack, BorderLayout.EAST);
        result.add(identities, BorderLayout.SOUTH);
        return result;
    }

    public void setAlignmentMatrix(AlignmentMatrix matrix) {
    	if (matrix == null) return; 
        alignment = matrix;

        drawerPanel.setAlignmentMatrix(alignment);
        solutionChoice.removeAllItems();
        for (int count = 0; count < alignment.getSolutions().size(); count++)
            solutionChoice.addItem(Integer.toString((count)));
    }

    public void displaySolution(int solutionIndex) {
    	if (solutionIndex < 0 || solutionIndex > alignment.getSolutions().size() - 1) return;
    	AlignmentSolution solution = alignment.getSolutions().get(solutionIndex);
        
    	identities.setText(getStatsLabel(solution));
    	sequencesPanel.displayAlignedSequences(solution.getAlignedSequences());
    	drawerPanel.drawSolution(solution);
    }

	private String getStatsLabel(AlignmentSolution solution) {
		return String.format(
    		"   Score: %d.   Positives: %d (%d %%).   Identities: %d (%d %%).   Length: %d.   Sequence sizes (A, B): (%d , %d)", 
    		solution.getScore(), 
    		solution.getPositives(), solution.getPositivesPercentage(), 
    		solution.getIdentities(), solution.getIdentitiesPercentage(), 
    		solution.getAlignedSequences().getAlignedA().length(), 
    		alignment.getSequenceA().length(), alignment.getSequenceB().length());
	}
}
