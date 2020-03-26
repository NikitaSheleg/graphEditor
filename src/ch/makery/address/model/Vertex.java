package ch.makery.address.model;

import ch.makery.address.controller.MyApplication;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private Circle circle;
    private int id;
    private Text text = new Text();



    public Text getText() {

        return text;

    }



    public void setTextInPane(Pane pane){

        pane.getChildren().add(text);

    }



    public void setText(Text text) {

        this.text = text;

    }
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
