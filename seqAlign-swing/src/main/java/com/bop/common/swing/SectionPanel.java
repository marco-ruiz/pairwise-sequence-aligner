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

import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Marco Ruiz
 */
@SuppressWarnings("serial")
public class SectionPanel extends JPanel {

    public SectionPanel(String title, LayoutManager layout) {
        setLayout(layout);

        TitledBorder borderTitle = new TitledBorder(title);
        EmptyBorder borderEmpty = new EmptyBorder(0, 5, 0, 5);
        setBorder(new CompoundBorder(borderTitle, borderEmpty));
    }
}