
package org.bangbang.song.android.viewanatomization;

import org.bangbang.song.android.common.debug.Log;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

public class AnatomizeView extends TextView {

    private String TAG = AnatomizeView.class.getSimpleName();

    public AnatomizeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AnatomizeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnatomizeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onSaveInstanceState()
     */
    @Override
    public Parcelable onSaveInstanceState() {
        Log.d(TAG, "onSaveInstanceState().");
        return super.onSaveInstanceState();
    }

    /*
     * (non-Javadoc)
     * @see
     * android.widget.TextView#onRestoreInstanceState(android.os.Parcelable)
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        Log.d(TAG, "onRestoreInstanceState(), state: " + state);
        super.onRestoreInstanceState(state);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onPreDraw()
     */
    @Override
    public boolean onPreDraw() {
        Log.d(TAG, "onPreDraw().");
        return super.onPreDraw();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onDetachedFromWindow()
     */
    @Override
    protected void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow().");
        super.onDetachedFromWindow();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw().");
        super.onDraw(canvas);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown(). keyCode:  " + keyCode + "\tevent: " + event);
        return super.onKeyDown(keyCode, event);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onKeyMultiple(int, int,
     * android.view.KeyEvent)
     */
    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        Log.d(TAG, "onKeyMultiple(). keyCode:  " + keyCode + "\trepeatCount: " + repeatCount
                + "\tevent: " + event);
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onKeyUp(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyUp(). keyCode:  " + keyCode + "\tevent: " + event);
        return super.onKeyUp(keyCode, event);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure(). widthMeasureSpec: " + widthMeasureSpec + "\theightMeaseurSpec: "
                + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onSelectionChanged(int, int)
     */
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        Log.d(TAG, "onSelectionChanged(). selStart:  " + selStart + "\tselEnd: " + selEnd);
        super.onSelectionChanged(selStart, selEnd);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onStartTemporaryDetach()
     */
    @Override
    public void onStartTemporaryDetach() {
        Log.d(TAG, "onStartTemporaryDetach()");
        super.onStartTemporaryDetach();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onFinishTemporaryDetach()
     */
    @Override
    public void onFinishTemporaryDetach() {
        Log.d(TAG, "onFinishTemporaryDetach()");
        super.onFinishTemporaryDetach();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onFocusChanged(boolean, int,
     * android.graphics.Rect)
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        Log.d(TAG, "onFocusChanged(). focused: " + focused + "\tdirection: " + direction
                + "\tpreviouslyFocusedRect: " + previouslyFocusedRect);
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onWindowFocusChanged(boolean)
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Log.d(TAG, "onWindowFocusChanged(). hasWIndowFocus: " + hasWindowFocus);
        super.onWindowFocusChanged(hasWindowFocus);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent()");
        return super.onTouchEvent(event);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.TextView#onKeyShortcut(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyShortcut(). keyCode: " + keyCode + "\tevent: " + event);
        return super.onKeyShortcut(keyCode, event);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onWindowVisibilityChanged(int)
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        Log.d(TAG, "onWindowVisibilityChanged(). visibility: " + visibility);
        super.onWindowVisibilityChanged(visibility);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onKeyPreIme(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyPreIme(). keyCode: " + keyCode + "\tevent: " + event);
        return super.onKeyPreIme(keyCode, event);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onKeyLongPress(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyLongPress(). keyCode: " + keyCode + "\tevent: " + event);
        return super.onKeyLongPress(keyCode, event);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onScrollChanged(int, int, int, int)
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.d(TAG, "onScrollChanged(). l: " + l + "\tt: " + t + "\toldl: " + oldl + "\toldt: "
                + oldt);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onSizeChanged(int, int, int, int)
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged(). w: " + w + "\th: " + h + "\toldw: " + oldw + "\toldh: " + oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onLayout(boolean, int, int, int, int)
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout(). changed: " + changed + "\tleft: " + left + "\ttop: " + top
                + "\tright: " + right + "\tbottom: " + bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onFinishInflate()
     */
    @Override
    protected void onFinishInflate() {
        Log.d(TAG, "onFinishInflate()");
        super.onFinishInflate();
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow()");
        super.onAttachedToWindow();
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onSetAlpha(int)
     */
    @Override
    protected boolean onSetAlpha(int alpha) {
        Log.d(TAG, "onSetAlpha()");
        return super.onSetAlpha(alpha);
    }

}
