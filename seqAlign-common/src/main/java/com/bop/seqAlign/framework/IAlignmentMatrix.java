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

package com.bop.seqAlign.framework;

import java.util.List;

/**
 * @author Marco Ruiz
 * @since JDK1.2
 */
public interface IAlignmentMatrix {

    AlignmentTransition getInitialTransitionForSequenceA(int index);

    AlignmentTransition getInitialTransitionForSequenceB(int index);

    boolean isTraceBackStopCondition(AlignmentTransition target);

    List<AlignmentTransition> getTraceBackStarts();
}
