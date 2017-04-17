import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ConnectToCamera {

    static void openStreamVideo( VideoCapture cap ){
        JFrame f = new JFrame("Camera");
        Mat frame = new Mat();
        MatOfByte matOfByte = new MatOfByte();
        BufferedImage bufImage;
        for(;;) {
            cap.read(frame);
            if ( frame.empty() )
                break;
            Imgcodecs.imencode(".jpg", frame, matOfByte);
            byte[] byteArray = matOfByte.toArray();
            try {
                InputStream in = new ByteArrayInputStream(byteArray);
                bufImage = ImageIO.read(in);
                f.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
                f.pack();
                f.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture cap = new VideoCapture(0);
        //cap.open("rtsp://192.168.0.168:8555/PSIA/Streaming/channels/0?videoCodecType=MJPEG");
        if ( !cap.isOpened() ){
            System.out.println("Couldn't open the capture.");
            return;
        }
        else{
            System.out.println("Good to go");
            openStreamVideo(cap);
        }
    }
}
