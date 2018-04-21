public class Netpbm {

    public static void main(String[] args) {
        String[] fileformat = args[1].split("-");
        Picture result = Methods.pictureFromRaster(Methods.readFile(args[0]));
        for (int i = 2; i < args.length; i++) {
            switch (args[i]) {
                case "-i": result = Methods.invertPic(result);
                    break;
                case "-v": result = Methods.verticalMirror(result);
                    break;
                case "-h": result = Methods.horizontalMirror(result);
                    break;
                case "-c": result = Methods.centralMirror(result);
                    break;
                case "-l": result = Methods.leftRotat(result);
                    break;
                case "-r": result = Methods.rightRotat(result);
                    break;
                case "-g": result = Methods.colorToGrey(result);
                    break;
                case "-b": result = Methods.colorToBlackWhite(result);
                    break;
            }
        }

        Methods.write("result." + fileformat[1], result);
/*
        A program paraméterezése:
        program input kimeneti-formátum output [mûveletek]
        A lehetséges kimeneti formátumok:
        -PBM : PBM fájl
        -PGM : PGM fájl
        -PPM : PPM fájl
        A lehetséges mûveletek:
        -i : invertálás
        -v : függõleges tükrözés
        -h : vízszintes tükrözés
        -c : középpontos tükrözés (180 fokos forgatás)
        -l : balra forgatás (90 fokos forgatás)
        -r : jobba forgatás (270 fokos forgatás)
        -g : szürkeárnyalatosra konverzió
        -b : fekete-fehérre konverzió
*/
    }

}




