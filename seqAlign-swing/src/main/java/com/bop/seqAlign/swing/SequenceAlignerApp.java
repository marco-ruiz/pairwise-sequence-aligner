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
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class SequenceAlignerApp extends JFrame {

	private static final ExecutorService executor = Executors.newFixedThreadPool(1);
	
    private JPanel contentPane = new JPanel(new BorderLayout());
    private InputParametersPanel startup = new InputParametersPanel(this::runAlignmentSync);
    private AlignmentResultsPanel resultsPane = new AlignmentResultsPanel(this::restart);

    public SequenceAlignerApp() {
        super("Pairwise Sequence Alignment Tool");
        setSize(700, 500);
        setVisible(true);
        setContentPane(contentPane);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });
        restart();
    }

    public void restart(){
    	switchUserInterface(true);
    }
    
    public void switchUserInterface(boolean definition) {
    	contentPane.removeAll();
    	contentPane.add(getUserInterface(definition));
        validate();
        repaint();
    }

	private Component getUserInterface(boolean definition) {
		return definition ? startup : resultsPane;
	}

    public void runAlignment() {
    	executor.submit(() -> runAlignmentSync());
    }

    public void runAlignmentSync() {
        switchUserInterface(false);
		try {
			resultsPane.setAlignmentMatrix(startup.getAlignmentDescriptor().createAlignment());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
        new SequenceAlignerApp();
    }
}
