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

package com.bop.seqAlign.ws.web;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bop.seqAlign.framework.AlignmentDescriptor;
import com.bop.seqAlign.framework.AlignmentType;
import com.bop.seqAlign.framework.ScoringMatrix;
import com.bop.seqAlign.ws.web.resource.AlignmentResource;

/**
 * @author Marco Ruiz
 */
@RestController
@RequestMapping(value = "/api/align", produces = MediaTypes.HAL_JSON_VALUE)
public class AlignmentSolutionController {	//extends BaseController {
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResourceSupport> create(@RequestBody AlignmentDescriptorDTO thingDTO) {
		return createResponse(thingDTO);
	}

	private ResponseEntity<ResourceSupport> createResponse(AlignmentDescriptor.Builder descriptorBuilder) {
		AlignmentResource resource = new AlignmentResource(descriptorBuilder.build().createAlignment());
		return ResponseEntity.ok(resource);
	}

	@SuppressWarnings("unused")
	private AlignmentDescriptor.Builder createTestAlignmentDescriptorBuilder() {
		String sequenceA = "TNAKTAKVCQSFAWNEENTQKAVSMYQQLINENGLDFANSDGLKEIAKAVGAASPVSVRSKLTS"; 
		String sequenceB = "STVSPVFVCQSFAKNAGMYGERVGAVGAASPVSCFHLALTKQAQNKTIKPAVTSQLAKIIRSEVSNPPA"; 
		return new AlignmentDescriptor.Builder(sequenceA, sequenceB)
												.withScoringMatrix(ScoringMatrix.BLOSUM62)
												.withMaxNumberOfSolutions(50)
												.withFixGapPenalty(12)
												.withVariableGapPenalty(2)
												.withMinScore(10)
												.withAlignmentType(AlignmentType.LOCAL);
	}
}
