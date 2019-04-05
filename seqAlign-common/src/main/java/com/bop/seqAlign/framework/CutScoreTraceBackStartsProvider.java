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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import com.bop.common.math.TwoDimensionalCoordinates;

/**
 * @author Marco Ruiz
 */
public class CutScoreTraceBackStartsProvider {

    private int cutScore = 0;
    private AlignmentMatrix source;
    private BiPredicate<Integer, Integer> coordTester;

    public CutScoreTraceBackStartsProvider(int cutScore, AlignmentMatrix source) {
    	this(cutScore, source, (indexA, indexB) -> true);
    }
    
    public CutScoreTraceBackStartsProvider(int cutScore, AlignmentMatrix source, BiPredicate<Integer, Integer> coordTester) {
		this.cutScore = cutScore;
		this.source = source;
		this.coordTester = coordTester;
	}

    public int getCutScore() {
		return cutScore;
	}

	public List<Transition> getTraceBackStarts() {
		BiConsumer<TwoDimensionalCoordinates, List<Transition>> accumulator = (cutScore < 1) ? this::accumMax : this::accumByCutScore;
        int lenA = source.getSequenceA().length();
        int lenB = source.getSequenceB().length();

        List<Transition> result = new ArrayList<>();
		for (int indexA = 1; indexA < lenA; indexA++)
		    for (int indexB = 1; indexB < lenB; indexB++)
		    	if (coordTester.test(indexA, indexB))
		    		accumulator.accept(new TwoDimensionalCoordinates(indexA, indexB), result);
		
        Collections.sort(result, Transition.SCORE_COMPARATOR);
		return result;
	}

	private void accumByCutScore(TwoDimensionalCoordinates coords, List<Transition> result) {
		accumulateTransition(coords, cutScore, t -> result.add(t));
	}

	private void accumMax(TwoDimensionalCoordinates coords, List<Transition> result) {
		if (result.size() == 0) 
			result.add(new Transition());
		
		accumulateTransition(coords, result.get(0).getScore(), t -> result.set(0, t));
	}

	private void accumulateTransition(TwoDimensionalCoordinates coords, int cutScore, Consumer<Transition> accumulator) {
		Transition transition = new Transition(coords, source.getReferencedTransition(coords).getScore());
		if (transition.getScore() > cutScore)
			accumulator.accept(transition);
	}
}
