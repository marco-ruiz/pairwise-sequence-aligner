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

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marco Ruiz
 */
public class SequenceProvider implements ActionListener {
	
	private static final ExecutorService executor = Executors.newFixedThreadPool(1);
	
	private Runnable task;

	public SequenceProvider(TextArea txtField) {
		task = () -> txtField.setText(getSequence(txtField.getText()));
	}
	
    public void actionPerformed(ActionEvent e) {
		executor.submit(task);
    }

    private String getSequence(String sequenceId) {
        return getURLContents("http://www.expasy.org/cgi-bin/get-sprot-fasta?" + sequenceId, sequenceId);
    }

    private String getURLContents(String urlString, String seqID) {
        String line, result = "";

        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            while ((line = bufferedReader.readLine()) != null) result += line + "\n";
            return (result.charAt(0) == '<') ? seqID : result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return seqID;
    }
}
