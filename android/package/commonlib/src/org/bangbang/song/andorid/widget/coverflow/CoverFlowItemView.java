
package org.bangbang.song.andorid.widget.coverflow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * block any invalidate() & layout() request whenever setImageBitmap() which
 * will trigger a invalidate() & layout() request by default.
 * 
 * @author bysong@tudou.com
 */
public class CoverFlowItemView extends ImageView {
    // FIXME magic number
    private static final int WIDTH = 320;
    private static final int HEIGHT = 190;
    
    private boolean mBlockLayout = false;
    private boolean mBlockInvalidate = false;
    private boolean mCached = false;
    private boolean mBorder = false;

    public boolean isBorderSet() {
        return mBorder;
    }

    public void setBorder(boolean border) {
        this.mBorder = border;
    }

    public CoverFlowItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CoverFlowItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoverFlowItemView(Context context) {
        super(context);
    }

    public int getOriginWidth() {
        return WIDTH;
    }

    public int getOriginHeight() {
        return HEIGHT;
    }

    public boolean hasCached() {
        return mCached;
    }

    public void setCached(boolean cached) {
        mCached = cached;
    }

    public void blockLayout(boolean blockLayout) {
        mBlockLayout = blockLayout;
    }

    public void blockInvalidate(boolean blockInvalidate) {
        mBlockInvalidate = blockInvalidate;
    }

    @Override
    public void requestLayout() {
        if (!mBlockLayout) {
            super.requestLayout();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));
        super.onDraw(canvas);
    }

    @Override
    public void invalidate() {
        if (!mBlockInvalidate) {
            super.invalidate();
        }
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (!mBlockInvalidate) {
            super.invalidateDrawable(dr);
        }
    }

    @Override
    public void invalidate(int l, int t, int r, int b) {
        if (!mBlockInvalidate) {
            super.invalidate(l, t, r, b);
        }
    }

    @Override
    public void invalidate(Rect dirty) {
        if (!mBlockInvalidate) {
            super.invalidate(dirty);
        }
    }

}
