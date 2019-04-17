import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';

const styles = {
    root: {
        flexGrow: 1,
//        marginBottom: 20
    },
    grow: {
        flexGrow: 1,
    }
};

const Header = (props) => 
    <AppBar position="static" className={props.classes.root}>
        <Toolbar>
            <Typography variant="h6" color="inherit" className={props.classes.grow}>
                {props.title}
            </Typography>
            {props.buttonLabel && <Button color="inherit" onClick={props.onClick}>{props.buttonLabel}</Button>}
        </Toolbar>
    </AppBar>

Header.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Header);
