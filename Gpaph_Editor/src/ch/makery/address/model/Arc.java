package ch.makery.address.model;


import ch.makery.address.controller.MyApplication;
import javafx.beans.InvalidationListener;
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


    Vertex begin;

    Vertex end;

    int weight;
    Line line1 = new Line();
    Line line2 = new Line();

    public void setArrow() {
        line1.setStrokeWidth(2);
        line2.setStrokeWidth(2);
        MyApplication.pane.getChildren().add(line1);
        MyApplication.pane.getChildren().add(line2);
    }

    public void updateArrow() {

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