import React, { Fragment, useState } from 'react';
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

const carouselDisplacer = (max) => 
    (current) => 
        (offset = 0) => {
            let result = (current + offset) % max;
            if (result < 0) result += max;
            if (result >= max) result -= max;
            return result;
        }

export const useCarousel = (max, initialIndex) => {
    const displacer = carouselDisplacer(max);
    const [index, setIndex] = useState(displacer(initialIndex)(0));
    return [index, offset => setIndex(prevIndex => displacer(prevIndex)(offset))];
}
