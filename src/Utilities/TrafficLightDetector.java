/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.util.Arrays;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;

/**
 *
 * @author MSSM
 */
public class TrafficLightDetector {
    private CascadeClassifier greenSystem = new CascadeClassifier();
    private CascadeClassifier yellowSystem = new CascadeClassifier();
    private CascadeClassifier redSystem = new CascadeClassifier();
    
    public Mat blurIt(Mat image) {
    	Mat result = new Mat();
    	Imgproc.medianBlur(image, result, 3);
    	return result;
    }
    
    public void loadGreenCascadeFile(String greenSystemCascadeXmlFile) {
    	this.greenSystem.load(greenSystemCascadeXmlFile);
    }
    
    public void loadRedCascadeFile(String redSystemCascadeXmlFile) {
    	this.redSystem.load(redSystemCascadeXmlFile);
    }
    
    public void loadYellowCascadeFile(String yellowSystemCascadeXmlFile) {
    	this.yellowSystem.load(yellowSystemCascadeXmlFile);
    }
    
    public MatOfRect detectGreenSignals(Mat image){
        MatOfRect result = new MatOfRect();        
        this.greenSystem.detectMultiScale(image, result, 1.1, 3, 0|CASCADE_SCALE_IMAGE, new Size(1, 1), new Size(24, 24));
        return result;
    }
    
    public MatOfRect detectRedSignals(Mat image) {
        MatOfRect result = new MatOfRect();        
        this.redSystem.detectMultiScale(image, result, 1.1, 3, 0|CASCADE_SCALE_IMAGE, new Size(1, 1), new Size(24, 24));
        return result;
    }
    
    public MatOfRect detectYellowSignals(Mat image) {
        MatOfRect result = new MatOfRect();        
        this.yellowSystem.detectMultiScale(image, result, 1.1, 3, 0|CASCADE_SCALE_IMAGE, new Size(1, 1), new Size(24, 24));
        return result;
    }
    
    public void drawRectangles(Mat image, MatOfRect detectedObjects, Scalar color){
        for(int i = 0; i < detectedObjects.size().height; i++){            
            double[] detectedObjectInformations = detectedObjects.get(i, 0);
            
            Point p1 = new Point(new double[]{
                detectedObjectInformations[0],
                detectedObjectInformations[1]
            });
            
            Point p2 = new Point(new double[]{
                p1.x + detectedObjectInformations[2],
                p1.y + detectedObjectInformations[3]
            });
            
            Imgproc.rectangle(image, p1, p2, color, 2);
        }
    }
}
