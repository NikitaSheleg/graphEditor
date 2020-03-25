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

import javafx.scene.control.*;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import javafx.scene.input.*;

import javafx.scene.layout.Pane;

import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;

import javafx.scene.text.Text;

import javafx.scene.transform.Translate;

import javafx.stage.Modality;

import javafx.stage.Stage;


import javax.swing.*;


public class MyControler implements Initializable {

    private final float CIRCLE_RADIUS = 10.0f;

    private Translate translate = new Translate();

    public static int countCircle = 0;

    public Graph graph = new Graph();


    private Pane pane;


    public static Stage stage;


    private double x1, x2 = 0;

    private double y1, y2 = 0;


    double orgSceneX, orgSceneY;

    double orgTranslateX, orgTranslateY;


    @FXML

    private Button penCircle = new Button();


    @FXML

    private MenuItem showMultipleArcBut = new MenuItem();


    @FXML

    private MenuItem newPane = new MenuItem();


    @FXML

    private Button penLine = new Button();


    @FXML

    private Button transform = new Button();


    @FXML

    private Button unorientedArc = new Button();


    @FXML

    private TabPane tabPane = new TabPane();


    @FXML

    private Tab tab = new Tab();


    private List<Pane> panes = new ArrayList<>();

    private ArrayList<Circle> circleArray = new ArrayList<>();

    private ArrayList<Arc> arcArray = new ArrayList<>();

    List<Vertex> vertices = new ArrayList<>();


    @Override

    public void initialize(URL location, ResourceBundle resources) {

        InputStream input =

                getClass().getResourceAsStream("/ch/makery/address/view/circle.png");


        Image image = new Image(input);

        ImageView imageView = new ImageView(image);


        pane = new Pane();

        tabPane.getTabs().get(0).setContent(pane);


        panes.add(pane);


        //Image for button

       /* penCircle.graphicProperty().setValue(imageView);

        penLine.graphicProperty().setValue(imageView);

        transform.graphicProperty().setValue(imageView);

        unorientedArc.graphicProperty().setValue(imageView);*/

        // TODO (don't really need to do anything here).


        System.out.println("Hi");


    }


    public void showMultipleArc(ActionEvent event) {

        graph.showMultipleArc(arcArray);


    }


    public void newPaneAction(ActionEvent event) {

        Tab tab1 = new Tab("New File");

        Label label = new Label("This is newTab ");

        tab1.setContent(label);

        tabPane.getTabs().add(tab1);

        Pane pane = new Pane();

        panes.add(pane);

        tabPane.getTabs().get(tabPane.getTabs().size() - 1).setContent(pane);


    }


    // Ивент нажатия кнопки

    public void penCircleAction(ActionEvent event) {

        MyApplication.scene.setCursor(Cursor.DEFAULT);

        penCircle.setDisable(true);

        penLine.setDisable(false);

        transform.setDisable(false);
        unorientedArc.setDisable(false);

        for (Tab tab1 : tabPane.getTabs()) {

            tab1.getContent().addEventFilter(MouseEvent.MOUSE_CLICKED, drawCircle);

        }

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

        unorientedArc.setDisable(false);

        for (Circle circle : circleArray) {

            circle.setOnMousePressed(null);

            circle.setOnMouseDragged(null);

        }

        graph.showMatrix();

        for (Circle circle : circleArray) {

            circle.addEventFilter(MouseEvent.MOUSE_CLICKED, lineDrawEvent);

        }

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

        unorientedArc.setDisable(false);

        thread.run();


    }


    public void unorientedArcAction(ActionEvent actionEvent) {
        MyApplication.scene.setCursor(Cursor.CROSSHAIR);

        penCircle.setDisable(false);

        penLine.setDisable(false);

        transform.setDisable(false);

        unorientedArc.setDisable(true);

        for (Circle circle : circleArray) {

            circle.setOnMousePressed(null);

            circle.setOnMouseDragged(null);

        }

        graph.showMatrix();

        for (Circle circle : circleArray) {

            circle.addEventFilter(MouseEvent.MOUSE_CLICKED, lineDrawEvent);

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

            if (penCircle.isDisable() && e.getX() < 550 && e.getY() < 400) {

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

                vertex.setTextInPane();

                circle.setAccessibleText("str");


                vertex.setId(countCircle);

                vertex.getText().setX(vertex.getCircle().getCenterX() + 10);

                vertex.getText().setY(vertex.getCircle().getCenterY() + 10);

                graph.addVertex(circleArray);

                for (Pane pane1 : panes) {

                    pane1.getChildren().add(circle);

                }

                // panes.get(1).getChildren().add(circle);

                countCircle++;


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

                                    if (e.getCode() == KeyCode.I) {

                                        Label secondLabel = new Label("Enter name vertex");

                                        TextField textField = new TextField("Enter name");

                                        textField.setMinWidth(120);

                                        Button button1 = new Button("Button with Text");


                                        VBox secondaryLayout = new VBox();

                                        secondaryLayout.getChildren().addAll(secondLabel, textField, button1);


                                        Scene secondScene = new Scene(secondaryLayout, 230, 100);


                                        // New window (Stage)

                                        Stage newWindow = new Stage();

                                        newWindow.setTitle("Enter name");

                                        newWindow.setScene(secondScene);


                                        // Specifies the modality for new window.

                                        newWindow.initModality(Modality.WINDOW_MODAL);


                                        // Specifies the owner Window (parent) for new window

                                        newWindow.initOwner(stage);


                                        // Set position of second window, related to primary window.

                                        newWindow.setX(stage.getX() + 100);

                                        newWindow.setY(stage.getY() + 100);


                                        button1.setOnAction(new EventHandler<ActionEvent>() {

                                            @Override

                                            public void handle(ActionEvent actionEvent) {

                                                vertex.getText().setText(textField.getText());

                                                newWindow.close();

                                            }

                                        });


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

                    circle.setCenterX(t.getX());

                    circle.setCenterY(t.getY());


                    for (Vertex vertex : vertices) {

                        vertex.getText().setX(vertex.getCircle().getCenterX() + 10);

                        vertex.getText().setY(vertex.getCircle().getCenterY() + 10);

                        if (vertex.getCircle() == circle && vertex.getArcs() != null) {

                            for (Arc arc : vertex.getArcs()) {

                                if (arc.getBegin().getCircle() == vertex.getCircle()) {

                                    arc.setStartX(circle.getCenterX());

                                    arc.setStartY(circle.getCenterY());

                                } else if (arc.getEnd().getCircle() == vertex.getCircle()) {

                                    arc.setEndX(circle.getCenterX());

                                    arc.setEndY(circle.getCenterY());

                                }

                                arc.updateArrow();

                            }

                        }


                    }

                }

            };


    EventHandler<MouseEvent> lineDrawEvent = new EventHandler<MouseEvent>() {

        Arc arc = new Arc(0, 0, 0, 0);


        @Override

        public void handle(MouseEvent t) {

            if (penLine.isDisable() || unorientedArc.isDisable()) {

                if (x1 == 0 && y1 == 0) {

                    Circle circle = (Circle) t.getSource();

                    for (Vertex vertex : vertices) {

                        if (vertex.getCircle() == circle) {

                            x1 = t.getX();

                            y1 = t.getY();

                            arc.setBegin(vertex);

                            arc.setStartX(vertex.getCircle().getCenterX());

                            arc.setStartY(vertex.getCircle().getCenterY());

                            vertex.addArc(arc);

                        }

                    }

                } else {

                    if (x2 == 0 && y2 == 0) {

                        x2 = t.getX();

                        y2 = t.getY();

                        Circle circle = (Circle) (t.getSource());

                        for (Vertex vertex : vertices) {

                            if (vertex.getCircle() == circle) {

                                vertex.addArc(arc);

                                arc.setEnd(vertex);

                                arc.setEndX(vertex.getCircle().getCenterX());

                                arc.setEndY(vertex.getCircle().getCenterY());


                                arcArray.add(arc);


                                //dобавил немножко от себя

                                //Изменение цвета

/*



                                if (arc.getBegin().getCircle().getFill() == Color.WHITE) {

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

                                }

*/


                                arc.setStrokeWidth(2);

                                graph.addArc(arc);

                                for (Pane pane1 : panes) {

                                    pane1.getChildren().add(arc);
                                    if (penLine.isDisable()) {
                                        arc.setArrow(pane1);
                                    } else if (unorientedArc.isDisable()) {
                                        arc.setUnorientedArrow(pane1);
                                        arc.updateUnorientedArrow();
                                    }

                                }

                                // panes.get(0).getChildren().add(arc);

                                x1 = 0;

                                x2 = 0;

                                y1 = 0;

                                y2 = 0;
                                arc.updateArrow();
                                //  arc.updateUnorientedArrow();


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

                    if (Math.abs(t.getX() - arc.getStartX()) < 25 && Math.abs(t.getY() - arc.getStartY()) < 25) {

                        arc.setStartX(t.getX());

                        arc.setStartY(t.getY());

                        for (Vertex vertex : vertices) {

                            if (Math.abs(arc.getStartX() - vertex.getCircle().getCenterX()) < 15 && Math.abs(arc.getStartY() - vertex.getCircle().getCenterY()) < 15) {

                                arc.setStartX(vertex.getCircle().getCenterX());

                                arc.setStartY(vertex.getCircle().getCenterY());

                                arc.setBegin(vertex);

                                vertex.addArc(arc);

                            }

                        }

                    } else if (Math.abs(t.getX() - arc.getEndX()) < 25 && Math.abs(t.getY() - arc.getEndY()) < 25) {

                        arc.setEndX(t.getX());

                        arc.setEndY(t.getY());

                        for (Vertex vertex : vertices) {

                            if (Math.abs(arc.getEndX() - vertex.getCircle().getCenterX()) < 15 && Math.abs(arc.getEndY() - vertex.getCircle().getCenterY()) < 15) {

                                arc.setEndX(vertex.getCircle().getCenterX());

                                arc.setEndY(vertex.getCircle().getCenterY());

                                arc.setEnd(vertex);

                                vertex.addArc(arc);


                            }

                        }

                    }


                    arc.updateArrow();

                }

            };


}