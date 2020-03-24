package ch.makery.address.model;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Arc extends Line {

    Vertex begin;
    Vertex end;
    int weight;
    public Arc(double x1, double y1, double x2, double y2){
        super(x1, y1,x2,y2);
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
