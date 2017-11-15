package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.transform.*;
public class Main extends Application {
    private final String Filename = "model/home.3ds";
    private final double MODEL_SCALE_FACTOR = 20;
    private final double SCALE_DELTA = 1.1;
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 700;
    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private double mouseOldX;
    private double mouseOldY;
    private double maxScale;
    private double minScale;
    private Group root;
    @Override
    public void start(Stage stage) throws Exception{
        ModelImporter tdsImporter = new TdsModelImporter();
        tdsImporter.read(getClass().getResource("10.3ds").toExternalForm());
        Node[] tdsMesh = (Node[]) tdsImporter.getImport();
        tdsImporter.close();

        Group object = new GrouptdsMesh);
        root = new Group(object);
        object.setTranslateX(WINDOW_WIDTH/2);
        object.setTranslateY(WINDOW_HEIGHT/2);
        object.setTranslateZ(15000);
        object.setScaleX(MODEL_SCALE_FACTOR);
        object.setScaleY(MODEL_SCALE_FACTOR);
        object.setScaleZ(MODEL_SCALE_FACTOR);
        //object.getTransforms().add(new Rotate(25, Rotate.X_AXIS));
        object.getTransforms().add(new Rotate(80, Rotate.Y_AXIS));
        maxScale = 60.0;
        minScale = 10.0;
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.getTransforms().addAll (rotateX, rotateY, new Translate(0, 0, 0));
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, true);
        scene.setFill(Color.color(179.0/255.0,179.0/255.0,179.0/255.0));
        scene.setCamera(camera);
        object.setOnMousePressed(event -> {
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();
        });
        object.setOnMouseDragged(event -> {
            if(event.isPrimaryButtonDown())
            {
                //double xAngleRotate = object.getRotate() - (-event.getSceneY() + mouseOldY);
                double yAngleRotate = object.getRotate() + (-event.getSceneX() + mouseOldX)/100.0;
                //object.getTransforms().add(new Rotate(xAngleRotate, Rotate.X_AXIS));
                object.getTransforms().add(new Rotate(yAngleRotate, Rotate.Y_AXIS));
                //mouseOldX = event.getSceneX();
                mouseOldY = event.getSceneY();
            }
        });
        PointLight light = new PointLight(Color.color(0.7, 0.7, 0.7));
        light.setTranslateZ(-15000);
        light.setTranslateY(-15000);
        light.setTranslateX(-15000);
        root.getChildren().addAll(light);
        stage.setX(0);
        stage.setY(0);
        stage.setTitle("Java Fx3D 3DS");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
