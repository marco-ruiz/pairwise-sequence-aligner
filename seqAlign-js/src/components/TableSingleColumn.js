import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';

import IconButton from '@material-ui/core/IconButton';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

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

const createFormattedValue = (field, record) => {
    const rawValue = record[field.name];
    return field.formatter ? field.formatter(rawValue) : rawValue;
}

class TableSingleColumn extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            displayedFieldIndex: this.props.displayedFieldIndex,
            selectedRow: 0,
        };
    }

    moveToDisplay = (fieldIndexOffset) => {
        const len = this.props.fields.length;
        let displayedFieldIndex = this.state.displayedFieldIndex + fieldIndexOffset;
        if (displayedFieldIndex < 0) displayedFieldIndex = len - 1;
        if (displayedFieldIndex > len - 1) displayedFieldIndex = 0;
        this.setState({ displayedFieldIndex });
    }

    selectItem = (selectedRow) => {
        this.setState({ selectedRow });
        this.props.onSelect(selectedRow);
    }

    render() {
        const { classes, fields, listItems, showHeader } = this.props;
        const displayField = fields[this.state.displayedFieldIndex];
        const header = showHeader &&
            <Fragment>
                <TableCell className={classes.header} align="center">
                    <IconButton onClick={() => this.moveToDisplay(-1)}>
                        <ChevronLeftIcon />
                    </IconButton>
                    {displayField.caption}
                    <IconButton onClick={() => this.moveToDisplay(1)}>
                        <ChevronRightIcon />
                    </IconButton>
                </TableCell>
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
                            <TableCell align="right">{createFormattedValue(displayField, item)}</TableCell>
                        </TableRow>
                    )}
                </TableBody>
            </Table>
        );
    }
}

TableSingleColumn.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(TableSingleColumn);
