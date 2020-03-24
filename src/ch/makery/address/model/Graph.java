package ch.makery.address.model;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Graph {
    private ArrayList<ArrayList<Integer>> matrixAdjancy;

    public Graph() {
        matrixAdjancy = new ArrayList<>();
    }

    public void addVertex(ArrayList<Circle> circles) {
        for (int i = matrixAdjancy.size(); i < circles.size(); i++) {
            matrixAdjancy.add(new ArrayList<>());
            for (int j = 0; j < circles.size(); j++) {
                matrixAdjancy.get(i).add(0);

            }

            for (int k = 0; k < circles.size() - 1; k++) {
                matrixAdjancy.get(k).add(0);
            }
        }
    }

    public void addArc(Arc arc) {
        matrixAdjancy.get(arc.getBegin().getId()).set(arc.getEnd().getId(),1);
        matrixAdjancy.get(arc.getEnd().getId()).set(arc.getBegin().getId(),1);
    }

    public void showMatrix() {
        for (ArrayList<Integer> integers : matrixAdjancy) {
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }

    }

    public void showMultipleArc(ArrayList<Arc> arcs){


    }


}
