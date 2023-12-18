package ch.bbw.gamebbwoy.utils;

import java.util.Arrays;

public class ArrayConversionHelper {
    public static void main(String[] args) {
        String toConvert = "add(new Integer[]{0110000100010110100010000110});\n" +
                "add(new Integer[]{0110000100010110000100010110});\n" +
                "add(new Integer[]{0000100110010110000100010000});\n" +
                "add(new Integer[]{0110100010000110000100010110});\n" +
                "add(new Integer[]{0110100010000110100110010110});\n" +
                "add(new Integer[]{0110000100010000000100010000});\n" +
                "add(new Integer[]{0110100110010110100110010110});\n" +
                "add(new Integer[]{0110100110010110000100010110});\n";

        Arrays.stream(toConvert.split("")).forEach( str-> {
                    if(str.equals("1")) System.out.print("3,");
                    else if(str.equals("0"))System.out.print("-1,");
                    else System.out.print(str);
                });
    }
}
