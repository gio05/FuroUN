//
// Created by juan on 17/02/17.
//

#include <opencv2/opencv.hpp>
#include <iostream>
#include "openVideo.h"

using namespace cv;

int main(int argc, char** argv){
    VideoCapture cap;
    if(argc == 1) {
        cap.open("rtsp://192.168.0.168:8555/PSIA/Streaming/channels/0?videoCodecType=MJPEG");
    }
    else
        cap.open(argv[1]);
    if ( !cap.isOpened() ){
        std::cerr << "Couldn't open the capture." << std::endl;
        return -1;
    }
    else{
        std::cout << "Good to go" << std::endl;
        openStreamVideo(cap);
    }
}
