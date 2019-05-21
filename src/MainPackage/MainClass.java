package MainPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author MSSM
 */
public class MainClass extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        String os = "";
        
        if(System.getProperty("os.arch").indexOf("64") != -1) os = "64bits";
        else if(System.getProperty("os.arch").indexOf("x86") != -1) os = "32bits";
        
        System.out.println("os: " + os);
        System.loadLibrary("lib/opencv_java_" + os);
        
        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/FXMLFiles/MainStage.fxml"));
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
