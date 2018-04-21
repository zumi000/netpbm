import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Methods {
    static String[] fileDetails = new String[5];     // ### format, width, height, maxvalue, P-key
    static ArrayList<Integer> rasterArray = new ArrayList<>();

    public static String readFile(String fileName) {
        BufferedReader reader = null;
        StringBuilder raster = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(new java.io.File(fileName)));
            String sizeline = null;
            String maxVal = null;
            String line = null;
            if ((fileDetails[4] = reader.readLine()) != null) {
                char format = fileDetails[4].charAt(1);
                switch (format) {
                    case '1':
                        fileDetails[0] = "pbm";
                        fileDetails[3] = "1";
                        break;
                    case '2':
                        fileDetails[0] = "pgm";
                        break;
                    case '3':
                        fileDetails[0] = "ppm";
                        break;
                    default:
                        fileDetails[0] = "Unknown file format";
                        break;
                }
            } else {
                System.out.println("Unknown file format");
            }
            if ((sizeline = reader.readLine()) != null) {
                String[] size = sizeline.split("\\s");
                fileDetails[1] = size[0];
                fileDetails[2] = size[1];
            } else {
                System.out.println("Unknown file size");
            }
            if (fileDetails[3] == null && (maxVal = reader.readLine()) != null) {
                if (Integer.parseInt(maxVal) > 0 && Integer.parseInt(maxVal) < 65536) {
                    fileDetails[3] = maxVal;
                } else {
                    System.out.println("Unknown max value of pixels");
                }
            } else {
                System.out.println("Unknown max value of pixels");
            }

            while ((line = (reader.readLine())) != null) {
                String replacedLine = line.replaceAll("\\s+", " ");
                raster.append(replacedLine).append(" ");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //showFileDetails();
        return raster.toString();

    }

    public static void showFileDetails() {
        for (int i = 0; i < fileDetails.length; i++) {
            System.out.println(fileDetails[i]);
        }
    }

    public static Picture pictureFromRaster (String raster) {
        String[] stringsFromRaster = raster.split("[\\s, \\n]");
        for (String pix : stringsFromRaster) {
            rasterArray.add(Integer.parseInt(pix));
        }

        //System.out.println(Arrays.deepToString(stringsFromRaster));

        Pixels[][] pixel = new Pixels[Integer.parseInt(fileDetails[2])][Integer.parseInt(fileDetails[1])];
            int i = 0;
            while (i < rasterArray.size()) {
                for (int j = 0; j < Integer.parseInt(fileDetails[2]); j++) {
                    for (int k = 0; k < Integer.parseInt(fileDetails[1]); k++) {
                        pixel[j][k] = new Pixels(rasterArray.get(i), rasterArray.get(i + 1), rasterArray.get(i + 2));
                        i = i + 3;
                    }
                }
            }



        //System.out.println(Arrays.deepToString(pixel));
        Picture result = new Picture(Integer.parseInt(fileDetails[1]), Integer.parseInt(fileDetails[2]), Integer.parseInt(fileDetails[3]), pixel);
        return result;
    }

    public static Picture invertPic(Picture picture) {
        for (int i = 0; i < picture.height; i++) {
            for (int j = 0; j < picture.width; j++) {
                picture.pixels[i][j].pixR = picture.maxVal - picture.pixels[i][j].pixR;
                picture.pixels[i][j].pixB = picture.maxVal - picture.pixels[i][j].pixB;
                picture.pixels[i][j].pixG = picture.maxVal - picture.pixels[i][j].pixG;
                picture.pixels[i][j].pix = picture.maxVal - picture.pixels[i][j].pix;
            }
        }
        return picture;
    }

    public static Picture verticalMirror(Picture picture) {
        Picture result = new Picture(picture.width, picture.height, picture.maxVal, new Pixels[picture.height][picture.width]);
        for (int i = 0; i < picture.height; i++) {
            for (int j = 0; j < picture.width; j++) {
                result.pixels[i][j] = picture.pixels[i][picture.width - j - 1];
            }
        }
        return result;
    }

    public static Picture horizontalMirror(Picture picture) {
        Picture result = new Picture(picture.width, picture.height, picture.maxVal, new Pixels[picture.height][picture.width]);
        for (int i = 0; i < picture.height; i++) {
            for (int j = 0; j < picture.width; j++) {
                result.pixels[i][j] = picture.pixels[picture.height - i - 1][j];
            }
        }
        return result;
    }

    public static Picture centralMirror(Picture picture) {
        Picture result = verticalMirror(horizontalMirror(picture));
        return result;
    }

    public static Picture rightRotat(Picture picture) {
        Picture result = new Picture(picture.height, picture.width, picture.maxVal, new Pixels[picture.width][picture.height]);
        for (int i = 0; i < result.height; i++) { //58
            for (int j = 0; j < result.width; j++) { //11
                result.pixels[i][j] = picture.pixels[picture.height - j - 1][i];
            }
        }
        return result;
    }

    public static Picture leftRotat(Picture picture) {
        return centralMirror(rightRotat(picture));
    }

    public static Picture colorToGrey(Picture picture) {
        Picture result = new Picture(picture.width, picture.height, picture.maxVal, new Pixels[picture.height][picture.width]);
        for (int i = 0; i < picture.height; i++) {
            for (int j = 0; j < picture.width; j++) {
                result.pixels[i][j] = new Pixels((picture.pixels[i][j].pixR + picture.pixels[i][j].pixG + picture.pixels[i][j].pixB) / 3, (picture.pixels[i][j].pixR + picture.pixels[i][j].pixG + picture.pixels[i][j].pixB) / 3, (picture.pixels[i][j].pixR + picture.pixels[i][j].pixG + picture.pixels[i][j].pixB) / 3);
            }
        }
        return result;
    }

    public static Picture greyToBlackWhite(Picture picture) {
        Picture result = new Picture(picture.width, picture.height, picture.maxVal, new Pixels[picture.height][picture.width]);
        for (int i = 0; i < picture.height; i++) {
            for (int j = 0; j < picture.width; j++) {
                int newR = 0;
                int newG = 0;
                int newB = 0;
                if (picture.pixels[i][j].pixR > Integer.parseInt(fileDetails[3])/2) {
                    newR = Integer.parseInt(fileDetails[3]);
                }
                if (picture.pixels[i][j].pixG > Integer.parseInt(fileDetails[3])/2) {
                    newG = Integer.parseInt(fileDetails[3]);
                }
                if (picture.pixels[i][j].pixB > Integer.parseInt(fileDetails[3])/2) {
                    newB = Integer.parseInt(fileDetails[3]);
                }
                result.pixels[i][j] = new Pixels(newR, newG, newB);
            }
        }
        return result;
    }

    public static Picture colorToBlackWhite(Picture picture) {
        Picture result = greyToBlackWhite(colorToGrey(picture));
        return result;
    }


    public static void write (String fileName, Picture picture) {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(fileDetails[4]);
            writer.newLine();
            writer.write(picture.width + " " + picture.height);
            writer.newLine();
            writer.write(fileDetails[3]);
            writer.newLine();
            for (int i = 0; i < picture.height; i++) {
                for (int j = 0; j < picture.width; j++) {
                    writer.write(picture.pixels[i][j].toString());
                }
                writer.newLine();
            }
            writer.flush();
            } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }






