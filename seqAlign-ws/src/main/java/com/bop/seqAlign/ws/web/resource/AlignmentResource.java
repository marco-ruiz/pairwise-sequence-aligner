/*
 * Copyright 2002-2018 the original author or authors.
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

package com.bop.seqAlign.ws.web.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.ResourceSupport;

import com.bop.seqAlign.framework.AlignmentMatrix;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Marco Ruiz
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlignmentResource extends ResourceSupport {
	
	private AlignmentDescriptorResource descriptor;
	private List<AlignmentSolutionResource> solutions;
	
	public AlignmentResource(AlignmentMatrix matrix) {
		this.descriptor = new AlignmentDescriptorResource(matrix.getDescriptor());
		this.solutions = matrix.getSolutions().stream().map(AlignmentSolutionResource::new).collect(Collectors.toList());
	}

	public AlignmentDescriptorResource getDescriptor() {
		return descriptor;
	}

	public List<AlignmentSolutionResource> getSolutions() {
		return solutions;
	}
}
