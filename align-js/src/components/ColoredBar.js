import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import RgbaColor from '../rgba-color';

const styles = {
    verticalContainer: {
    },
    horizontalContainer: {
        display: "flex",
    },
}

export const PercentageBar = props =>
    <StyleColoredBar toValue={props.percentage} {...props} >
        {props.percentage}%
    </StyleColoredBar>

const barStylesGenerator = (propSize, valuesRangeSize, colors) =>
    (...boundaries) => 
        colors.map((backgroundColor, index) => {
            const toValue = boundaries[index + 1];
            const fromValue = boundaries[index];
            const percent = 100 * Math.abs(toValue - fromValue) / valuesRangeSize;
            const barStyle = { backgroundColor };
            barStyle[propSize] = percent + "%";
            return barStyle;
        });

const ColoredBar = ({ classes, colored = true, children, horizontalBar, size, 
                        fromValue = 0, toValue = 0, 
                        minValue = 0, maxValue = 100, 
                        color = new RgbaColor(255, 0, 0), backgroundColor }) => {

    const valuesRangeSize = maxValue - minValue;
    const propSize = (horizontalBar) ? 'width' : 'height';
    if (!colored) color = backgroundColor;
    const styleGenerator = barStylesGenerator(propSize, valuesRangeSize, [backgroundColor, color, backgroundColor]);

    if (fromValue > toValue) 
        [toValue, fromValue] = [fromValue, toValue];

    if (fromValue < minValue) fromValue = minValue;
    if (toValue > maxValue) toValue = maxValue;

    const [lowerBarStyle, barStyle, higherBarStyle] = styleGenerator(minValue, fromValue, toValue, maxValue);

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
}

ColoredBar.propTypes = {
    classes: PropTypes.object.isRequired,
    color: PropTypes.instanceOf(RgbaColor),
    backgroundColor: PropTypes.instanceOf(RgbaColor),
};

const StyleColoredBar = withStyles(styles)(ColoredBar);

export default StyleColoredBar;


