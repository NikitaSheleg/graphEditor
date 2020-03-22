package ch.makery.address.model;

import javafx.scene.shape.Circle;

public class Vertex {
    private Arc arc;
    Circle circle;

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Arc getArc() {
        return arc;
    }

    public void setArc(Arc arc) {
        this.arc = arc;
    }
}
