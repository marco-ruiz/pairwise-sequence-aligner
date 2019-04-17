import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import { withStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import CreateNewFolderIcon from '@material-ui/icons/CreateNewFolder';
import TableSingleColumn from './TableSingleColumn'

const drawerWidth = 200;

const styles = theme => ({
    root: {
        display: 'flex',
    },
    toolbar: {
        paddingRight: 24, // keep right padding when drawer closed
    },
    toolbarIcon: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: '0 8px',
        ...theme.mixins.toolbar,
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
    },
    menuButton: {
        marginLeft: 12,
        marginRight: 12,
    },
    title: {
        flexGrow: 1,
    },
    drawerPaper: {
        position: 'relative',
        whiteSpace: 'nowrap',
        width: drawerWidth,
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    drawerPaperClose: {
        overflowX: 'hidden',
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        width: theme.spacing.unit * 7,
        [theme.breakpoints.up('sm')]: {
            width: theme.spacing.unit * 9,
        },
    },
    appBarSpacer: theme.mixins.toolbar,
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 3,
        height: '100vh',
        overflow: 'auto',
    },
    tableContainer: {
        height: 320,
    },
    h5: {
        marginBottom: theme.spacing.unit * 2,
    },
});

class ItemsDashboard extends React.Component {
    state = {
        drawerVisibility: true,
        selectedItemIndex: 0,
    };

    setDrawerVisibility = (drawerVisibility) => {
        this.setState({ drawerVisibility });
    }

    selectItem = (selectedItemIndex) => {
        this.setState({ selectedItemIndex });     
    }

    render() {
        const { classes, title, fields, listItems, onRestart, detailsFactory } = this.props;

        const menuIcon = 
            <IconButton
                color="inherit"
                aria-label="Open drawer"
                onClick={() => this.setDrawerVisibility(!this.state.drawerVisibility)}
                className={classes.menuButton}
            >
                <MenuIcon />
            </IconButton>

        const titleComponent = 
            <Typography component="h1" variant="h6" color="inherit" noWrap className={classes.title}>
                {title}
            </Typography>

        return (
            <div className={classes.root}>
                <CssBaseline />
                <AppBar
                    position="absolute"
                    className={classes.appBar}
                >
                    <Toolbar disableGutters={true} className={classes.toolbar}>
                        {menuIcon}{titleComponent}
                        <IconButton color="inherit" onClick={onRestart}>
                            <CreateNewFolderIcon />
                        </IconButton>
                    </Toolbar>
                </AppBar>
                <Drawer
                    variant="permanent"
                    classes={{ paper: classNames(classes.drawerPaper, !this.state.drawerVisibility && classes.drawerPaperClose), }}
                    open={this.state.drawerVisibility}
                >
                    <div className={classes.toolbarIcon}>
                        <IconButton onClick={this.handleDrawerClose}>
                            <ChevronLeftIcon />
                        </IconButton>
                    </div>
                    <Divider />
                    <TableSingleColumn 
                        classes={classes}
                        fields={fields}
                        listItems={listItems}
                        displayedFieldIndex={0}
                        onSelect={this.selectItem}
                        showHeader={this.state.drawerVisibility}
                    />
                    <Divider />
                </Drawer>
                <main className={classes.content}>
                    <div className={classes.appBarSpacer} />
                    {detailsFactory(fields, listItems[this.state.selectedItemIndex])}
                </main>
            </div>
        );
    }
}

ItemsDashboard.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ItemsDashboard);
