import React, { useState, Fragment } from 'react';
import PropTypes from 'prop-types';

import CarouselCaption from './CarouselCaption';
import PresentationsTable from './PresentationsTable';
import PropertyPresentation from '../property-presentation';

const carouselDisplacer = (max, current, offset) => {
    const trueIndexOffset = offset % max;
    let result = current + trueIndexOffset;
    if (result < 0) result += max;
    if (result > max - 1) result -= max;
    return result;
}

const presentationTransformer = onMove => 
    (presentation, index) =>
        presentation.clone({ caption: <CarouselCaption key={index} onMove={onMove}>{presentation.caption}</CarouselCaption> });

export default class PresentationsColumn extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            presentations: props.presentations.map(presentationTransformer(this.moveToDisplay)),
            selectedIndex: props.selectedIndex,
        };
    }

    moveToDisplay = (fieldIndexOffset) => {
        this.setState({ 
            selectedIndex: carouselDisplacer(this.state.presentations.length, this.state.selectedIndex, fieldIndexOffset) 
        });
    }

    render() {
        const selectedPresentation = Array.of(this.state.presentations[this.state.selectedIndex]);
        return (
            <PresentationsTable {...this.props} presentations={selectedPresentation} />
        );
    }
}

PresentationsColumn.propTypes = {
    presentations: PropTypes.arrayOf(
        PropTypes.instanceOf(PropertyPresentation)
    ),
};

