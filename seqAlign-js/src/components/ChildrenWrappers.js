import React from 'react';

const computeIndex = (items, currentIndex, indexOffset) => {
    const len = items.length;
    const trueIndexOffset = indexOffset % len;
    let selectedIndex = currentIndex + trueIndexOffset;
    if (selectedIndex < 0) selectedIndex += len;
    if (selectedIndex > len - 1) selectedIndex -= len;
    return selectedIndex;
}

export const withSingleChild = (WrappedComponent) => {
    return class SingleChildComponent extends React.Component {
        constructor(props) {
            super(props);

            this.state = {
                children: props.children,
                selectedIndex: props.selectedIndex,
            };
        }

        moveToSelect = (indexOffset) => {
            this.setState({ selectedIndex: computeIndex(this.state.presentation, this.state.selectedIndex, indexOffset), });
        }

        render() {
            return (
                <WrappedComponent {...this.props}>
                    {this.state.children[this.state.selectedIndex]}
                </WrappedComponent>
            );
        }
    }
}

export const withChildrenTransformer = (WrappedComponent, transformer) => {
    return props => 
        <WrappedComponent {...props}>
            {props.children.map((child, index) => transformer(child, index))}/>
        </WrappedComponent>
}

