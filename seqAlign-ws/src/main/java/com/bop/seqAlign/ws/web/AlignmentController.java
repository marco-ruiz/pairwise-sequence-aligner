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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bop.seqAlign.framework.AlignmentDescriptor;
import com.bop.seqAlign.framework.AlignmentType;
import com.bop.seqAlign.framework.ScoringMatrix;
import com.bop.seqAlign.ws.web.resource.AlignmentResource;
import com.bop.seqAlign.ws.web.resource.ScoringMatrixesResource;

/**
 * @author Marco Ruiz
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api", produces = MediaTypes.HAL_JSON_VALUE)
public class AlignmentController {

	private static final Logger logger = LoggerFactory.getLogger(AlignmentController.class);
	
	@Autowired
	private ScoringMatrixesResource scoringMatrixesResource;
	
	@GetMapping(value = "/matrixes")
	public ResponseEntity<ResourceSupport> getMatrixes() {
		logger.info("Received request to get list of scoring matrixes available");
		return ResponseEntity.ok(scoringMatrixesResource);
	}

	@PostMapping(value = "/align", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResourceSupport> createAlignment(@RequestBody AlignmentDescriptorDTO descriptorDTO) {
		return createResponse(descriptorDTO);
	}

	private ResponseEntity<ResourceSupport> createResponse(AlignmentDescriptor.Builder descriptorBuilder) {
		AlignmentDescriptor descriptor = descriptorBuilder.build();
		logger.info("Received request to perform a {} alignment", descriptor.getAlignmentType().getName());
		return ResponseEntity.ok(new AlignmentResource(descriptor.createAlignment()));
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
