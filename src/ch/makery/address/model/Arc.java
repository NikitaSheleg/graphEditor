package ch.makery.address.model;


import ch.makery.address.controller.MyApplication;
import javafx.beans.InvalidationListener;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.Arrays;
import java.util.EventListener;


public class Arc extends Line {


    private Vertex begin;

    private Vertex end;

    int weight;
    private Line line1 = new Line();
    private Line line2 = new Line();
    private Line line3;
    private Line line4;


    Runnable binaryRunnable = () -> {
        InvalidationListener updater = o -> {
            double ex = getStartX();
            double ey = getStartY();
            double sx = getEndX();
            double sy = getEndY();


            line3.setEndX(ex);
            line3.setEndY(ey);
            line4.setEndX(ex);
            line4.setEndY(ey);


            if (ex == sx && ey == sy) {
                // arrow parts of length 0
                line3.setStartX(ex);
                line3.setStartY(ey);
                line4.setStartX(ex);
                line4.setStartY(ey);

            } else {
                double factor = 10 / Math.hypot(sx - ex, sy - ey);
                double factorO = 10 / Math.hypot(sx - ex, sy - ey);

                // part in direction of main line
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

                // part ortogonal to main line
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;

                line3.setStartX(ex + dx - oy);
                line3.setStartY(ey + dy + ox);
                line4.setStartX(ex + dx + oy);
                line4.setStartY(ey + dy - ox);
            }
        };

        // add updater to properties
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    };


    public void setUnorientedArrow(Pane pane) {
        setArrow(pane);
        line3 = new Line();
        line4 = new Line();
        line3.setStrokeWidth(2);
        line4.setStrokeWidth(2);
        pane.getChildren().add(line3);
        pane.getChildren().add(line4);
    }

    public boolean isUnoriented() {
        return line3 != null && line4 != null;
    }

    public void setArrow(Pane pane) {

        line1.setStrokeWidth(2);

        line2.setStrokeWidth(2);

        pane.getChildren().add(line1);

        pane.getChildren().add(line2);

    }


    Runnable runnable = () -> {
        InvalidationListener updater = o -> {
            double ex = getEndX();
            double ey = getEndY();
            double sx = getStartX();
            double sy = getStartY();

            line1.setEndX(ex);
            line1.setEndY(ey);
            line2.setEndX(ex);
            line2.setEndY(ey);

            if (ex == sx && ey == sy) {
                // arrow parts of length 0
                line1.setStartX(ex);
                line1.setStartY(ey);
                line2.setStartX(ex);
                line2.setStartY(ey);
            } else {
                double factor = 10 / Math.hypot(sx - ex, sy - ey);
                double factorO = 10 / Math.hypot(sx - ex, sy - ey);

                // part in direction of main line
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

                // part ortogonal to main line
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;

                line1.setStartX(ex + dx - oy);
                line1.setStartY(ey + dy + ox);
                line2.setStartX(ex + dx + oy);
                line2.setStartY(ey + dy - ox);
            }
        };

        // add updater to properties
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    };

    public void updateArrow() {
        runnable.run();
    }

    public void updateUnorientedArrow() {
        binaryRunnable.run();
    }

    public Arc(double x1, double y1, double x2, double y2) {

        super(x1, y1, x2, y2);
        this.setStrokeLineCap(StrokeLineCap.ROUND);

    }


    public Vertex getBegin() {

        return begin;

    }


    public void setBegin(Vertex begin) {

        this.begin = begin;

    }


    public Vertex getEnd() {

        return end;

    }


    public void setEnd(Vertex end) {

        this.end = end;

    }

}