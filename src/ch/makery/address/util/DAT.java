package ch.makery.address.util;

import java.io.Serializable;

public class DAT implements Serializable {

    private double newTranslateX;//сохраняемое положение по Х
    private double newTranslateY;//сохраняемое положение по Y
    private double radius;
    private String text = "";//сохраняемый текст

    private double beginX;
    private double beginY;
    private double endX;
    private double endY;


    public DAT(double newTranslateX, double newTranslateY) {
        this.newTranslateX=newTranslateX;
        this.newTranslateY=newTranslateY;

    }

    public DAT(double beginX, double beginY, double endX, double endY) {
        this.beginX = beginX;
        this.beginY = beginY;
        this.endX = endX;
        this.endY = endY;
    }

    public double getBeginX() {
        return beginX;
    }

    public double getBeginY() {
        return beginY;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }

    /**
     * @return the newTranslateX
     */
    public double getNewTranslateX() {
        return newTranslateX;
    }

    /**
     * @return the newTranslateY
     */
    public double getNewTranslateY() {
        return newTranslateY;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    public double getRadius() {
        return radius;
    }
}
