
export default class RgbaColor {
    
    constructor(r, g, b, a = 1) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    withAllLevels(level) {
        return new RgbaColor(level, level, level, this.a);
    } 

    factorAlphaBy(level) {
        return this.clone({ alpha: Math.abs(level) * this.a });
    }

    clone({ red = this.r, green = this.g, blue = this.b, alpha = this.a }) {
        return new RgbaColor(red, green, blue, alpha);
    }

    toString() {
        return `rgba(${this.r}, ${this.g}, ${this.b}, ${this.a})`;
    }
}
