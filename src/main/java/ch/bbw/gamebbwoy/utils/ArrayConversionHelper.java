package ch.bbw.gamebbwoy.utils;

import java.util.Arrays;

public class ArrayConversionHelper {
    public static void main(String[] args) {
        String toConvert = "000000000000000232000030000023200033300002320033333000232000232000333330023200003330002320000030000232000000000000000";

        Arrays.stream(toConvert.split("")).forEach( str-> {
            System.out.print(str+",");
                });
    }
}
