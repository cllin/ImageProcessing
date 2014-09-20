package com.cllin.imageprocessing.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cllin.imageprocessing.R;
import com.cllin.imageprocessing.activity.exceptions.ProcessorException;
import com.cllin.imageprocessing.processor.ProcessorInterface;
import com.cllin.imageprocessing.processor.ProcessorInterface.ProcessorType;

public class MainActivity extends Activity 
implements OnClickListener, OnCheckedChangeListener {
//  TAG
    private static final String MSG_TAG = "MainActivity";
    
//  VIEW
    private RelativeLayout mRelativeLayout = null;
    private RadioGroup mSelectRadioGroup = null;
    private Button mProcessBtn = null;
    private Button mLastImageBtn = null;
    private Button mNextImageBtn = null;
	
//  IMAGEVIEW
    private int mCurrentImage = 0;
    private ImageView mImageView = null;
    private Bitmap mBitmap = null;
	
//  IMAGES
    private static final int[] IMAGES = {
	R.drawable.bathing_kid, R.drawable.eiffel_tower, R.drawable.energize, 
	R.drawable.hansel_and_gretel, R.drawable.newsweek_1933, 
	R.drawable.ny_building, R.drawable.plus_ultre};
	
//  PROCESSOR
    private ProcessorType mCurrentSelection = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        setView();
        setImageView();
    }
    
    private void setView() {
    	mRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
    	mSelectRadioGroup = (RadioGroup) findViewById(R.id.select_process_radiogroup);
    	mProcessBtn = (Button) findViewById(R.id.process_button);
    	mLastImageBtn = (Button) findViewById(R.id.last_image_button);
        mNextImageBtn = (Button) findViewById(R.id.next_image_button);
        
        mSelectRadioGroup.setOnCheckedChangeListener(this);
        mProcessBtn.setOnClickListener(this);
        mLastImageBtn.setOnClickListener(this);
        mNextImageBtn.setOnClickListener(this);
    }
    
    private void setNewBitmap(Bitmap newBitmap) {
	if (mBitmap != null) mBitmap.recycle();
		
	mBitmap = newBitmap;
    	
    	mImageView.setImageBitmap(mBitmap); 
    	mImageView.invalidate();
    }
    
    private void showNextImage() {
    	mCurrentImage = (mCurrentImage == IMAGES.length - 1)? 0 : mCurrentImage + 1;

    	mBitmap = BitmapFactory.decodeResource(getResources(), IMAGES[mCurrentImage]);             
    	
    	mImageView.setImageBitmap(mBitmap); 
    	mImageView.invalidate();	
    }
    
    private void showLastImage() {
    	mCurrentImage = (mCurrentImage == 0)? IMAGES.length - 1 : mCurrentImage - 1;
    	
    	mBitmap = BitmapFactory.decodeResource(getResources(), IMAGES[mCurrentImage]);             
    	
    	mImageView.setImageBitmap(mBitmap); 
    	mImageView.invalidate();	
    }
    
    private void setImageView() {
        mImageView = new ImageView(MainActivity.this);
        mImageView.setBackgroundColor(Color.TRANSPARENT);
        
	mBitmap = BitmapFactory.decodeResource(getResources(), IMAGES[mCurrentImage]);             

	mImageView.setImageBitmap(mBitmap);
	mImageView.invalidate();
	mRelativeLayout.addView(mImageView);
    }
    
    private void processImage() {
    	Bitmap output = Bitmap.createBitmap(
    		mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());
    	ProcessorInterface processor = ProcessorInterface.getProcessor(getApplicationContext());
		
	if (mCurrentSelection == null) {
	    Toast.makeText(getApplication(), "Please choose a processor", Toast.LENGTH_SHORT).show();
	    return;
	}
		
	Log.d(MSG_TAG, "selection: "+ mCurrentSelection.toString());
		
	try {
	    output = processor.process(mCurrentSelection, mBitmap);
	    setNewBitmap(output);
	} catch (ProcessorException e) {
	    e.printStackTrace();
	    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
	}
    }

    @Override
    public void onClick(View view) {
	switch (view.getId()) {
	case R.id.process_button:
	    processImage();
	    break;
	case R.id.last_image_button:
	    showLastImage();
	    break;
	case R.id.next_image_button:
	    showNextImage();
	    break;
	}
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedItemId) {
	switch (checkedItemId) {
	case R.id.binarization_radiobutton:
	    mCurrentSelection = ProcessorType.BINARIZE;
	    break;
	case R.id.grayscale_radiobutton:
	    mCurrentSelection = ProcessorType.GRAYSCALE;
	    break;
	case R.id.sobel_radiobutton:
	    mCurrentSelection = ProcessorType.SOBEL;
	    break;
	}
	
	Toast.makeText(getApplicationContext(), mCurrentSelection.toString(), Toast.LENGTH_SHORT).show();
    }
}
