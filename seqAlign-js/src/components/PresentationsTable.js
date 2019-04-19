import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

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

class PresentationsTable extends React.Component {

    state = {
        selectedRow: 0,
    };

    selectItem = (selectedRow) => {
        this.setState({ selectedRow });
        this.props.onSelect(selectedRow);
    }

    render() {
        const { classes, presentation, listItems, showHeader } = this.props;
        const header = showHeader &&
            <Fragment>
            {presentation.map((displayField, index) =>
                <TableCell key={index} className={classes.header} align="center">
                    {displayField.caption}
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
                            className={this.state.selectedRow === index ? classes.selectedRow : classes.row} 
                            hover 
                            onClick={() => this.selectItem(index)}>
                            {presentation.map((displayField, cellIndex) =>
                                <TableCell key={cellIndex} align="right">{displayField.extractValue(item)}</TableCell>
                            )}
                        </TableRow>
                    )}
                </TableBody>
            </Table>
        );
    }
}

PresentationsTable.propTypes = {
    classes: PropTypes.object.isRequired,
    presentation: PropTypes.arrayOf(
        PropTypes.instanceOf(PropertyPresentation)
    ),
};

export default withStyles(styles)(PresentationsTable);
