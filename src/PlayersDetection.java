import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
//import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class PlayersDetection {


	public static double[] centroids(double lower[], double upper[], Mat src) {

		Mat destination = src.clone();
		Core.inRange(destination, new Scalar(lower), new Scalar(upper), destination);

		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(destination, contours, new Mat(), 1, 2);

		System.out.println(contours.size());
		MatOfPoint cnt = contours.get(0);

		System.out.println(" CONTORNOS ");
		for (MatOfPoint MoP: contours) {
			System.out.println( MoP );

		}


		Moments M = Imgproc.moments(cnt);
		/*
		int cx_b = (int) (M.get_m10()/M.get_m00());
		int cy_b = (int) (M.get_m01()/M.get_m00());
		*/

		double cx_b =  (M.get_m10()/M.get_m00());
		double cy_b =  (M.get_m01()/M.get_m00());

		System.out.println( "Centroides" );
		System.out.println( cx_b );
		System.out.println( cy_b );

		double ans[] = {cx_b, cy_b};

		return ans;


	}

	public static void getPixelInfo(int row, int col, Mat destination){

		System.out.println( "PIXEL INFO: " + row + " , " + col );
		double[] data = destination.get(row, col);
        data[0] = data[0] / 2;
        data[1] = data[1] / 2;
        data[2] = data[2] / 2;

        System.out.println( data[0] );
        System.out.println( data[1] );
        System.out.println( data[2] );

	}

	public static void run(){

		Mat source = Imgcodecs.imread("C:/Users/Giio19/Desktop/jugador1.png", Imgcodecs.CV_LOAD_IMAGE_COLOR);

		//Mat source = Highgui.imread("C:/Users/Giio19/Desktop/jugador1.png", Highgui.CV_LOAD_IMAGE_COLOR);

		getPixelInfo(25, 75, source);

		Mat destination = new Mat(source.rows(),source.cols(),source.type());
		Imgproc.GaussianBlur(source, destination,new Size(45,45), 0);
		Imgproc.cvtColor(destination, destination, Imgproc.COLOR_BGR2HSV);

		getPixelInfo(25, 25, destination);
		getPixelInfo(25, 75, destination);
		getPixelInfo(75, 25, destination);
		getPixelInfo(75, 75, destination);

		double lower_blue[] = {45.0,100.0,110.0};
		double upper_blue[] = {55.0,130.0,120.0};

		double lower_green[] = {25,0,90.0,80.0};
		double upper_green[] = {69.0,206.0,100.0};

		double cb[] = centroids(lower_blue, upper_blue, destination);
		double cg[] = centroids(lower_green, upper_green, destination);

		double X = (cb[0] + cg[0])/2;
		double Y = (cb[1] + cg[1])/2;
		double THETA = Math.atan2( (cg[1] - cb[1]),(cg[0] - cb[0]) );
		THETA = ( Math.toDegrees(THETA) + 45);

		System.out.println( "DATOS JUGADOR 1\nX: " + X + " Y: " + Y + " THETA: " + THETA );

	}
	public static void main( String[] args ) {
	      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	      run();
	   }
}
