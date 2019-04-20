import React, { Fragment } from 'react';
import IconButton from '@material-ui/core/IconButton';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';

export default ({ children, onMove }) =>
    <Fragment>
        <IconButton onClick={() => onMove(-1)}>
            <ChevronLeftIcon />
        </IconButton>
        {children}
        <IconButton onClick={() => onMove(1)}>
            <ChevronRightIcon />
        </IconButton>
    </Fragment>
