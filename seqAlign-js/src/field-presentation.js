
export default class FieldPresentation {

    constructor(fieldCaption, fieldName, fieldValueFormatter = (value) => value) {
        this.caption = fieldCaption;
        this.name = fieldName;
        this.formatter = fieldValueFormatter;
    }

    extractValue(target) {
        const rawValue = this.name ? target[this.name] : target;
        return this.formatter(rawValue);
    }
}
