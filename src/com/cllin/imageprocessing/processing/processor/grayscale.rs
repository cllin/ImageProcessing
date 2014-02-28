#pragma version(1)
#pragma rs java_package_name(com.cllin.imageprocessing.processing.processor)

#define MSG_TAG "GrayScaleFromRenderScript"

void root(const uchar4 *v_in, uchar4 *v_out) {
	float4 f4 = rsUnpackColor8888(*v_in);
	
	float average = (f4.r + f4.g + f4.b) / 3;
	float3 output = {average, average, average};
	*v_out = rsPackColorTo8888(output);
}

void init(){
	rsDebug("Called init", rsUptimeMillis());
}