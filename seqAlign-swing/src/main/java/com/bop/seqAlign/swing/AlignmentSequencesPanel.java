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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.bop.seqAlign.framework.AlignedSequences;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class AlignmentSequencesPanel extends JScrollPane {
	
    private JLabel seqDisplayCount, seqDisplayA, seqDisplayX, seqDisplayB;

    public AlignmentSequencesPanel() {
    	super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JPanel sequences = new JPanel(new GridLayout(4, 0));
        seqDisplayCount = createSequenceDisplay(sequences);
        seqDisplayA = createSequenceDisplay(sequences);
        seqDisplayX = createSequenceDisplay(sequences);
        seqDisplayB = createSequenceDisplay(sequences);
    	setViewportView(sequences);
    }
    
    private JLabel createSequenceDisplay(JPanel container) {
        JLabel result = new JLabel(" ");
        result.setFont(new Font("Courier", Font.PLAIN, 12));
        result.setForeground(Color.black);
        container.add(result);
        return result;
    }

    public void displayAlignedSequences(AlignedSequences aligned) {
        int alignmentLength = aligned.getAlignedA().length();
		seqDisplayA.setText(aligned.getAlignedA());
        seqDisplayX.setText(aligned.getAlignment());
        seqDisplayB.setText(aligned.getAlignedB());
        seqDisplayCount.setText(getNumberingLabel(25, alignmentLength));
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
