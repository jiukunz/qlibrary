package com.tudou.android.CoverFlowTest;

import org.bangbang.song.andorid.widget.coverflow.CoverFlowAdapter;
import org.bangbang.song.andorid.widget.coverflow.CoverFlowItemView;
import org.bangbang.song.andorid.widget.coverflow.SpecularUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


public class ImageAdapter extends CoverFlowAdapter {
	private static final String TAG = ImageAdapter.class.getSimpleName();

	private Context mContext;

	
	
	private final Integer[] mImageIds = { R.drawable.p1
			, R.drawable.p2,
			R.drawable.p3
			, R.drawable.p4, R.drawable.p5
			, R.drawable.p6,
			R.drawable.p7, R.drawable.p8, R.drawable.p1, R.drawable.p2,
			R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
			R.drawable.p7, R.drawable.p8
			};

	private Camera mCamera = new Camera();

	public ImageAdapter(Context c) {
		super(c);
		mContext = c;
	}

	public int getCount() {
		return mImageIds.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(Demo.TAG, "getView(). pos: " + position + "\tconvertView: "
				+ convertView + "\tparent: " + parent);
		CoverFlowItemView imageView;
		if (convertView == null) {
			convertView = new CoverFlowItemView(mContext);

			imageView = (CoverFlowItemView) convertView;
			imageView.setScaleType(CoverFlowItemView.ScaleType.FIT_XY);
		} else {
			imageView = (CoverFlowItemView) convertView;
		}

		// imageView.setImageResource(mImageIds[position]);
		imageView.setImageBitmap(getBitmap(position));
		return imageView;

//		return super.getPositonedView(position);
	}

	@Override
	public Bitmap getBitmap(int position) {
		// TODO Auto-generated method stub
		Bitmap bm = ((BitmapDrawable) mContext.getResources().getDrawable(
				mImageIds[position])).getBitmap();

		return SpecularUtil.createReflectedImage(bm);
	}
}