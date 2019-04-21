import React from 'react';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import Header from './Header';
import SelectControl from './SelectControl';

const styles = {
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    textField: {
        marginLeft: 5,
        marginRight: 5,
        width: 140,
    },
    formControl: {
        margin: 5,
        minWidth: 120,
    },
};

export default class DescriptorForm extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            descriptor: props.descriptor,
            error: '',
        };
    }

    onChangeField = (e, field, validator) => {
        this.setFieldValue(field, e.target.value, validator);
    }

    setFieldValue(field, value, validator) {
        let descriptor = Object.assign({}, this.state.descriptor);
        descriptor[field] = value;
        if (!validator || validator(value))
            this.setState(() => ({ descriptor }));
    }

    onSubmit = e => {
        e.preventDefault();
        const error = (!this.state.descriptor.sequenceA || !this.state.descriptor.sequenceB);
        this.setFieldValue('error', error ? 'Please provide both sequences.' : '');
        if (error) return;

        this.props.onSubmit(this.state.descriptor);
    }

    createNumericField = (label, fieldName) => 
        <TextField
            label={label}
            value={this.state.descriptor[fieldName]}
            style={styles.textField}
            onChange={e => this.onChangeField(e, fieldName, value => value.match(/^\d+$/))}
            type="number"
            InputLabelProps={{ shrink: true }}
            margin="normal"
            variant="outlined"
        />

    createSequenceField = (label, fieldName) => 
        <TextField
            label={label}
            value={this.state.descriptor[fieldName]} 
            onChange={e => this.onChangeField(e, fieldName)}
            margin="normal"
            variant="outlined"
            multiline
            rows="3"
            fullWidth
        />

    render() {
        const { matrixes } = this.props;
        return (
            <div style={styles.container}>
                <Header title="Alignment Definition" buttonLabel="Align!" onClick={this.onSubmit}/>

                {this.state.error && <p className="form__error">{this.state.error}</p>}

                {this.createSequenceField("First sequence to align", 'sequenceA')}
                {this.createSequenceField("Second sequence to align", 'sequenceB')}

                <SelectControl 
                    label="Alignment Type" 
                    value={this.state.descriptor.alignmentType} 
                    fieldName='alignmentType' 
                    onChange={e => this.onChangeField(e, 'alignmentType')}
                >
                    <MenuItem value="LOCAL">Local</MenuItem>
                    <MenuItem value="REPEATED_LOCAL">Repeated Local</MenuItem>
                    <MenuItem value="GLOBAL">Global</MenuItem>
                    <MenuItem value="OVERLAP_GLOBAL">Overlap Global</MenuItem>
                </SelectControl>

                <SelectControl 
                    label="Scoring Matrix" 
                    value={this.state.descriptor.scoringMatrixName} 
                    fieldName='scoringMatrixName' 
                    onChange={e => this.onChangeField(e, 'scoringMatrixName')}
                >
                {matrixes.map((name, index) => 
                    <MenuItem key={index} value={name}>{name}</MenuItem>
                )}
                </SelectControl>

                {this.createNumericField("Gap Open Penalty", 'fixGapPenalty')}
                {this.createNumericField("Gap Close Penalty", 'varGapPenalty')}
                {this.createNumericField("Min Score", 'minScore')}
                {this.createNumericField("Max Solutions", 'maxNumberOfSolutions')}
            </div>
        );
    }
}

//export default withStyles(styles)(DescriptorForm);
