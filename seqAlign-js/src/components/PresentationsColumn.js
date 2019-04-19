import React, { Fragment } from 'react';
import PropTypes from 'prop-types';

import IconButton from '@material-ui/core/IconButton';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import PresentationsTable from './PresentationsTable';

import PropertyPresentation from '../property-presentation';

const columnCaption = (index, caption, moveToDisplay) =>
    <Fragment key={index}>
        <IconButton onClick={() => moveToDisplay(-1)}>
            <ChevronLeftIcon />
        </IconButton>
        {caption}
        <IconButton onClick={() => moveToDisplay(1)}>
            <ChevronRightIcon />
        </IconButton>
    </Fragment>

export default class PresentationsColumn extends React.Component {

    constructor(props) {
        super(props);

        const presentationTransformer = (presentationObj, index) => 
            presentationObj.clone({ caption: columnCaption(index, presentationObj.caption, this.moveToDisplay) });

        this.state = {
            presentation: props.presentation.map(presentationTransformer),
            displayedFieldIndex: props.displayedFieldIndex,
        };
    }

    moveToDisplay = (fieldIndexOffset) => {
        const len = this.props.presentation.length;
        let displayedFieldIndex = this.state.displayedFieldIndex + fieldIndexOffset;
        if (displayedFieldIndex < 0) displayedFieldIndex = len - 1;
        if (displayedFieldIndex > len - 1) displayedFieldIndex = 0;
        this.setState({ displayedFieldIndex });
    }

    render() {
        const displayedPresentation = Array.of(this.state.presentation[this.state.displayedFieldIndex]);
        return (
            <PresentationsTable {...this.props} presentation={displayedPresentation} />
        );
    }
}

PresentationsColumn.propTypes = {
    presentation: PropTypes.arrayOf(
        PropTypes.instanceOf(PropertyPresentation)
    ),
};

