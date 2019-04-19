
export default class PropertyPresentation {

    constructor(fieldCaption, fieldName, fieldValueFormatter = (value) => value) {
        this.caption = fieldCaption;
        this.name = fieldName;
        this.formatter = fieldValueFormatter;
    }

    extractValue(target) {
        const rawValue = this.name ? target[this.name] : target;
        return this.formatter(rawValue);
    }

    clone(newValues) {
        return Object.assign(new PropertyPresentation(), this, newValues);
    }
}
