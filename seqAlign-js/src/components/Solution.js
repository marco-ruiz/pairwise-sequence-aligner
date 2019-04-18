import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

import PairsPanel from './PairsPanel';
import SolutionAlignment from './SolutionAlignment';
import ColoredBar from './ColoredBar';

const styles = theme => ({
    root: {
        width: '100%',
    },
    heading: {
        fontSize: theme.typography.pxToRem(15),
        flexBasis: '33.33%',
        flexShrink: 0,
    },
    secondaryHeading: {
        fontSize: theme.typography.pxToRem(15),
        color: theme.palette.text.secondary,
    },
    selectedRow: {
        fontWeight: 500,
    },
    header: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
        fontSize: 15,
        fontWeight: 500,
    },
    pairsPanel: {
        marginBottom: 40,
    }
});

const descriptorFields = [
    { name: 'sequenceA', caption: 'First Sequence' },
    { name: 'sequenceB', caption: 'Second Sequence' },
    { name: 'alignmentType', caption: 'Alignment Type' },
    { name: 'scoringMatrixName', caption: 'Scoring Matrix' },
    { name: 'fixGapPenalty', caption: 'Open Gap Penalty' },
    { name: 'varGapPenalty', caption: 'Extension Gap Penalty' },
    { name: 'minScore', caption: 'Minimum Score' },
    { name: 'maxNumberOfSolutions', caption: 'Maximum Number of Solutions' },
];

const solutionFields = [
    { name: 'score', caption: 'Score' },
    { name: 'evalue', caption: 'E-Value', formatter: (value) => value.toExponential(1) },
    { name: 'positives', caption: 'Positives' },
    { name: 'positivesPercentage', caption: '% Positives', formatter: (value) => <BarPercentage percentage={value} /> },
    { name: 'identities', caption: 'Identities' },
    { name: 'identitiesPercentage', caption: '% Identities', formatter: (value) => <BarPercentage percentage={value} /> },
    { name: 'alignedSequences', caption: 'Length', formatter: (alignedSequences) => alignedSequences.length },
];

const hueLevel = 180;

const BarPercentage = props => 
    <Fragment>
        <ColoredBar
            horizontalBar
            toValue={props.percentage}
            color={`rgba(${hueLevel}, 0, 0, 0.5)`}
            backgroundColor={`rgba(${hueLevel}, ${hueLevel}, ${hueLevel}, 0.5)`}
            size={200} >
            {props.percentage}%
        </ColoredBar>
    </Fragment>;

class Solution extends React.Component {

    render() {
        const { classes, descriptor, solution } = this.props;

        return (
            <div className={classes.root}>
                <ExpansionPanel>
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography className={classes.heading}>Parameters</Typography>
                        <Typography className={classes.secondaryHeading}>Parameters used to compute this solution</Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        <PairsPanel fields={descriptorFields} record={descriptor} />
                    </ExpansionPanelDetails>
                </ExpansionPanel>
                <ExpansionPanel>
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography className={classes.heading}>Scoring Values</Typography>
                        <Typography className={classes.secondaryHeading}></Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        <PairsPanel fields={solutionFields} record={solution} />
                    </ExpansionPanelDetails>
                </ExpansionPanel>
                <ExpansionPanel expanded={true}>
                    <ExpansionPanelSummary>
                        <Typography className={classes.heading}>Solution Alignment</Typography>
                        <Typography className={classes.secondaryHeading}></Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        <SolutionAlignment hueLevel={hueLevel} solution={solution} />
                    </ExpansionPanelDetails>
                </ExpansionPanel>
            </div>
        );
    };
}

Solution.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Solution);
