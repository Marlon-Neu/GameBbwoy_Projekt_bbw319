package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;

public class Background implements PixelDrawing {
    private final int borderLeft;
    private final int borderRight;

    public Background(int borderLeft, int borderRight) {

        this.borderLeft = borderLeft;
        this.borderRight = borderRight;
    }


    @Override
    public void tick(PixelDisplay graphic) {
        for (int j=0;j< graphic.getPixelHeight();j+=2){

            for(int k=0;k<borderLeft;k+=2){
                graphic.setPixel(k,j, PixelColor.LOW);
            }

            for(int k=1;k<borderLeft;k+=2){
                graphic.setPixel(k,j, PixelColor.HIGH);
            }

            graphic.setPixel(borderLeft,j,PixelColor.BLACK);

            for(int k=borderLeft+1;k<borderRight;k++){
                graphic.setPixel(k,j, PixelColor.WHITE);
            }

            graphic.setPixel(borderRight,j,PixelColor.BLACK);

            for(int k=borderRight+1;k< graphic.getPixelWidth();k+=2){
                graphic.setPixel(k,j, PixelColor.LOW);
            }
            for(int k=borderRight+2;k< graphic.getPixelWidth();k+=2){
                graphic.setPixel(k,j, PixelColor.HIGH);
            }
        }


        for (int j=1;j< graphic.getPixelHeight();j+=2){
            for(int k=0;k<borderLeft;k+=2){
                graphic.setPixel(k,j, PixelColor.HIGH);
            }
            for(int k=1;k<borderLeft;k+=2){
                graphic.setPixel(k,j, PixelColor.LOW);
            }

            graphic.setPixel(borderLeft,j,PixelColor.BLACK);

            for(int k=borderLeft+1;k<borderRight;k++){
                graphic.setPixel(k,j, PixelColor.WHITE);
            }

            graphic.setPixel(borderRight,j,PixelColor.BLACK);

            for(int k=borderRight+1;k< graphic.getPixelWidth();k+=2){
                graphic.setPixel(k,j, PixelColor.HIGH);
            }
            for(int k=borderRight+2;k< graphic.getPixelWidth();k+=2){
                graphic.setPixel(k,j, PixelColor.LOW);
            }
        }
    }




}
