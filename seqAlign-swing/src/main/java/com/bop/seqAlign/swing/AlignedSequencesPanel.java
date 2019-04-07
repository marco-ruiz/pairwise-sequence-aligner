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
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;

import com.bop.common.swing.TextProfileFactory;
import com.bop.seqAlign.framework.AlignedSequences;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class AlignedSequencesPanel extends JScrollPane {
	
    private JTextPane seqDisplayCount, seqDisplayA, seqDisplayX, seqDisplayB;
    private boolean coloringEnabled = true;
	private List<SimpleAttributeSet> coloringProfiles;
	private TextProfileFactory textProfileFactory = new TextProfileFactory(175);

    public AlignedSequencesPanel() {
    	super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JPanel sequences = new JPanel(new GridLayout(4, 0));
        seqDisplayCount = createSequenceDisplay(sequences);
        seqDisplayA = createSequenceDisplay(sequences);
        seqDisplayX = createSequenceDisplay(sequences);
        seqDisplayB = createSequenceDisplay(sequences);
    	setViewportView(sequences);
    }
    
    private JTextPane createSequenceDisplay(JPanel container) {
    	JTextPane result = new JTextPane();
    	result.setEditable(false);
    	result.getCaret().deinstall(result);
        result.setFont(new Font("Courier", Font.PLAIN, 14));
        result.setForeground(Color.BLACK);
        container.add(result);
        return result;
    }

    public void displayAlignedSequences(AlignedSequences aligned) {
        seqDisplayCount.setText(getNumberingLabel(25, aligned.getAlignedA().length()));
		seqDisplayA.setText(aligned.getAlignedA());
        seqDisplayX.setText(aligned.getAlignment());
        seqDisplayB.setText(aligned.getAlignedB());

        coloringProfiles = aligned.getScoreContributionLevels().stream()
										.map(textProfileFactory::createTextProfile)
										.collect(Collectors.toList());
        applyColoring();
    }
    
	public void setColoringEnabled(boolean enabled) {
		coloringEnabled = enabled;
        applyColoring();
	}

	private void applyColoring() {
		IntStream.range(AlignedSequences.PREFIX_LENGTH, seqDisplayA.getText().length() - AlignedSequences.PREFIX_LENGTH)
        	.forEach(charIndex -> applyColoringToSymbol(charIndex, getColoringProfile(charIndex)));
	}

    private void applyColoringToSymbol(int charIndex, SimpleAttributeSet coloringProfile) {
		seqDisplayA.getStyledDocument().setCharacterAttributes(charIndex, 1, coloringProfile, true);
		seqDisplayX.getStyledDocument().setCharacterAttributes(charIndex, 1, coloringProfile, true);
		seqDisplayB.getStyledDocument().setCharacterAttributes(charIndex, 1, coloringProfile, true);
    }
    
    private SimpleAttributeSet getColoringProfile(int charIndex) {
    	return coloringEnabled ? 
    				coloringProfiles.get(charIndex - AlignedSequences.PREFIX_LENGTH) : 
    				textProfileFactory.getDefaultProfile();
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
