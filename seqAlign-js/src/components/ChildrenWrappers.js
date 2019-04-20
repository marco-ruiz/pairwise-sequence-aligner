import React from 'react';

const computeCircularDisplacement = (max, current, offset) => {
    const trueIndexOffset = offset % max;
    let result = current + trueIndexOffset;
    if (result < 0) result += max;
    if (result > max - 1) result -= max;
    return result;
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
            this.setState({ 
                selectedIndex: computeCircularDisplacement(this.state.presentation.length, this.state.selectedIndex, indexOffset), 
            });
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

