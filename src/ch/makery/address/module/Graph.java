package ch.makery.address.module;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private ArrayList<ArrayList<Integer>> matrixAdjancy;

    public Graph() {
        matrixAdjancy = new ArrayList<>();
    }

    public void addVertex(ArrayList<Circle> circles) {
        for (int i = matrixAdjancy.size(); i < circles.size(); i++) {
            matrixAdjancy.add(new ArrayList<>());
            for (int j = 0; j < circles.size(); j++) {
                matrixAdjancy.get(i).add(999);

            }

                for (int k = 0; k < circles.size()-1; k++) {
                    matrixAdjancy.get(k).add(888);
                }

        }



    }

    public void showMatrix() {
        for (ArrayList<Integer> integers : matrixAdjancy) {
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }

    }


}
