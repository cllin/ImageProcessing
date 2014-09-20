#pragma version(1)
#pragma rs java_package_name(com.cllin.imageprocessing.processing.processor)

#define MSG_TAG "SobelDetectingFromRenderScript"

#include "rs_cl.rsh"

rs_allocation gIn;
rs_allocation gOut;
rs_script gScript;

float *gOutPixels;

int count = 0;
int mImageWidth;
int mImageHeight;

void root(const int *v_in, int *v_out) {
	if (count != 0) return;
 	count++;
	
	for (int i = 1; i < mImageHeight; i++) {
		for (int j = 1; j < mImageWidth; j++) {
			int pix = v_in[i * mImageWidth + j];
			int Past_X = v_in[i * mImageWidth + (j - 1)];
			int Past_Y = v_in[(i - 1) * mImageWidth + j];
			
			int dev_X = (((Past_X >> 16) & 0xff - (pix >> 16) & 0xff) + ((Past_X >> 8) & 0xff - (pix >> 8) & 0xff) + (Past_X & 0xff - pix & 0xff)) / 3;
			int dev_Y = (((Past_Y >> 16) & 0xff - (pix >> 16) & 0xff) + ((Past_Y >> 8) & 0xff - (pix >> 8) & 0xff) + (Past_Y & 0xff - pix & 0xff)) / 3;
			
			gOutPixels[i * mImageWidth + j] = sqrt((float) dev_X * dev_X + (float) dev_Y * dev_Y);
		}
	}
	
	int threshold = 31;
	rsDebug("threshold = ", threshold);
	
	for (int i = 1; i < mImageHeight; i++) {
		for (int j = 1; j < mImageWidth; j++) {
			if (gOutPixels[i * mImageWidth + j] > threshold) {
				gOutPixels[i * mImageWidth + j] = (0 << 24) | (255 << 16) | (255 << 8) | 255;
			} else {
				gOutPixels[i * mImageWidth + j] = 0 & 0xFF;
			}
		}
	}
}

void init() {
	rsDebug("Called init", rsUptimeMillis());
}

void compute() {
	rsForEach(gScript, gIn, gOut);
}