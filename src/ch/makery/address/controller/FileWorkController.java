package ch.makery.address.controller;

import ch.makery.address.model.Arc;
import ch.makery.address.model.Vertex;
import ch.makery.address.util.DAT;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileWorkController {


    //метод для сохранения объектов
    public void saveNode(List<Vertex> vertecies, List<Arc> arcs) throws IOException {
        List<DAT> dBList1 = new ArrayList<>();
        List<DAT> dBList2 = new ArrayList<>();
        for (Vertex vertex : vertecies) {
            dBList1.add(new DAT(vertex.getVertexTransX(), vertex.getVertexTransY()));
        }
        for (Arc arc : arcs) {
            dBList2.add(new DAT(arc.getStartX(), arc.getStartY(), arc.getEndX(), arc.getEndY()));
        }
        try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream("Node.dat"))) {
            ous.writeObject(dBList1);//сохраняем объект с данными о Node
            ous.writeObject(dBList2);//сохраняем объект с данными о Node
        }
    }

    //метод для восстановления объектов
    public void openNode(Tab tab) throws IOException, ClassNotFoundException {
        Pane root = new Pane();
        tab.setContent(root);
        ArrayList<Vertex> dragList1 = new ArrayList<>();
        ArrayList<Arc> dragList2 = new ArrayList<>();
        List<DAT> datList;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Node.dat"))) {
            datList = (ArrayList<DAT>) ois.readObject();
        }
        for (DAT dat : datList) {
            dragList1.add(new Vertex(dat.getNewTranslateX(), dat.getNewTranslateY(), root));
        }
        for (DAT dat : datList) {
            dragList2.add(new Arc(dat.getBeginX(), dat.getBeginY(), dat.getEndX(), dat.getEndY()));
        }
        root.getChildren().removeAll(datList);
        root.getChildren().addAll(dragList1);
        root.getChildren().addAll(dragList2);
    }

}
