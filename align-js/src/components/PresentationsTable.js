import React, { useState, Fragment } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

import CarouselCaption, { useCarousel } from './CarouselCaption';
import PropertyPresentation from '../property-presentation';

const styles = theme => ({
    root: {
        display: 'flex',
    },
    row: {
    },
    selectedRow: {
        backgroundColor: "#eee",
    }
});

const PresentationsTable = ({ classes, presentations, listItems, showHeader, onSelect }) => {
    const [selectedRow, setSelectedRow] = useState(0);
    const selectItem = (selectedIndex) => {
        setSelectedRow(selectedIndex);
        onSelect(selectedIndex);
    }
    
    const header = showHeader &&
        <Fragment>
            {presentations.map((presentation, index) =>
                <TableCell key={index} className={classes.header} align="center">
                    {presentation.caption}
                </TableCell>
            )}
        </Fragment>

    return (
        <Table className={classes.table}>
            <TableHead>
                <TableRow>{header}</TableRow>
            </TableHead>
            <TableBody>
                {listItems.map((item, index) =>
                    <TableRow key={index}
                        className={selectedRow === index ? classes.selectedRow : classes.row}
                        hover
                        onClick={() => selectItem(index)}>
                        {presentations.map((presentation, cellIndex) =>
                            <TableCell key={cellIndex} align="right">{presentation.extractValue(item)}</TableCell>
                        )}
                    </TableRow>
                )}
            </TableBody>
        </Table>
    );
}

PresentationsTable.propTypes = {
    classes: PropTypes.object.isRequired,
    presentation: PropTypes.arrayOf(
        PropTypes.instanceOf(PropertyPresentation)
    ),
};

const StyledPresentationsTable = withStyles(styles)(PresentationsTable);

export default StyledPresentationsTable;

export const PresentationsColumn = (props) => {
    const { presentations, initialIndex } = props;
    const [ [firstPresentation], offsetter ] = useCarousel(presentations, initialIndex);
    const singlePresentationArray = Array.of(firstPresentation.clone(
        { caption: <CarouselCaption onMove={offsetter}>{firstPresentation.caption}</CarouselCaption> }
    ));

    return (
        <StyledPresentationsTable {...props} presentations={singlePresentationArray} />
    );
}

PresentationsColumn.propTypes = {
    presentation: PropTypes.arrayOf(
        PropTypes.instanceOf(PropertyPresentation)
    ),
};
