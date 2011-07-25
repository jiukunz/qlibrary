
package org.bangbang.song.andorid.widget.coverflow;

import org.bangbang.song.andorid.common.debug.Log;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

/**
 * <p>
 * Pseudo CoverFlow impled by
 * 
 * <pre>
 * 1. give different {@link android.view.animation.Transformation} to child view;
 * 2. set spacing to negative;
 * 3. adjust child drawing order;
 * </pre>
 * <p>
 * XXX if spacing is too small, we can NOT select item accurately.
 * <p>
 * TODO efficiency is a problem at some poorly equipped devices.
 * 
 * @author bysong@tudou.com
 */
public class PseudoCoverFlow extends Gallery {
    private static final int DEFAULT_SPACING = -240;// (WINDTH / 2) - 1
    private static final String TAG = PseudoCoverFlow.class.getSimpleName();
    private static final boolean LOG = false;
    private static final boolean DEBUG_LOCATE = true;

    private int[] mDrawOrder = new int[] {};

    private boolean FPS = false;
    private int mFrames;
    private int mTotalFrames;
    private long mCurrentTime;
    private long mElapseTime;
    private long mTickTime;
    private long mFPS;
    private long mMaxFPS;
    private long mMinFPS = Long.MAX_VALUE;
    private String mFPSText;
    private boolean mFirstDraw = true;
    private long mFirstDrawTime;
    private Paint mFPSPaint;

    public PseudoCoverFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    public PseudoCoverFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    public PseudoCoverFlow(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    void init() {

        // boolean drawingCache = isDrawingCacheEnabled();
        // Log.d(TAG, "drawingCacheEnabled: " + drawingCache);
        // setDrawingCacheEnabled(true);

        mFPSPaint = new Paint();
        mFPSPaint.setColor(Color.RED);

        setStaticTransformationsEnabled(true);
//        setChildrenDrawingOrderEnabled(true);
        setSpacing(DEFAULT_SPACING);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // TODO Auto-generated method stub
        return false
        // && super.onFling(e1, e2, velocityX, velocityY)
        ;
    }

    @Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
    	if (DEBUG_LOCATE) {
    		Log.d(TAG, "onScroll(). e1: + " + e1 + "\te2: + " + e2 
    						+ "distanceX: " + distanceX
    						+ "distanceY: " + distanceY);
    		logPosition();
    	}
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

    private void logPosition() {
		// TODO Auto-generated method stub
		int center = (getWidth() - getLeftPaddingOffset() - getRightPaddingOffset()) / 2 + getLeftPaddingOffset();
		final int COUNT = getChildCount();
		View child = null;
		int childLeft = -1;
		int childRight = -1;
		int cloestIndex = -1;
		int cloestDistance = Integer.MAX_VALUE;
		
		Log.d(TAG, "         center: " + center);
		for (int i = COUNT - 1; i >= 0; i --) {
			child = getChildAt(i);
			childLeft = child.getLeft();
			childRight = child.getRight();
			int minDistance = Math.min(Math.abs(center - childLeft), Math.abs(center - childLeft));
			if (minDistance < cloestDistance){
				cloestDistance = minDistance;
				cloestIndex = i;
			}
			
			Log.d(TAG, i + " left: " + childLeft
						+ "\tright: " + childRight
						+ "\tcloestDistance: " + cloestDistance
						);
		}
		Log.d(TAG, "cloestDistance index: " + cloestIndex);
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
    	if (DEBUG_LOCATE) {
    		Log.d(TAG, "onShowPress(). e: " + e);
    	}
		super.onShowPress(e);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		if (DEBUG_LOCATE) {
			Log.d(TAG, "onDown(). e: " + e);
		}
		return super.onDown(e);
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		if (DEBUG_LOCATE) {
			Log.d(TAG, "onLongPress(). e: " + e);
		}
		super.onLongPress(e);
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
    	if (DEBUG_LOCATE) {
    		Log.d(TAG, "onSingleTapup(). e: " + e);
    	}
		return super.onSingleTapUp(e);
	}

	protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        drawFPS(canvas);
    }

    private void drawFPS(Canvas canvas) {
        if (FPS) {
            mFrames += 1;
            mTotalFrames += 1;
            mCurrentTime = System.currentTimeMillis();
            mElapseTime = mCurrentTime - mTickTime;
            if (mElapseTime > 1000) {
                mFPS = mFrames * 1000 / mElapseTime;
                if (mFPS > mMaxFPS) {
                    mMaxFPS = mFPS;
                }
                if (mFPS < mMinFPS && !mFirstDraw) {
                    mMinFPS = mFPS;
                }

                mFPSText = "frames: " + mFrames + "  elapse time: "
                        + mElapseTime + "  FPS: " + mFPS +
                        "  max FPS: " + mMaxFPS + "  min FPS: " + mMinFPS
                        + "  AVG FPS: " + (mTotalFrames * 1000 / (mCurrentTime - mFirstDrawTime));

                mFrames = 0;
                mTickTime = mCurrentTime;
            }
            if (mFirstDraw) {
                mFirstDraw = false;
                mFirstDrawTime = mCurrentTime;
            }
            canvas.drawText(mFPSText, 10, 10, mFPSPaint);

            invalidate();
        }
    }

    /**
     * Maps a point to a position in the list.
     * 
     * @param x X in local coordinate
     * @param y Y in local coordinate
     * @return The position of the item which contains the specified point, or
     *         {@link #INVALID_POSITION} if the point does not intersect an
     *         item.
     */
    public int pointToPosition(int x, int y) {
        Rect frame = new Rect();

        int previousDrawOrder = -Integer.MAX_VALUE;
        int drawOrder = 0;
        int position = INVALID_POSITION;
        final int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    drawOrder = mDrawOrder[i];
                    if (LOG) {
                        Log.d(TAG, "previous order: " + previousDrawOrder);
                        Log.d(TAG, " order: " + drawOrder);
                        Log.d(TAG, "positon: " + position);
                    }
                    if (drawOrder > previousDrawOrder) {
                        position = getFirstVisiblePosition() + i;
                        previousDrawOrder = drawOrder;
                    }

                    if (LOG) {
                        Log.d(TAG, "previous order: " + previousDrawOrder);
                        Log.d(TAG, " order: " + drawOrder);
                        Log.d(TAG, "positon: " + position);
                    }
                }
            }
        }

        return position;
    }

    protected boolean getChildStaticTransformation(View child, Transformation t) {
        final int centerOfCoverFlow = getCenterOfView(this);
        final int childCount = getChildCount();
        final int indexOfCenterView = getIndexOfCenterView(childCount);
        final int indexOfChild = indexOfChild(child);

        final int xDelt = centerOfCoverFlow - (child.getLeft() + child.getWidth() / 2);
        final int indexDelt = indexOfCenterView - indexOfChild;

        final int position = indexOfChild + getFirstVisiblePosition();

        ((CoverFlowAdapter) getAdapter()).computeTransfomtion((CoverFlowItemView) child, t,
                position, xDelt, (xDelt >= 0 ? 1 : -1) * centerOfCoverFlow, indexDelt);
        return true;
    }

    /**
     * Get the Centre of the View.
     * 
     * @return The centre of the given view.
     */
    private static int getCenterOfView(final View view) {
        return (view.getWidth() - view.getPaddingLeft() - view.getPaddingRight()) / 2
                + view.getPaddingLeft();
    }

    public boolean addStatesFromChildrenX() {
        // TODO Auto-generated method stub
        return super.addStatesFromChildren() && false;
    }

    protected int getChildDrawingOrder(int childCount, int i) {
        if (childCount != mDrawOrder.length) {
            mDrawOrder = new int[childCount];
        }

        int order = i;

        int centerIndex = childCount / 2;
        centerIndex = getIndexOfCenterView(childCount);
        if (i < centerIndex) {
            order = i;
        } else if (i == childCount - 1) {
            order = centerIndex;
        } else {
            order = centerIndex + childCount - i - 1;
        }

        mDrawOrder[order] = i;

        return order;
    }

    /**
     * let a + b = CONSTANT, if 
     * a = b, then a*b will result
     * a biggest value.
     * 
     * @param childCount
     * @return
     */
    private int getIndexOfCenterView(int childCount) {
        int index = -1;
        View childV = null;
        int multi = Integer.MIN_VALUE;
        int previousMult = multi;
        int center = getCenterOfView(this);
        for (int i = 0; i < childCount; i++) {
            childV = getChildAt(i);
            multi = -1 * (center - childV.getLeft()) * (center - childV.getRight());
            if (multi > previousMult) {
                previousMult = multi;
                index = i;
            }
        }

        return index;
    }

}
