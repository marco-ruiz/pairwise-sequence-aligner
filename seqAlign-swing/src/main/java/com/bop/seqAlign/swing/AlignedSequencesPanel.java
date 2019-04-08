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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.bop.common.swing.ColorByLevelFactory;
import com.bop.seqAlign.framework.AlignedSequences;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class AlignedSequencesPanel extends JScrollPane {
	
    private JTextPane seqDisplayCount, seqDisplayA, seqDisplayX, seqDisplayB;
    private boolean coloringEnabled = true;
	private List<SimpleAttributeSet> coloringProfiles;
	
	private ColorByLevelFactory colorByLevelFactory = new ColorByLevelFactory(175);
	private SimpleAttributeSet defaultColoringProfile = createTextProfile(0);

    public AlignedSequencesPanel() {
    	super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JPanel sequences = new JPanel(new GridLayout(4, 0));
    	setViewportView(sequences);
        seqDisplayCount = createSequenceDisplay(sequences);
        seqDisplayA = createSequenceDisplay(sequences);
        seqDisplayX = createSequenceDisplay(sequences);
        seqDisplayB = createSequenceDisplay(sequences);
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
        seqDisplayCount.setText(getNumberingLabel(25, aligned.getLength()));
		seqDisplayA.setText(aligned.getFormattedAlignedA());
        seqDisplayX.setText(aligned.getFormattedAlignment());
        seqDisplayB.setText(aligned.getFormattedAlignedB());

        coloringProfiles = aligned.getScoreContributionLevels().stream()
										.map(this::createTextProfile)
										.collect(Collectors.toList());
        applyColoring();
    }
    
	/**
	 * @param colorLevel Value within [-1, 1]
	 * @return
	 */
	public SimpleAttributeSet createTextProfile(float colorLevel) {
		SimpleAttributeSet result = new SimpleAttributeSet();
		if (Float.isNaN(colorLevel)) return result;
		StyleConstants.setBackground(result, colorByLevelFactory.createColor(colorLevel));
        return result;
	}
    
	public void setColoringEnabled(boolean enabled) {
		coloringEnabled = enabled;
        applyColoring();
	}

	private void applyColoring() {
		int affixLen = AlignedSequences.FORMATTER.getQuoteAffixTotalLength();
		IntStream.range(affixLen, seqDisplayA.getText().length() - affixLen)
        	.forEach(charIndex -> applyColoringToSymbol(charIndex, getColoringProfile(charIndex)));
	}

    private void applyColoringToSymbol(int charIndex, SimpleAttributeSet coloringProfile) {
		seqDisplayA.getStyledDocument().setCharacterAttributes(charIndex, 1, coloringProfile, true);
		seqDisplayX.getStyledDocument().setCharacterAttributes(charIndex, 1, coloringProfile, true);
		seqDisplayB.getStyledDocument().setCharacterAttributes(charIndex, 1, coloringProfile, true);
    }
    
    private SimpleAttributeSet getColoringProfile(int charIndex) {
    	return coloringEnabled ? 
    				coloringProfiles.get(charIndex - AlignedSequences.FORMATTER.getQuoteAffixTotalLength()) : 
    				defaultColoringProfile;
    }

    private static String getNumberingLabel(int interval, int max) {
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
