public class Picture {
    int width;
    int height;
    int maxVal;
    Pixels[][] pixels;

    Picture(int width, int height, int maxVal, Pixels[][] pixels) {
        this.width = width;
        this.height = height;
        this.maxVal = maxVal;
        this.pixels = pixels;
    }

    // pbm B&W: P1 [Whitespace] Width [Whitespace] H [Whitespace] pixels
    // pgm Gray: P2 [Whitespace] W [Whitespace] H [Whitespace] maxPix [Whitespace] pixels
    // ppm RGB: P3 [Whitespace] W [Whitespace] H [Whitespace] maxPix [Whitespace] pixR [Whitespace(s)] pixB [Whitespace(s)] pixG [Whitespace(s)]
}
