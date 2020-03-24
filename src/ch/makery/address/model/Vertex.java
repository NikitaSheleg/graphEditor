package ch.makery.address.model;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private Circle circle;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private List<Arc> arcs = new ArrayList<>();

    public void addArc(Arc arc) {
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
