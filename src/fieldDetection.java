//import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class fieldDetection {

	public Mat getHoughPTransform(Mat image, double rho, double theta, int threshold) {
	    Mat result = image.clone();
	    Mat lines = new Mat();
	    Imgproc.HoughLinesP(image, lines, rho, theta, threshold);

	    for (int i = 0; i < lines.cols(); i++) {
	        double[] val = lines.get(0, i);
	        Imgproc.line(result, new Point(val[0], val[1]), new Point(val[2], val[3]), new Scalar(0, 0, 255), 2);
            //Core.line(result, new Point(val[0], val[1]), new Point(val[2], val[3]), new Scalar(0, 0, 255), 2);
	    }
	    return result;
	}
}
