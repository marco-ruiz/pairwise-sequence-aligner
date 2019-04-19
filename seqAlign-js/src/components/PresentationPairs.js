import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import PropertyPresentation from '../property-presentation';

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

class PresentationPairs extends React.Component {

    render() {
        const { classes, presentation, record } = this.props;

        return (
            <Table>
                <TableBody>
                {presentation.map((field, index) => 
                    <TableRow key={index} className={classes.tableRow}>
                        <TableCell className={classes.tableCell} align="right">
                            {field.caption + ": "}
                        </TableCell>
                        <TableCell className={classes.tableCell} width="100%" align="left">
                            {field.extractValue(record)}
                        </TableCell>
                    </TableRow>
                )}
                </TableBody>
            </Table>
        );
    }
}

PresentationPairs.propTypes = {
    classes: PropTypes.object.isRequired,
    presentation: PropTypes.arrayOf(
        PropTypes.instanceOf(PropertyPresentation)
    ),
};

export default withStyles(styles)(PresentationPairs);
