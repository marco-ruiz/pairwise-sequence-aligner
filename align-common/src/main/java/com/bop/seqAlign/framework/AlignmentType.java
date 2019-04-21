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

package com.bop.seqAlign.framework;

import java.util.Arrays;
import java.util.function.Function;

import com.bop.seqAlign.framework.algorithm.GlobalAlignmentMatrix;
import com.bop.seqAlign.framework.algorithm.LocalAlignmentMatrix;
import com.bop.seqAlign.framework.algorithm.OverlapGlobalAlignmentMatrix;
import com.bop.seqAlign.framework.algorithm.RepeatedLocalAlignmentMatrix;

/**
 * @author Marco Ruiz
 */
public enum AlignmentType {
	
	LOCAL("Local", LocalAlignmentMatrix::new), 
	REPEATED_LOCAL("Repeated Local", RepeatedLocalAlignmentMatrix::new), 
	GLOBAL("Global", GlobalAlignmentMatrix::new), 
	OVERLAP_GLOBAL("Overlap Global", OverlapGlobalAlignmentMatrix::new);

	public static final String[] getNames() {
    	return Arrays.stream(values()).map(AlignmentType::getName).toArray(String[]::new);
	}
	
    public static final AlignmentType fromName(String name) {
    	return Arrays.stream(values()).filter(type -> type.getName().equals(name)).findFirst().orElse(null);
    }
	
	private String name;
	private Function<AlignmentDescriptor, AlignmentMatrix> alignmentFactory;
	
	AlignmentType(String name, Function<AlignmentDescriptor, AlignmentMatrix> alignmentFactory) {
		this.name = name;
		this.alignmentFactory = alignmentFactory;
	}

	public String getName() {
		return name;
	}

	public Function<AlignmentDescriptor, AlignmentMatrix> getAlignmentFactory() {
		return alignmentFactory;
	}
	
	public AlignmentMatrix createAlignment(AlignmentDescriptor descriptor) {
		AlignmentMatrix result = alignmentFactory.apply(descriptor);
		result.computeAlignments();
		return result;
	}
}
