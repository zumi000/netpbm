public class Pixels {
    int pixR;
    int pixG;
    int pixB;
    int pix;

    Pixels(int pixR, int pixG, int pixB) {
        this.pixR = pixR;
        this.pixG = pixG;
        this.pixB = pixB;
    }

    Pixels(int pix) {
        this.pix = pix;
        }

    @Override
    public String toString() {
        return pixR + " " + pixB + " " + pixG + " ";
    }

}



