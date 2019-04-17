import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import { FormHelperText } from '@material-ui/core';

const styles = {
    verticalContainer: {
    },
    horizontalContainer: {
        display: "flex",
    },
}

class ColoredBar extends React.Component {

    createBarStyle = (propSize, fromVal, toVal, valuesRangeSize, backgroundColor) => {
        const percent = 100 * Math.abs(toVal - fromVal) / valuesRangeSize;
        const barStyle = { backgroundColor };
        barStyle[propSize] = percent + "%";
//        barStyle.paddingTop = "100%";
        return barStyle;
    }

    render() {
        let { classes, children, horizontalBar, size, 
                fromValue = 0, toValue = 0, 
                minValue = 0, maxValue = 100, 
                color = '#f00', backgroundColor } = this.props;

        const valuesRangeSize = maxValue - minValue;
        const propSize = (horizontalBar) ? 'width' : 'height';

        if (fromValue > toValue) 
            [toValue, fromValue] = [fromValue, toValue];

        if (fromValue < minValue) fromValue = minValue;
        if (toValue > maxValue) toValue = maxValue;

        const lowerBarStyle = this.createBarStyle(propSize, minValue, fromValue, valuesRangeSize, backgroundColor);
        const barStyle = this.createBarStyle(propSize, fromValue, toValue, valuesRangeSize, color);
        const higherBarStyle = this.createBarStyle(propSize, toValue, maxValue, valuesRangeSize, backgroundColor);

        return (
            <div className={horizontalBar ? classes.horizontalContainer : classes.verticalContainer} style={{ [propSize]: size }}>
                {!horizontalBar && children}
                &nbsp;
                <div style={horizontalBar ? lowerBarStyle : higherBarStyle}></div>
                <div style={barStyle}></div>
                <div style={horizontalBar ? higherBarStyle : lowerBarStyle}></div>
                &nbsp;
                {horizontalBar && children}
            </div>
        );
    };
}

ColoredBar.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ColoredBar);


