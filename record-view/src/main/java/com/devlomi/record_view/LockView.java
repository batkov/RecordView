package com.devlomi.record_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * Created by Devlomi on 24/08/2017.
 */

public class LockView extends FrameLayout {

    public static final int DEFAULT_LOCK_BOUNDS = 20; //8dp
    private ImageView lockImageView, stopImageView, arrow;
    private float initialTouchY, initialY, difY = 0, difRawY = 0;
    private float lockBounds = DEFAULT_LOCK_BOUNDS;
    private Context context;
    private boolean isSwiped;


    public LockView(Context context) {
        super(context);
        this.context = context;
        init(context, null, -1, -1);
    }


    public LockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs, -1, -1);
    }

    public LockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs, defStyleAttr, -1);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        View view = View.inflate(context, R.layout.lock_layout, this);

        setClipChildren(false);

        arrow = findViewById(R.id.arrow);
        lockImageView = findViewById(R.id.lock_image_view);
        stopImageView = findViewById(R.id.stop_image_view);


        hideViews();


        if (attrs != null && defStyleAttr == -1 && defStyleRes == -1) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LockView,
                    defStyleAttr, defStyleRes);


            int slideArrowResource = typedArray.getResourceId(R.styleable.LockView_lock_arrow, -1);
            int lockResource = typedArray.getResourceId(R.styleable.LockView_lock_arrow, -1);
            int unlockResource = typedArray.getResourceId(R.styleable.LockView_unlock_image, -1);
            int stopResource = typedArray.getResourceId(R.styleable.LockView_stop_image, -1);

            if (slideArrowResource != -1) {
                //Drawable slideArrow = AppCompatResources.getDrawable(getContext(), slideArrowResource);
                //arrow.setImageDrawable(slideArrow);
            }
            if (lockResource != -1) {
                //Drawable slideArrow = AppCompatResources.getDrawable(getContext(), slideArrowResource);
                //arrow.setImageDrawable(slideArrow);
            }
            if (unlockResource != -1) {
                //Drawable slideArrow = AppCompatResources.getDrawable(getContext(), slideArrowResource);
                //arrow.setImageDrawable(slideArrow);
            }
            if (stopResource != -1) {
                //Drawable slideArrow = AppCompatResources.getDrawable(getContext(), slideArrowResource);
                //arrow.setImageDrawable(slideArrow);
            }

            typedArray.recycle();
        }


    }


    private void hideViews() {
        setVisibility(GONE);
    }

    private void showViews() {
        setVisibility(VISIBLE);
    }


    protected void onActionDown(RecordButton recordBtn, MotionEvent motionEvent) {
        initialTouchY = motionEvent.getRawY();
        initialY = 0;
        showViews();
        isSwiped = false;

    }


    protected void onActionMove(RecordButton recordBtn, MotionEvent motionEvent) {
        if (!isSwiped) {
            int rawY = (int) motionEvent.getRawY();
            int y = (int) motionEvent.getY();
            int outLocation[] = new int[2];
            recordBtn.getLocationOnScreen(outLocation);
            difY = (rawY - initialTouchY);
            if (initialY == 0) {
                initialY = recordBtn.getY();
            }
            if (difY < 0) {
                recordBtn.animate()
                        .y(initialY + difY)
                        .setDuration(0)
                        .start();


/*
                lockImageView.animate()
                        .y(rawY - difY)
                        .setDuration(0)
                        .start();
                        */
            }
        }
    }

    protected void onActionUp(RecordButton recordBtn) {
        recordBtn.animate()
                .y(0)
                .setDuration(100)
                .start();
        hideViews();
    }


    private void setMarginBottom(int marginBottom, boolean convertToDp) {
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        int margin = marginBottom;
        if (convertToDp) {
            margin = (int) DpUtil.toPixel(marginBottom, context);
        }
        layoutParams.bottomMargin = margin;
    }



    public float getLockBounds() {
        return lockBounds;
    }

    public void setLockBounds(float lockBounds) {
        setLockBounds(lockBounds, true);
    }

    private void setLockBounds(float lockBounds, boolean convertDpToPixel) {
        float bounds = convertDpToPixel ? DpUtil.toPixel(lockBounds, context) : lockBounds;
        this.lockBounds = bounds;
    }

}


