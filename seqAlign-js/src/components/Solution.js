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
import ColoredBar, { PercentageBar } from './ColoredBar';
import FieldPresentation from '../field-presentation';

const hueLevel = 180;

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

const parametersPresentation = [
    new FieldPresentation('First Sequence', 'sequenceA'),
    new FieldPresentation('Second Sequence', 'sequenceB'),
    new FieldPresentation('Alignment Type', 'alignmentType'),
    new FieldPresentation('Scoring Matrix', 'scoringMatrixName'),
    new FieldPresentation('Open Gap Penalty', 'fixGapPenalty'),
    new FieldPresentation('Extension Gap Penalty', 'varGapPenalty'),
    new FieldPresentation('Minimum Score', 'minScore'),
    new FieldPresentation('Maximum Number of Solutions', 'maxNumberOfSolutions'),
];

export const solutionPresentation = (percentageValueFormatter) => [
    new FieldPresentation('Score', 'score'),
    new FieldPresentation('E-Value', 'score', value => value.toExponential(1)),
    new FieldPresentation('Positives', 'positives'),
    new FieldPresentation('% Positives', 'positivesPercentage', percentageValueFormatter),
    new FieldPresentation('Identities', 'identities'),
    new FieldPresentation('% Identities', 'identitiesPercentage', percentageValueFormatter),
    new FieldPresentation('Length', '', sol => sol.alignedSequences.length),
];

const visualSolutionPresentation = solutionPresentation(value => 
    <PercentageBar percentage={value} hueLevel={hueLevel} horizontalBar size={200}/>
);

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
                        <PairsPanel presentation={parametersPresentation} record={descriptor} />
                    </ExpansionPanelDetails>
                </ExpansionPanel>
                <ExpansionPanel>
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography className={classes.heading}>Scoring Values</Typography>
                        <Typography className={classes.secondaryHeading}></Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        <PairsPanel presentation={visualSolutionPresentation} record={solution} />
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
