package common.pianters;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import common.views.LockableScrollView;
import me.rds.angrydictionary.R;

/**
 * TODO: document your custom view class.
 */
public final class ResizableView extends View implements View.OnTouchListener {


    private int minWidth=10;
    private int minHeight=10;

    private int xDelta;
    private int yDelta;

    private int mCurrentWidth;
    private int mCurrentHeight;

    protected LockableScrollView mScrollViewContainer;

    private int boundsColor=getResources().getColor(R.color.red);

    public ResizableView(Context context) {
        super(context);
        getSize();
    }

    public ResizableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getSize();
    }

    public ResizableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getSize();
    }


    private void getSize(){
        this.setOnTouchListener(this);
    }

    public void setScrollViewContainer(LockableScrollView view){
        mScrollViewContainer=view;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        setParentActionInTouch(event.getAction() & MotionEvent.ACTION_MASK);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) ResizableView.this.getLayoutParams();
                xDelta = X - lParams.leftMargin;
                yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ResizableView.this.getLayoutParams();
                layoutParams.leftMargin = X - xDelta;
                layoutParams.topMargin = Y - yDelta;
                ResizableView.this.setLayoutParams(layoutParams);
                break;
        }
        ResizableView.this.invalidate();
        return true;
    }



    private void setParentActionInTouch(int action){
        switch(action){
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                  setParentScrolling(true);
                break;
            case  MotionEvent.ACTION_POINTER_DOWN:
            case  MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                setParentScrolling(false);
                break;
        }
    }

    private void setParentScrolling(boolean enable){
        if(mScrollViewContainer!=null)
            mScrollViewContainer.setScrollingEnabled(enable);
    }


}
