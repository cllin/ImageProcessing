#pragma version(1)
#pragma rs java_package_name(com.cllin.imageprocessing.processing)

#define MSG_TAG "BinarizeFromRenderScript"

const float THRESHOLD = 0.5;

void root(const uchar4 *v_in, uchar4 *v_out) {
	float4 f4 = rsUnpackColor8888(*v_in);
	
	if ((f4.r + f4.g + f4.b) / 3 > THRESHOLD) {
		float3 output = {0, 0, 0};
    	*v_out = rsPackColorTo8888(output);
	} else {
		float3 output = {1.0, 1.0, 1.0};
		*v_out = rsPackColorTo8888(output);
	} 
		
}

void init(){
	rsDebug("Called init", rsUptimeMillis());
}