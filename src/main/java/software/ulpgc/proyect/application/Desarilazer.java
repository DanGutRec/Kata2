package software.ulpgc.proyect.application;

import software.ulpgc.proyect.model.Movie;

public class Desarilazer {
    public static Movie fromTsv(String line){
        return fromTsv(line.split("\t"));
    }

    private static Movie fromTsv(String[] split) {
        return new Movie(split[0],split[1],split[2],split[3],toBoolean(split[4]),toInt(split[5]),toInt(split[6]),toInt(split[7]),toSplit(split[8]));
    }

    private static String[] toSplit(String s) {
        return s.split(",");
    }

    private static int toInt(String s) {
        if (s.equals("\\N")) return -1;
        return Integer.parseInt(s);
    }

    private static boolean toBoolean(String s) {
        return s.equals("1");
    }
}
