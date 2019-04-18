import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import ColoredBar from './ColoredBar';

const styles = {
    table: {
        width: 0,
    },
    tableRow: {
        height: 0,
    },
    tableCell: {
        fontFamily: "monospace",
        fontSize: 15,

        textAlign: "center",
        width: 0,
        border: 0,
        padding: 0,
    },
}

class SolutionAlignment extends React.Component {

    createLevelsArray = alignedSequences => {
        const affixLevels = Array(alignedSequences.affixLength).fill(0);
        return affixLevels.concat(alignedSequences.scoreContributionLevels, affixLevels)
    }

    createRGBA = (level) => {
        const alpha = Math.abs(level * 0.5);
        const rgb = (level > 0) ? this.props.hueLevel + ", 0, 0" : "0, 0, " + this.props.hueLevel;
        return 'rgba(' + rgb + ', ' + alpha + ')';
    }

    render() {
        const { classes } = this.props;
        const { alignedSequences } = this.props.solution;
        const { formattedAlignedA, formattedAlignment, formattedAlignedB } = alignedSequences;
        const levels = this.createLevelsArray(alignedSequences);
        const rgbas = levels.map(level => this.createRGBA(level));

        return (
            <Table className={classes.table}>
                <TableBody>
                    <TableRow className={classes.tableRow}>
                    {levels.map((level, index) =>
                        <TableCell key={index} className={classes.tableCell}>
                            <ColoredBar
                                size={150}
                                minValue={-1}
                                maxValue={1}
                                fromValue={0}
                                toValue={level}
                                color={rgbas[index]} />
                        </TableCell>
                    )}
                    </TableRow>
                {[formattedAlignedA, formattedAlignment, formattedAlignedB].map((seq, seqIndex) =>
                    <TableRow key={seqIndex} className={classes.tableRow}>
                        {seq.split("").map((symbol, index) =>
                            <TableCell key={index} className={classes.tableCell} style={{ backgroundColor: rgbas[index] }}>
                                {symbol}
                            </TableCell>
                        )}
                    </TableRow>
                )}
                </TableBody>
            </Table>
        );
    };
}

SolutionAlignment.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SolutionAlignment);
