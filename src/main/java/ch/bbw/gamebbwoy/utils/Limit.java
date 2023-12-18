package ch.bbw.gamebbwoy.utils;

public class Limit {
    public static double maxDouble(double num, double limit){
        if(num>limit){
            return limit;
        }
        if(num<-limit){
            return -limit;
        }
        return num;
    }

    public static double minDouble(double num,double limit){
        if(num>limit){
            return num;
        }
        if(num<-limit){
            return num;
        }
        if(num>0){
            return limit;
        }
        return -limit;
    }
}
