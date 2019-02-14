package com.devlomi.record_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

public class RecordLayout extends ConstraintLayout {

    RecordView recordView;
    LockView lockView;
    RecordButton recordButton;

    public RecordLayout(Context context) {
        super(context);
    }

    public RecordLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public RecordLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.record_layout, this);
        recordView = findViewById(R.id.record_view);
        lockView = findViewById(R.id.lock_view);
        recordButton = findViewById(R.id.record_button);
        recordButton.setRecordView(recordView);
        recordButton.setLockView(lockView);
    }

    public boolean isListenForRecord() {
        return recordButton.isListenForRecord();
    }

    public void setListenForRecord(boolean listenForRecord) {
        recordButton.setListenForRecord(listenForRecord);
    }

    public void setOnRecordClickListener(OnRecordClickListener onRecordClickListener) {
        recordButton.setOnRecordClickListener(onRecordClickListener);
    }

    public void setCancelBounds(int cancelBounds) {
        recordView.setCancelBounds(cancelBounds);
    }

    public void setSmallMicColor(int parseColor) {
        recordView.setSmallMicColor(parseColor);
    }

    public void setLessThanSecondAllowed(boolean allowed) {
        recordView.setLessThanSecondAllowed(allowed);
    }

    public void setSlideToCancelText(String slideToCancel) {
        recordView.setSlideToCancelText(slideToCancel);
    }

    public void setCustomSounds(int recordStart, int recordFinished, int errorSound) {
        recordView.setCustomSounds(recordStart, recordFinished, errorSound);
    }

    public void setOnRecordListener(OnRecordListener onRecordListener) {
        recordView.setOnRecordListener(onRecordListener);
    }

    public void setOnBasketAnimationEndListener(OnBasketAnimationEnd onBasketAnimationEnd) {
        recordView.setOnBasketAnimationEndListener(onBasketAnimationEnd);
    }
}
