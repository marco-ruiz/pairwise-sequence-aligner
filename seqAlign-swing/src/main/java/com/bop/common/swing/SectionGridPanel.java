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

package com.bop.common.swing;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class SectionGridPanel extends SectionPanel {

    public SectionGridPanel(String title, LabeledComponent... components) {
    	this(title, Arrays.asList(components));
    }
    
    public SectionGridPanel(String title, List<LabeledComponent> components) {
    	super(title, new GridLayout(components.size(), 2));
    	components.forEach(comp -> addLabeledComponent(comp));
    }
    
	private void addLabeledComponent(LabeledComponent comp) {
    	add(new JLabel(comp.label + ": "));
    	add(comp.component);
	}

	public static class LabeledComponent {
		private String label;
		private Component component;

		public LabeledComponent(String label, Component component) {
			this.label = label;
			this.component = component;
		}
	}
}
