package ch.makery.address.module;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private Circle circle;

    private List<Arc> arcs = new ArrayList<>();

    public void addArc(Arc arc){
        arcs.add(arc);
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }


}
