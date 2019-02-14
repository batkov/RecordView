package com.devlomi.record_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by Devlomi on 13/12/2017.
 */

public class RecordButton extends AppCompatImageView implements View.OnTouchListener, View.OnClickListener {

    private ScaleAnim scaleAnim;
    private RecordView recordView;
    private LockView lockView;
    private boolean listenForRecord = true;
    private OnRecordClickListener onRecordClickListener;

    private int initialY = 0;
    private int initialX = 0;
    private static final int THRESHOLD = 10;
    Boolean lockedHorizontal = null;


    public void setRecordView(RecordView recordView) {
        this.recordView = recordView;
    }

    public RecordButton(Context context) {
        super(context);
        init(context, null);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);


    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecordButton);

            int imageResource = typedArray.getResourceId(R.styleable.RecordButton_mic_icon, -1);


            if (imageResource != -1) {
                setTheImageResource(imageResource);
            }

            typedArray.recycle();
        }


        scaleAnim = new ScaleAnim(this);


        this.setOnTouchListener(this);
        this.setOnClickListener(this);


    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setClip(this);
    }

    public void setClip(View v) {
        if (v.getParent() == null) {
            return;
        }

        if (v instanceof ViewGroup) {
            ((ViewGroup) v).setClipChildren(false);
            ((ViewGroup) v).setClipToPadding(false);
        }

        if (v.getParent() instanceof View) {
            setClip((View) v.getParent());
        }
    }


    private void setTheImageResource(int imageResource) {
        Drawable image = AppCompatResources.getDrawable(getContext(), imageResource);
        setImageDrawable(image);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isListenForRecord()) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    initialX = (int) event.getRawX();
                    initialY = (int) event.getRawY();
                    if (recordView != null) {
                        recordView.onActionDown((RecordButton) v, event);
                    }
                    if (lockView != null) {
                        lockView.onActionDown((RecordButton) v, event);
                    }
                    break;


                case MotionEvent.ACTION_MOVE:
                    int theX = (int) event.getRawX();
                    int theY = (int) event.getRawY();
                    int y = Math.min(theY - initialY, 0);
                    int x = Math.min(theX - initialX, 0);
                    Log.d("RecordButton", "initialX: " + initialX + " initialY: " + initialY);
                    Log.d("RecordButton", "theX: " + theX + " theY: " + theY);
                    Log.d("RecordButton", "x: " + x + " y: " + y);
                    boolean threshold = Math.abs(y) > THRESHOLD || Math.abs(x) > THRESHOLD;
                    if (!threshold) {
                        Log.d("RecordButton", "threshold drop");
                        break;
                    }
                    if (lockedHorizontal == null) {
                        lockedHorizontal = Math.abs(y) < Math.abs(x);
                        Log.d("RecordButton", "lockedHorizontal set:" + lockedHorizontal);
                    }
                    if (lockedHorizontal && x >= 0) {
                        lockedHorizontal = null;
                        Log.d("RecordButton", "drop lock, x >= 0");
                        break;
                    }
                    if (!lockedHorizontal && y >= 0) {
                        lockedHorizontal = null;
                        Log.d("RecordButton", "drop lock, y >= 0");
                        break;
                    }
                    if (recordView != null && lockedHorizontal) {
                        Log.d("RecordButton", "send to record view");
                        recordView.onActionMove((RecordButton) v, event);
                    }
                    if (lockView != null && !lockedHorizontal) {
                        Log.d("RecordButton", "send to lock view");
                        lockView.onActionMove((RecordButton) v, event);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    lockedHorizontal = null;
                    if (recordView != null) {
                        recordView.onActionUp((RecordButton) v);
                    }
                    if (lockView != null) {
                        lockView.onActionUp((RecordButton) v);
                    }
                    break;

            }

        }
        return isListenForRecord();


    }


    protected void startScale() {
        scaleAnim.start();
    }

    protected void stopScale() {
        scaleAnim.stop();
    }

    public void setListenForRecord(boolean listenForRecord) {
        this.listenForRecord = listenForRecord;
    }

    public boolean isListenForRecord() {
        return listenForRecord;
    }

    public void setOnRecordClickListener(OnRecordClickListener onRecordClickListener) {
        this.onRecordClickListener = onRecordClickListener;
    }


    @Override
    public void onClick(View v) {
        if (onRecordClickListener != null)
            onRecordClickListener.onClick(v);
    }

    public void setLockView(LockView lockView) {
        this.lockView = lockView;
    }
}
