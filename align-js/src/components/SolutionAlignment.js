import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import ColoredBar from './ColoredBar';
import RgbaColor from '../rgba-color';

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

const SolutionAlignment = ({ classes, colored, positiveColor, negativeColor, solution: { alignedSequences } }) => {
    const sequences = ['formattedAlignedA', 'formattedAlignment', 'formattedAlignedB'].map(key => alignedSequences[key]);
    const levels = createAffixedArray(alignedSequences.scoreContributionLevels, 0, alignedSequences.affixLength);
    const rgbas = levels.map(level => (level > 0 ? positiveColor : negativeColor).factorAlphaBy(level, 0.5));
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
    positiveColor: PropTypes.instanceOf(RgbaColor),
    negativeColor: PropTypes.instanceOf(RgbaColor),
};

export default withStyles(styles)(SolutionAlignment);
