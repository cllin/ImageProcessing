#pragma version(1)
#pragma rs java_package_name(com.cllin.imageprocessing.processing)

#define MSG_TAG "SobelDetectingFromRenderScript"

void root(const uchar4 *v_in, uchar4 *v_out) {
	float4 f4 = rsUnpackColor8888(*v_in);
//	float3 output = {f4.r, f4.g, f4.b};
	
	float3 output = {0, 0, 0};
    *v_out = rsPackColorTo8888(output);
}

void init(){
	rsDebug("Called init", rsUptimeMillis());
}