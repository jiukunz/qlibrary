
package org.bangbang.song.andorid.widget.coverflow;

import java.util.HashMap;
import java.util.Map;

import org.bangbang.song.android.common.debug.Log;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;

public abstract class CoverFlowAdapter extends BaseAdapter {
    private static final boolean DEBUG = false;
    private Context mContext;
    private Map<Integer, Bitmap> mCache = new HashMap<Integer, Bitmap>();
    private String TAG = CoverFlowAdapter.class.getSimpleName();
    private Camera mCamera = new Camera();
    private static final float MAX_ROT_ANGLE = 60;
    private static final boolean CACHE = false;

    public CoverFlowAdapter(Context context) {
        super();
        mContext = context;
    }

    public Bitmap extractCacheItem(int position) {
        return mCache.get(position);
    }

    public void setCacheItem(int position, Bitmap bm) {
        mCache.put(position, bm);
    }

    public Bitmap applyTransformation2Bitmap(Bitmap bitmap, Transformation t,
            float rotationAngel) {
        Bitmap transed = bitmap;
        transed = SpecularUtil.transformImageBitmap(bitmap, t, rotationAngel);
        return transed;
    }

    /**
     * get original bitmap at specified <code>position</code>.
     * 
     * @param position
     * @return
     */
    public abstract Bitmap getBitmap(int position);

    public void computeTransfomtion(CoverFlowItemView child, Transformation t,
            int position, int xDelt, int maxXDelt, int indexDelt) {
        if (DEBUG) {
            Log.d(TAG, "position: " + position + "\txDelt: " + xDelt
                    + "\tmaxXDelt: " + maxXDelt + "\tindexDelt: " + indexDelt);
        }

        t.clear();
        t.setTransformationType(Transformation.TYPE_BOTH);

        mCamera.save();
        final Matrix imageMatrix = t.getMatrix();

        int height = child.getOriginHeight();
        int width = child.getOriginWidth();

        // height = child.getHeight();
        // width = child.getWidth();

        // gen rotation angle.
        float computeRotationAngle = 0;
        final float fraction = (xDelt >= 0 ? 1.0f : -1.0f) * xDelt / maxXDelt;
        computeRotationAngle = fraction * 180;
        // maybe a Interpolator is better.
        // computeRotationAngle = new
        // LinearInterpolator().getInterpolation(fraction);
        float realRotationAngle = computeRotationAngle;
        if (Math.abs(computeRotationAngle) > MAX_ROT_ANGLE) {
            realRotationAngle = (computeRotationAngle > 0 ? 1.0f : -1.0f)
                    * MAX_ROT_ANGLE;
        }
        // if (Math.abs(indexDelt) > 1){
        // realRotationAngle = 0.f;
        // }

        // gen alpha
        float alpha = 0.f;
        alpha = 1.f - xDelt * 1.0f / maxXDelt;
        // gen Z transformation
        float tZ = 0f;
        tZ =
                // ( xDelt >= 0 ? 1.0f : 1.0f ) *
                120.f * xDelt / maxXDelt;
        // tZ += 100;

        // real transformation
        // mCamera.translate(0.0f, 0.0f, tZ);
        if (xDelt == 0) {
            // mCamera.translate(0.0f, 0.0f, -150);
        }
        // Log.d(TAG, "realRotationAngle: " + realRotationAngle);
        mCamera.rotateY(realRotationAngle);

        mCamera.getMatrix(imageMatrix);
        // t.setAlpha(alpha);
        imageMatrix.preTranslate(-(width / 2.0f), -(height / 2.0f));
        imageMatrix.postTranslate((width / 2.0f), (height / 2.0f));

        mCamera.restore();

        if (CACHE
        // && false
        ) {
            doCacheStuff(child, t, position, computeRotationAngle,
                    realRotationAngle, indexDelt);
        }
    }

    private void doCacheStuff(View child, Transformation t, int position,
            float computeRotationAngle, float realRotationAngle, int indexDelt) {
        Bitmap bm = extractCacheItem(position);
        CoverFlowItemView itemView = (CoverFlowItemView) child;
        boolean cached = itemView.hasCached();

        if (Math.abs(computeRotationAngle) >= MAX_ROT_ANGLE // check-cache
        // ||
        // Math.abs(indexDelt) > 1
        ) {

            if (null == bm) { // no cache yet.
                bm = getBitmap(position);
                bm = applyTransformation2Bitmap(bm, t, realRotationAngle);
                setCacheItem(position, bm);
            } else {

            }

            if (!cached) {
                itemView.setCached(true);
                itemView.blockLayout(true);
                itemView.blockInvalidate(true);
                itemView.setImageBitmap(bm);
                itemView.blockLayout(false);
                itemView.blockInvalidate(false);
            }

            // do NOT transform any more.
            t.clear();
            t.setTransformationType(Transformation.TYPE_IDENTITY);
        } else { // un-cache
            if (null != bm) {
                setCacheItem(position, null);
            }

            if (cached) {
                bm = getBitmap(position);
                itemView.setCached(false);
                itemView.blockLayout(true);
                itemView.blockInvalidate(true);
                itemView.setImageBitmap(bm);
                itemView.blockLayout(false);
                itemView.blockInvalidate(false);
            }
        }
    }
}
