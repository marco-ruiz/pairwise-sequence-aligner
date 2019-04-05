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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.bop.seqAlign.framework.AlignedSequences;
import com.bop.seqAlign.framework.AlignmentMatrix;
import com.bop.seqAlign.framework.AlignmentSolution;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class SequenceAlignmentPane extends JPanel {

    private AlignmentMatrix alignment;
    private JLabel seqDisplayCount, seqDisplayA, seqDisplayX, seqDisplayB;
    private JComboBox<String> solutionChoice = new JComboBox<>();
    private JLabel identities = new JLabel();
    private JButton goBack = new JButton("Back");
	private Consumer<Integer> dotterRefresher;

    public SequenceAlignmentPane(Consumer<Integer> dotterRefresher, Runnable restarter) {
        this.dotterRefresher = dotterRefresher;
        goBack.addActionListener(e -> restarter.run());
        solutionChoice.addActionListener(e -> displaySolution(solutionChoice.getSelectedIndex()));
        rebuild();
    }

    public void rebuild() {
        this.removeAll();

        identities = new JLabel();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEtchedBorder());
        add(createSummaryPanel(), BorderLayout.NORTH);
        add(createSequencesPanel(), BorderLayout.CENTER);
    }

    public void setAlignmentMatrix(AlignmentMatrix matrix) {
    	if (matrix == null) return; 
        alignment = matrix;

        solutionChoice.removeAllItems();
        for (int count = 0; count < alignment.getSolutions().size(); count++)
            solutionChoice.addItem(Integer.toString((count)));
    }

    private JPanel createSummaryPanel() {
        JPanel result = new JPanel(new BorderLayout());
        result.add(new JLabel("   Alignment Number: "), BorderLayout.WEST);
        result.add(solutionChoice, BorderLayout.CENTER);
        result.add(goBack, BorderLayout.EAST);
        result.add(identities, BorderLayout.SOUTH);
        return result;
    }

    private JScrollPane createSequencesPanel() {
        JPanel sequences = new JPanel(new GridLayout(4, 0));
        seqDisplayCount = createSequenceDisplay(sequences);
        seqDisplayA = createSequenceDisplay(sequences);
        seqDisplayX = createSequenceDisplay(sequences);
        seqDisplayB = createSequenceDisplay(sequences);
        return new JScrollPane(sequences, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    }

    private JLabel createSequenceDisplay(JPanel container) {
        JLabel result = new JLabel(" ");
        result.setFont(new Font("Courier", Font.PLAIN, 12));
        result.setForeground(Color.black);
        container.add(result);
        return result;
    }

    public void displaySolution(int solutionIndex) {
    	if (solutionIndex < 0 || solutionIndex > alignment.getSolutions().size() - 1) return;
    	AlignmentSolution solution = alignment.getSolutions().get(solutionIndex);
        AlignedSequences aligned = solution.getAlignedSequences();
        int alignmentLength = aligned.getAlignedA().length();
		seqDisplayA.setText(aligned.getAlignedA());
        seqDisplayX.setText(aligned.getAlignment());
        seqDisplayB.setText(aligned.getAlignedB());
        seqDisplayCount.setText(getNumberingLabel(25, alignmentLength));

        String stats = String.format(
    		"   Score: %d.   Positives: %d (%d %%).   Identities: %d (%d %%).   Length: %d.   Sequence sizes (A, B): (%d , %d)", 
    		solution.getScore(), 
    		solution.getPositives(), solution.getPositivesPercentage(), 
    		solution.getIdentities(), solution.getIdentitiesPercentage(), 
    		alignmentLength, 
    		alignment.getSequenceA().length(), alignment.getSequenceB().length());

        identities.setText(stats);
        dotterRefresher.accept(solutionIndex);
    }

    private String getNumberingLabel(int interval, int max) {
        StringBuffer text = new StringBuffer(max);
        String number;
        for (int count = interval; count < max; count += interval) {
            number = Integer.toString(count);
            for (int spaces = 0; spaces < interval - number.length(); spaces++) text.append(' ');
            text.append(number);
        }
        return new String(text);
    }
}
