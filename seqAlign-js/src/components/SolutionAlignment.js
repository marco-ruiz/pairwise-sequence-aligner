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

const createAffixedArray = (sourceArray, affixValue, affixSize) => {
    const affixValues = Array(affixSize).fill(affixValue);
    return affixValues.concat(sourceArray, affixValues)
}

const createRGBA = (hueLevel, level) => {
    const alpha = Math.abs(level * 0.5);
    const rgb = (level > 0) ? hueLevel + ", 0, 0" : "0, 0, " + hueLevel;
    return 'rgba(' + rgb + ', ' + alpha + ')';
}

const SolutionAlignment = ({ classes, colored, hueLevel, solution: { alignedSequences } }) => {
    const sequences = ['formattedAlignedA', 'formattedAlignment', 'formattedAlignedB'].map(key => alignedSequences[key]);
    const levels = createAffixedArray(alignedSequences.scoreContributionLevels, 0, alignedSequences.affixLength);
    const rgbas = levels.map(level => createRGBA(hueLevel, level));
    const contributionBars = 
        <TableRow className={classes.tableRow}>
            {levels.map((level, index) =>
                <TableCell key={index} className={classes.tableCell}>
                    <ColoredBar
                        colored={colored}
                        size={150}
                        minValue={-1}
                        maxValue={1}
                        fromValue={0}
                        toValue={level}
                        color={rgbas[index]} />
                </TableCell>
            )}
        </TableRow>

    return (
        <Table className={classes.table}>
            <TableBody>
            {sequences.map((seq, seqIndex) =>
                <TableRow key={seqIndex} className={classes.tableRow}>
                    {seq.split("").map((symbol, index) =>
                        <TableCell key={index} className={classes.tableCell} style={{ backgroundColor: colored && rgbas[index] }}>
                            {symbol === ' ' ? <span>&nbsp;</span> : symbol}
                        </TableCell>
                    )}
                </TableRow>
            )}
            {colored && contributionBars}
            </TableBody>
        </Table>
    );
}

SolutionAlignment.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SolutionAlignment);
