/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

import com.jfoenix.controls.JFXTextField;

import Utilities.TrafficLightDetector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 *
 * @author MSSM
 */
public class MainStageController implements Initializable{
	private TrafficLightDetector trafficLightDetector = new TrafficLightDetector();
	private FileChooser fileChooser = new FileChooser();
	
    @FXML private ImageView withBlurTrafficLightImage, withoutBlurTrafficLightImage;
    @FXML private JFXTextField greenCascadePath, redCascadePath, yellowCascadePath;

    @FXML
    private void loadImage(){
        fileChooser.setTitle("Choisissez une photo...");
                   
        File file = fileChooser.showOpenDialog(this.withBlurTrafficLightImage.getScene().getWindow());

        if(file != null) {
            Mat mat = Imgcodecs.imread(file.getAbsolutePath());
            Mat bluredMat = this.trafficLightDetector.blurIt(mat);
            
            this.performDetection(mat);
            this.performDetection(bluredMat);
            
            this.updateImageView(mat, this.withoutBlurTrafficLightImage);
            this.updateImageView(bluredMat, this.withBlurTrafficLightImage);            
        }
    }
    
    @FXML
    void loadGreenCascade(ActionEvent event) {
		fileChooser.setTitle("Fichier GreenCascade.xml...");
			        
		File file = fileChooser.showOpenDialog(this.withBlurTrafficLightImage.getScene().getWindow());
		
		if(file != null) {
			this.greenCascadePath.setText(file.getAbsolutePath());
			this.trafficLightDetector.loadGreenCascadeFile(file.getAbsolutePath());
		}
    }
    
    @FXML
    void loadRedCascade(ActionEvent event) {
		fileChooser.setTitle("Fichier RedCascade.xml...");
			        
		File file = fileChooser.showOpenDialog(this.withBlurTrafficLightImage.getScene().getWindow());
		
		if(file != null) {
			this.redCascadePath.setText(file.getAbsolutePath());
			this.trafficLightDetector.loadRedCascadeFile(file.getAbsolutePath());
		}
    }

    @FXML
    void loadYellowCascade(ActionEvent event) {
		fileChooser.setTitle("Fichier YellowCascade.xml...");
        
		File file = fileChooser.showOpenDialog(this.withBlurTrafficLightImage.getScene().getWindow());

		if(file != null) {
			this.yellowCascadePath.setText(file.getAbsolutePath());
			this.trafficLightDetector.loadYellowCascadeFile(file.getAbsolutePath());
		}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    private void performDetection(Mat mat) {
    	MatOfRect greenSignals = this.trafficLightDetector.detectGreenSignals(mat);
        MatOfRect redSignals = this.trafficLightDetector.detectRedSignals(mat);
        MatOfRect yellowSignals = this.trafficLightDetector.detectYellowSignals(mat);
        
        this.trafficLightDetector.drawRectangles(mat, greenSignals, new Scalar(0, 255, 0));
        this.trafficLightDetector.drawRectangles(mat, redSignals, new Scalar(0, 0, 255));
        this.trafficLightDetector.drawRectangles(mat, yellowSignals, new Scalar(0, 255, 255));
    }
    
    private void updateImageView(Mat image, ImageView imageView) {
    	MatOfByte matByte = new MatOfByte();
        
        Imgcodecs.imencode(".png", image, matByte);
        
        Image encodedImage = new Image(new ByteArrayInputStream(matByte.toArray()));
        
        imageView.setImage(encodedImage);
    }
}
