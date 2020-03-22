package ch.makery.address.module;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Arc extends Line {

    Circle begin;
    Circle end;
    int weight;

    public Arc(double x1, double y1, double x2, double y2){
        super(x1, y1,x2,y2);
    }

    public Circle getBegin() {
        return begin;
    }

    public void setBegin(Circle begin) {
        this.begin = begin;
    }

    public Circle getEnd() {
        return end;
    }

    public void setEnd(Circle end) {
        this.end = end;
    }

}
