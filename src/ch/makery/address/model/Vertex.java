package ch.makery.address.model;

import ch.makery.address.controller.MyApplication;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private Circle circle;

    private List<Arc> arcs = new ArrayList<>();

    private int id = 0;
    private Text text = new Text();

    public Text getText() {
        return text;
    }

    public void setTextInPane(){
        MyApplication.pane.getChildren().add(text);
    }

    public void setText(Text text) {
        this.text = text;
    }

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

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
