package ch.makery.address.controller;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import ch.makery.address.module.Arc;
import ch.makery.address.module.Graph;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Translate;

import javax.swing.*;

public class MyControler implements Initializable {
    private final float CIRCLE_RADIUS = 10.0f;
    private Translate translate = new Translate();
    public static int countCircle = 0;
    public Graph graph = new Graph();

    private double x1, x2 = 0;
    private double y1, y2 = 0;

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    @FXML
    private Button penCircle = new Button();

    @FXML
    private Button penLine = new Button();

    @FXML
    private Button transform = new Button();

    private ArrayList<Circle> circleArray = new ArrayList<>();
    private ArrayList<Arc> arcArray = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToolBar toolBar = new ToolBar();
        toolBar.getItems().add(penCircle);
        toolBar.getItems().add(penLine);
        toolBar.getItems().add(transform);

        InputStream input =
                getClass().getResourceAsStream("/ch/makery/address/view/circle.png");

        Image image = new Image(input);
        ImageView imageView = new ImageView(image);


        penCircle.graphicProperty().setValue(imageView);
        penLine.graphicProperty().setValue(imageView);
        transform.graphicProperty().setValue(imageView);
        // TODO (don't really need to do anything here).

        System.out.println("Hi");


    }

    // Ивент нажатия кнопки
    public void penCircleAction(ActionEvent event) {
        MyApplication.scene.setCursor(Cursor.DEFAULT);
        penCircle.setDisable(true);
        penLine.setDisable(false);
        transform.setDisable(false);
        MyApplication.scene.addEventFilter(MouseEvent.MOUSE_CLICKED, drawCircle);
        for (Circle circle : circleArray) {
            circle.setOnMousePressed(null);
            circle.setOnMouseDragged(null);
        }

    }

    //Ивент нажатия кнопки
    public void penLineAction(ActionEvent event) {
        MyApplication.scene.setCursor(Cursor.CROSSHAIR);
        penCircle.setDisable(false);
        penLine.setDisable(true);
        transform.setDisable(false);
        for (Circle circle : circleArray) {
            circle.setOnMousePressed(null);
            circle.setOnMouseDragged(null);
        }
        graph.showMatrix();
        for (Circle circle : circleArray) {
            circle.addEventFilter(MouseEvent.MOUSE_CLICKED, lineDrawEvent);
        }

    }


    public void transformAction(ActionEvent event) {
        MyApplication.scene.setCursor(Cursor.DEFAULT);
        penCircle.setDisable(false);
        penLine.setDisable(false);
        transform.setDisable(true);
        for (Circle circle : circleArray) {
            circle.setOnMousePressed(circleOnMousePressedEventHandler);
            circle.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        }

    }


    EventHandler<MouseEvent> trans = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Dragboard db = circleArray.get(0).startDragAndDrop(TransferMode.ANY);
        }
    };

    //Ивент рисования круга
    EventHandler<MouseEvent> drawCircle = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (penCircle.isDisable() && e.getX() > 55 && e.getY() > 44) {
                Circle circle = new Circle();
                circleArray.add(circle);
                circle.setCenterX(e.getX());
                circle.setCenterY(e.getY());
                circle.setRadius(CIRCLE_RADIUS);
                circle.setStroke(Color.GREEN);
                circle.setPickOnBounds(true);
                circle.setFill(Color.WHITE);
                countCircle++;
                circle.setId(String.valueOf(countCircle));
                graph.addVertex(circleArray);
                /*circle.setOnMousePressed(circleOnMousePressedEventHandler);
                circle.setOnMouseDragged(circleOnMouseDraggedEventHandler);*/
                MyApplication.pane.getChildren().add(circle);

            }

        }
    };


    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    if (transform.isDisable()) {
                        orgSceneX = t.getSceneX();
                        orgSceneY = t.getSceneY();
                        orgTranslateX = ((Circle) (t.getSource())).getTranslateX();
                        orgTranslateY = ((Circle) (t.getSource())).getTranslateY();
                    }
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((Circle) (t.getSource())).setTranslateX(newTranslateX);
                    ((Circle) (t.getSource())).setTranslateY(newTranslateY);

                }
            };


    EventHandler<MouseEvent> lineDrawEvent = new EventHandler<MouseEvent>() {
        Arc arc = new Arc(0, 0, 0, 0);


        @Override
        public void handle(MouseEvent t) {
            if (penLine.isDisable()) {

                if (x1 == 0 && y1 == 0) {
                    x1 = t.getSceneX();
                    y1 = t.getSceneY();
                    arc.setBegin(((Circle) (t.getSource())));
                    arc.setStartX(x1);
                    arc.setStartY(y1);
                } else {
                    if (x2 == 0 && y2 == 0) {
                        x2 = t.getSceneX();
                        y2 = t.getSceneY();

                        arc.setEnd(((Circle) (t.getSource())));


                        arc.setEndX(x2);
                        arc.setEndY(y2);

                        arcArray.add(arc);

                        //обавил немножко от себя
                        if (arc.getBegin().getFill() != Color.BROWN && arc.getEnd().getFill() != Color.GREEN) {
                            arc.getBegin().setFill(Color.GREEN);
                            arc.getEnd().setFill(Color.BROWN);
                        } else if (arc.getBegin().getFill() == Color.BROWN) {
                            arc.getEnd().setFill(Color.GREEN);
                        } else if (arc.getEnd().getFill() == Color.GREEN) {
                            arc.getBegin().setFill(Color.BROWN);
                        }


                        MyApplication.pane.getChildren().add(arc);
                        x1 = 0;
                        x2 = 0;
                        y1 = 0;
                        y2 = 0;
                        this.arc = new Arc(0, 0, 0, 0);
                    }

                }

            }
        }
    };


}
