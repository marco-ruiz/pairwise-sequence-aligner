import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';

const styles = {
    sequence: {
        display: "block",
        fontFamily: "monospace",
//        fontSize: 14,
        whiteSpace: "pre",
        color: "black",
//        fontWeight: 700
    }
}

class AlignedSequences extends React.Component {

    constructor(props) {
        super(props);
    
        this.state = this.createStateObject(props);
    }

    componentWillReceiveProps(nextProps){
        this.setState(this.createStateObject(nextProps));
    }

    createStateObject = props => {
        const alignedSequences = props.solution.alignedSequences;
        const affixLevels = Array(alignedSequences.affixLength).fill(0);
        return { 
            allLevels: affixLevels.concat(alignedSequences.scoreContributionLevels, affixLevels)
        };
    }

    createSymbols = (formattedSequence) => {
        return formattedSequence.split("").map((symbol, index) => 
            <span key={index} style={{ backgroundColor: this.getRGBA(index) }}>{symbol}</span>
        );
    }

    getRGBA = (index) => {
        const level = this.state.allLevels[index];
        const alpha = Math.abs(level * 0.5);
        const rgb = (level > 0) ? this.props.hueLevel + ", 0, 0" : "0, 0, " + this.props.hueLevel;
        return 'rgba(' + rgb + ', ' + alpha + ')';
    }

    render() {
        const { formattedAlignedA, formattedAlignment, formattedAlignedB } = this.props.solution.alignedSequences;
        const sequences = [formattedAlignedA, formattedAlignment, formattedAlignedB];

        return (
            <div className={this.props.classes.sequence}>
                {sequences.map(seq => <div>{this.createSymbols(seq)}</div>)}
            </div>
        );
    };
}

AlignedSequences.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(AlignedSequences);


