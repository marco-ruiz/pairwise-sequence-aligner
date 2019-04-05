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
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.bop.seqAlign.framework.AlignmentMatrix;
import com.bop.seqAlign.swing.results.DotterPane;
import com.bop.seqAlign.swing.results.SequenceAlignmentPane;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class SequenceAligner extends JPanel {

	private static final ExecutorService executor = Executors.newFixedThreadPool(1);
	
    private JFrame appFrame;
    private StartUpPanel startup = new StartUpPanel(this::runAlignment);

    private DotterPane display;
    private SequenceAlignmentPane resultsPane;
    private JPanel ultimateResultPane;

    public SequenceAligner() {
        buildFrame();
        buildGUI();
        restart();
    }

    private void buildFrame() {
        appFrame = new JFrame("Pairwise Sequence Alignment Tool");
        appFrame.setSize(700, 500);
        appFrame.setVisible(true);
        appFrame.setContentPane(this);
        appFrame.validate();
        appFrame.repaint();
        appFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        appFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });
    }

    private void buildGUI() {
        ultimateResultPane = new JPanel();
        String tableTitle = "Alignment Plot";
        setLayout(new BorderLayout());

        // Create the table panel
        JPanel tablePane = new JPanel(new BorderLayout());
        tablePane.setBorder(new TitledBorder(tableTitle));
        display = new DotterPane();
        tablePane.add(display, BorderLayout.CENTER);
        resultsPane = new SequenceAlignmentPane(display::highlightSolution, this::restart);

        // Create the whole panel
        ultimateResultPane.setLayout(new BorderLayout());
        ultimateResultPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        ultimateResultPane.add(tablePane, BorderLayout.CENTER);
        ultimateResultPane.add(resultsPane, BorderLayout.SOUTH);
    }

    public void restart(){
    	switchUserInterface(true);
        validate();
        repaint();
    }
    
    public void switchUserInterface(boolean definition) {
    	remove(getUserInterface(!definition));
    	add(getUserInterface(definition));
        appFrame.validate();
        appFrame.repaint();
    }

	private Component getUserInterface(boolean definition) {
		return definition ? startup : ultimateResultPane;
	}

    public void runAlignment() {
    	executor.submit(() -> runAlignmentSync());
    }

    public void runAlignmentSync() {
    	updateAlignment(null);
        switchUserInterface(false);
        resultsPane.rebuild();
		try {
			updateAlignment(startup.getAlignmentDescriptor().createAlignment());
		} catch (IOException e) {}
    }

	private void updateAlignment(AlignmentMatrix alignment) {
		display.setAlignmentMatrix(alignment);
		resultsPane.setAlignmentMatrix(alignment);
	}

    public static void main(String[] args) {
        new SequenceAligner();
    }
}
