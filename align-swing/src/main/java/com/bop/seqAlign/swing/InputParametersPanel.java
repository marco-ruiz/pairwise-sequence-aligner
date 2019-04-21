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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.bop.common.swing.SectionGridPanel;
import com.bop.common.swing.SectionGridPanel.LabeledComponent;
import com.bop.common.swing.SectionPanel;
import com.bop.seqAlign.framework.AlignmentDescriptor;
import com.bop.seqAlign.framework.AlignmentType;
import com.bop.seqAlign.framework.AlignmentUtils;
import com.bop.seqAlign.framework.ScoringMatrix;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class InputParametersPanel extends JPanel {
	
	private TextArea  txtSeqA = new TextArea(">sp|P23541|VA2_BPT5 A2 protein - Bacteriophage T5.\nTNAKTAKVCQSFAWNEENTQKAVSMYQQLINENGLDFANSDGLKEIAKAVGAASPVSVRSKLTS" , 7, 20);
	private TextArea  txtSeqB = new TextArea(">sp|MISSING\nSTVSPVFVCQSFAKNAGMYGERVGAVGAASPVSCFHLALTKQAQNKTIKPAVTSQLAKIIRSEVSNPPA", 7, 20);
	
	private JComboBox<ScoringMatrix> matrix = new JComboBox<>(ScoringMatrix.values());
	private JComboBox<String> alignmentType = new JComboBox<>(AlignmentType.getNames());
	
	private JTextField txtGapOpen  = new JTextField("5");
	private JTextField txtGapExt   = new JTextField("1");
	private JTextField txtMinScore = new JTextField("20");
	private JTextField txtMaxSol   = new JTextField("15");

    public InputParametersPanel(Runnable alignmentRunner) {
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel sectionPanel = new SectionPanel("Sequences", new BorderLayout());
        JPanel sequencesPanel = new JPanel(new GridLayout(1, 2));
        sequencesPanel.add(createSequencePanel("Sequence A", txtSeqA));
        sequencesPanel.add(createSequencePanel("Sequence B", txtSeqB));
        
        sectionPanel.add(sequencesPanel, BorderLayout.NORTH);
        center.add(sectionPanel, BorderLayout.NORTH);

        sectionPanel = new SectionGridPanel("Scoring Parameters", 
										        new LabeledComponent("Scoring Matrix", matrix),
										        new LabeledComponent("Gap Open Penalty", txtGapOpen),
										        new LabeledComponent("Gap Extension Penalty", txtGapExt));
        center.add(sectionPanel, BorderLayout.CENTER);

        sectionPanel = new SectionGridPanel("Cut Solutions Parameters", 
										        new LabeledComponent("Minimum Score", txtMinScore),
										        new LabeledComponent("Maximum Number of Solutions", txtMaxSol));
        center.add(sectionPanel, BorderLayout.SOUTH);

        JPanel executionPanel = new JPanel(new FlowLayout());
        JButton alignButton = new JButton("Run Pairwise Alignment!");
        alignButton.addActionListener(e -> alignmentRunner.run());
        executionPanel.add(new JLabel("Alignment Type: "));
        executionPanel.add(alignmentType);
        executionPanel.add(alignButton);

        // Create the whole panel
        add(center, BorderLayout.NORTH);
        add(executionPanel, BorderLayout.SOUTH);
    }
    
	private JPanel createSequencePanel(String title, TextArea txtSequence) {
    	JButton sequenceRetriever = new JButton("Retrieve protein sequence");
    	sequenceRetriever.addActionListener(new SequenceProvider(txtSequence));
    	
        JPanel result = new JPanel(new BorderLayout());
        result.add(new JLabel(title, JLabel.CENTER), BorderLayout.NORTH);
        result.add(new JScrollPane(txtSequence), BorderLayout.CENTER);
        result.add(sequenceRetriever, BorderLayout.SOUTH);
        return result;
    }

	public AlignmentDescriptor getAlignmentDescriptor() throws IOException {
        return new AlignmentDescriptor.Builder(readSequence(txtSeqA), readSequence(txtSeqB))
												.withScoringMatrix((ScoringMatrix)matrix.getSelectedItem())
												.withMaxNumberOfSolutions(getInteger(txtMaxSol))
												.withFixGapPenalty(getInteger(txtGapOpen))
												.withVariableGapPenalty(getInteger(txtGapExt))
												.withMinScore(getInteger(txtMinScore))
												.withAlignmentType(AlignmentType.fromName((String)alignmentType.getSelectedItem()))
												.build();
	}
	
	private int getInteger(JTextField textField) {
		return Integer.parseInt(textField.getText());
	}
    
	private String readSequence(TextArea textArea) throws IOException {
		return AlignmentUtils.cleanSequence(textArea.getText()).toString();
	}
}
