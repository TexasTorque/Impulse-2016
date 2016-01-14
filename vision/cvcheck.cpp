#include <opencv2\opencv.hpp>
#include <opencv2\core\cuda.hpp>

using namespace cv::cuda;

int main() {
	//version check
	printf("version ->\n");
	printf("\tOpenCV Version %s\n", CV_VERSION);

	//cuda check
	printf("cuda ->\n");
	int cudaCount = getCudaEnabledDeviceCount();
	if (cudaCount == 0) {
		printf("\tno CUDA enabled GPUs on this machine\n");
		return 0;
	}
	if (TargetArchs::builtWith(FeatureSet::FEATURE_SET_COMPUTE_10)) {
		printf("\tbuilt with c10");
	}
	if (TargetArchs::builtWith(FeatureSet::FEATURE_SET_COMPUTE_11)) {
		printf("\tbuilt with c11");
	}
	if (TargetArchs::builtWith(FeatureSet::FEATURE_SET_COMPUTE_12)) {
		printf("\tbuilt with c12");
	}
	if (TargetArchs::builtWith(FeatureSet::FEATURE_SET_COMPUTE_13)) {
		printf("\tbuilt with c13");
	}
	if (TargetArchs::builtWith(FeatureSet::FEATURE_SET_COMPUTE_20)) {
		printf("\tbuilt with c20");
	}
	if (TargetArchs::builtWith(FeatureSet::FEATURE_SET_COMPUTE_21)) {
		printf("\tbuilt with c21");
	}
	if (TargetArchs::builtWith(FeatureSet::FEATURE_SET_COMPUTE_30)) {
		printf("\tbuilt with c30");
	}
	if (TargetArchs::builtWith(FeatureSet::FEATURE_SET_COMPUTE_32)) {
		printf("\tbuilt with c32");
	}
	if (TargetArchs::builtWith(FeatureSet::FEATURE_SET_COMPUTE_35)) {
		printf("\tbuilt with c35");
	}
	if (TargetArchs::builtWith(FeatureSet::FEATURE_SET_COMPUTE_50)) {
		printf("\tbuilt with c50");
	}

	printf("cuda device ->\n");
	printf("\tname: %s\n", DeviceInfo::DeviceInfo().name());
	
	return 0;
}