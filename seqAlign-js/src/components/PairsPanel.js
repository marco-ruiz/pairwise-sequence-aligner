import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

const styles = theme => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        height: 140,
        width: 100,
    },
    control: {
        padding: theme.spacing.unit * 2,
    },
    tableRow: {
        height: 24,
    },
    tableCell: {
        whiteSpace: "nowrap",
        paddingRight: 10,
        border: 0,
    },
});

class PairsPanel extends React.Component {

    render() {
        const { classes, fields, record } = this.props;

        return (
            <Table>
                <TableBody>
                {fields.map((field, index) => 
                    <TableRow className={classes.tableRow} key={index}>
                        <TableCell className={classes.tableCell} align="right">
                            {field.caption + ": "}
                        </TableCell>
                        <TableCell className={classes.tableCell} width="100%" align="left">
                            {record[field.name]}
                        </TableCell>
                    </TableRow>
                )}
                </TableBody>
            </Table>
        );
    }
}

PairsPanel.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(PairsPanel);
