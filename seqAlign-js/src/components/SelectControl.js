import React from 'react';
import ReactDOM from 'react-dom';
import OutlinedInput from '@material-ui/core/OutlinedInput';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';

const styles = {
    formControl: {
        margin: 5,
        marginTop: 15,
        minWidth: 120,
    },
};

export default class SelectControl extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            labelWidth: 0,
        };
    }

    componentDidMount() {
        this.setState({
            labelWidth: ReactDOM.findDOMNode(this.InputLabelRef).offsetWidth,
        });
    }
    
    render() {
        const { label, value, fieldName, onChange } = this.props;
        return (
            <FormControl variant="outlined" style={styles.formControl}>
                <InputLabel ref={ref => { this.InputLabelRef = ref;}} htmlFor={"outlined-" + fieldName}>
                    {label}
                </InputLabel>
                <Select
                    value={value}
                    onChange={onChange}
                    input={<OutlinedInput labelWidth={this.state.labelWidth} name={fieldName} id={"outlined-" + fieldName}/>}
                >
                    {this.props.children}
                </Select>
            </FormControl>
        );
    }
}

