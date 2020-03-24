package ch.makery.address.controller;

import java.io.InputStream;
import java.net.URL;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import ch.makery.address.model.Arc;
import ch.makery.address.model.Graph;
import ch.makery.address.model.Vertex;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MyControler implements Initializable {
    private final float CIRCLE_RADIUS = 10.0f;
    private Translate translate = new Translate();
    public static int countCircle = 0;
    public Graph graph = new Graph();

    public static Stage stage;



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

        graph.showMultipleArc(arcArray);


    }

    Runnable thread = new Runnable() {
        @Override
        public void run() {
            for (Circle circle : circleArray) {
                circle.setOnMousePressed(circleOnMousePressedEventHandler);
                circle.setOnMouseDragged(circleOnMouseDraggedEventHandler);

            }
            System.out.println(arcArray.size());
            for (Arc arc : arcArray) {
                //arc.setOnMousePressed(weightArc); //Метод с кнопкой

                arc.setOnMouseDragged(transLine);
            }

        }
    };

    public void transformAction(ActionEvent event) {
        MyApplication.scene.setCursor(Cursor.DEFAULT);
        penCircle.setDisable(false);

        penLine.setDisable(false);
        transform.setDisable(true);

        thread.run();

    }


    EventHandler<MouseEvent> trans = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Dragboard db = circleArray.get(0).startDragAndDrop(TransferMode.ANY);
        }
    };

    List<Vertex> vertices = new ArrayList<>();
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


                Vertex vertex = new Vertex();
                vertex.setCircle(circle);
                vertices.add(vertex);
                circle.setAccessibleText("str");

                vertex.setId(countCircle);
                graph.addVertex(circleArray);
                countCircle++;


                MyApplication.pane.getChildren().add(circle);

            }

        }
    };


    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    if (transform.isDisable()) {
                        Circle circle = (Circle) t.getSource();

                        for (Vertex vertex : vertices) {
                            vertex.getCircle().setFill(Color.WHITE);
                            if (vertex.getCircle() == circle) {
                                vertex.getCircle().setFill(Color.GREEN);
                                vertex.getCircle().getScene().setOnKeyPressed(e -> {
                                    if (e.getCode() == KeyCode.R) {
                                        Label secondLabel = new Label("Enter name vertex");

                                        StackPane secondaryLayout = new StackPane();
                                        secondaryLayout.getChildren().add(secondLabel);

                                        Scene secondScene = new Scene(secondaryLayout, 230, 100);

                                        // New window (Stage)
                                        Stage newWindow = new Stage();
                                        newWindow.setTitle("Second Stage");
                                        newWindow.setScene(secondScene);

                                        // Specifies the modality for new window.
                                        newWindow.initModality(Modality.WINDOW_MODAL);

                                        // Specifies the owner Window (parent) for new window
                                        newWindow.initOwner(stage);

                                        // Set position of second window, related to primary window.
                                        newWindow.setX(stage.getX());
                                        newWindow.setY(stage.getY());

                                        newWindow.show();
                                    }
                                });
                            }
                        }


                    }
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    Circle circle = (Circle) t.getSource();
                    circle.setCenterX(t.getSceneX());
                    circle.setCenterY(t.getSceneY());
                    for (Vertex vertex : vertices) {
                        if (vertex.getCircle() == circle && vertex.getArcs() != null) {
                            for (Arc arc : vertex.getArcs()) {
                                if (arc.getBegin().getCircle() == vertex.getCircle()) {
                                    arc.setStartX(circle.getCenterX());
                                    arc.setStartY(circle.getCenterY());
                                } else if (arc.getEnd().getCircle() == vertex.getCircle()) {
                                    arc.setEndX(circle.getCenterX());
                                    arc.setEndY(circle.getCenterY());
                                }
                            }
                        }

                    }
                }
            };


    EventHandler<MouseEvent> lineDrawEvent = new EventHandler<MouseEvent>() {
        Arc arc = new Arc(0, 0, 0, 0);

        @Override
        public void handle(MouseEvent t) {
            if (penLine.isDisable()) {
                for (Vertex vertex : vertices) {
                    vertex.getCircle().setFill(Color.WHITE);
                }

                if (x1 == 0 && y1 == 0) {
                    Circle circle = (Circle) t.getSource();

                    for (Vertex vertex : vertices) {
                        if (vertex.getCircle() == circle) {
                            x1 = t.getSceneX();
                            y1 = t.getSceneY();

                            arc.setBegin(vertex);
                            arc.setStartX(((Circle) (t.getSource())).getCenterX());
                            arc.setStartY(((Circle) (t.getSource())).getCenterY());
                            vertex.addArc(arc);
                        }
                    }

                } else {
                    if (x2 == 0 && y2 == 0) {
                        x2 = t.getSceneX();
                        y2 = t.getSceneY();
                        Circle circle = (Circle) (t.getSource());
                        for (Vertex vertex : vertices) {
                            if (vertex.getCircle() == circle) {
                                vertex.addArc(arc);
                                arc.setEnd(vertex);

                                arc.setEndX(((Circle) (t.getSource())).getCenterX());
                                arc.setEndY(((Circle) (t.getSource())).getCenterY());

                                arcArray.add(arc);

                                //dобавил немножко от себя

                                //Красить в цвета кружочки

                                /*if (arc.getBegin().getCircle().getFill() == Color.WHITE) {
                                    arc.getBegin().getCircle().setFill(Color.GREEN);
                                }
                                if (arc.getEnd().getCircle().getFill() == Color.WHITE && arc.getBegin().getCircle().getFill() == Color.GREEN) {
                                    arc.getEnd().getCircle().setFill(Color.BROWN);
                                } else if (arc.getBegin().getCircle().getFill() == arc.getEnd().getCircle().getFill()) {
                                    arc.getBegin().getCircle().setFill(Color.STEELBLUE);
                                } else if (arc.getBegin().getCircle().getFill() == Color.BROWN) {
                                    arc.getEnd().getCircle().setFill(Color.GREEN);
                                } else if (arc.getEnd().getCircle().getFill() == Color.GREEN) {
                                    arc.getBegin().getCircle().setFill(Color.BROWN);
                                }*/
                                arc.setStrokeWidth(4);
                                graph.addArc(arc);

                                MyApplication.pane.getChildren().add(arc);
                                x1 = 0;
                                x2 = 0;
                                y1 = 0;
                                y2 = 0;
                                this.arc = new Arc(x1, y1, x2, y2);
                            }
                        }

                    }

                }

            }
        }
    };

    /*EventHandler<MouseEvent> weightArc =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    Arc arc = (Arc) t.getSource();
                    arc.setStroke(Color.GREEN);
                    arc.getScene().setOnKeyPressed(e -> {
                        if (e.getCode() == KeyCode.R) {
                            arc.setEndY(567);
                            arc.setStroke(Color.GRAY);
                        }
                    });

                }
            };*/

    EventHandler<MouseEvent> transLine =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    for (Vertex vertex : vertices) {
                        vertex.getCircle().setFill(Color.WHITE);
                    }
                    Arc arc = (Arc) t.getSource();
                    if (Math.abs(t.getSceneX() - arc.getStartX()) < 25 && Math.abs(t.getSceneY() - arc.getStartY()) < 25) {
                        arc.setStartX(t.getSceneX());
                        arc.setStartY(t.getSceneY());
                        for (Vertex vertex : vertices) {
                            if (Math.abs(arc.getStartX() - vertex.getCircle().getCenterX()) < 15 && Math.abs(arc.getStartY() - vertex.getCircle().getCenterY()) < 15) {
                                arc.setStartX(vertex.getCircle().getCenterX());
                                arc.setStartY(vertex.getCircle().getCenterY());
                                arc.setBegin(vertex);
                                vertex.addArc(arc);
                            }
                        }
                    } else if (Math.abs(t.getSceneX() - arc.getEndX()) < 25 && Math.abs(t.getSceneY() - arc.getEndY()) < 25) {
                        arc.setEndX(t.getSceneX());
                        arc.setEndY(t.getSceneY());
                        for (Vertex vertex : vertices) {
                            if (Math.abs(arc.getEndX() - vertex.getCircle().getCenterX()) < 15 && Math.abs(arc.getEndY() - vertex.getCircle().getCenterY()) < 15) {
                                arc.setEndX(vertex.getCircle().getCenterX());
                                arc.setEndY(vertex.getCircle().getCenterY());
                                arc.setEnd(vertex);
                                vertex.addArc(arc);

                            }
                        }
                    }


                }
            };
}



