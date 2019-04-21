import React, { Fragment, useState, useMemo } from 'react';
import IconButton from '@material-ui/core/IconButton';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';

const CarouselCaption = ({ children, onMove }) =>
    <Fragment>
        <IconButton onClick={() => onMove(-1)}>
            <ChevronLeftIcon />
        </IconButton>
        {children}
        <IconButton onClick={() => onMove(1)}>
            <ChevronRightIcon />
        </IconButton>
    </Fragment>

export default CarouselCaption;

const carouselDisplacer = (max) => 
    (current) => 
        (offset = 0) => {
            let result = (current + offset) % max;
            if (result < 0) result += max;
            if (result >= max) result -= max;
            return result;
        }

export const useCarouselIndex = (max, initialIndex) => {
    const displacer = carouselDisplacer(max);
    const [index, setIndex] = useState(displacer(initialIndex)(0));
    return [index, offset => setIndex(prevIndex => displacer(prevIndex)(offset))];
}

const createCarouselArray = (items, fromIndex) => items.slice(fromIndex).concat(items.slice(0, fromIndex));

export const useCarousel = (items, initialIndex) => {
    const [selectedIndex, offsetter] = useCarouselIndex(items.length, initialIndex);
    const [carouselItems, setCarouselItems] = useState(createCarouselArray(items, initialIndex));
    useMemo(() => setCarouselItems(createCarouselArray(items, selectedIndex)), [selectedIndex]);
    return [carouselItems, offsetter];
}
