#include <opencv2\imgproc.hpp>
#include <opencv2\imgproc\imgproc_c.h>
#include <opencv2\highgui.hpp>

using namespace std;
using namespace cv;

int main() {
	VideoCapture camera(0);

	Mat frame;
	vector<Vec4i> hierarchy;
	vector<vector<Point>> contours;

	namedWindow("window", CV_WINDOW_AUTOSIZE);

	while (true) {
		camera >> frame;

		inRange(frame, Scalar(0, 100, 0), Scalar(255, 255, 255), frame);

		findContours(frame, contours, hierarchy, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);

		printf("contours: %i\n", contours.size());
		printf("hierarchy: %i\n", hierarchy.size());

		imshow("window", frame);
		if (waitKey(30) >= 0) break;
	}
	return 0;
}