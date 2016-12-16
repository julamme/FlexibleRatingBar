package juhanilammi.com.flexibleratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Flexible rating bar with adjustable point number. Supports custom drawables
 * @author Juhani Lammi
 */

public class FlexibleRatingBar extends LinearLayout implements View.OnClickListener {
    private static final String TAG = FlexibleRatingBar.class.getSimpleName();
    private static final int MODIFIER_FULL = 1;
    private static final double MODIFIER_HALF = 0.25;
    public static final int MODE_FULL = 10;
    public static final int MODE_HALVES = 20;
    private List<ImageView> mRatingPoints;
    private static final int DEFAULT_NUMBER_OF_POINTS = 5;
    private int mNumberOfPoints;
    private Drawable mFullPoint;
    private Drawable mHalfPoint;
    private Drawable mEmptyPoint;
    private static Drawable mDefaultFullPoint;
    private static Drawable mDefaultHalfPoint;
    private static Drawable mDefaultEmptyPoint;
    private int mode;
    private float mRating;
    private boolean hasCustomDrawables = false;

    public FlexibleRatingBar(Context context) {
        super(context);
        this.setOrientation(HORIZONTAL);
        mRatingPoints = new ArrayList<>();
        initializeDefaultDrawables(context);

    }


    public int getMode() {
        return mode;
    }

    public FlexibleRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRatingPoints = new ArrayList<>();
        initializeDefaultDrawables(context);

    }

    /**
     * Create default drawables for the view
     *
     * @param context Context
     */
    private void initializeDefaultDrawables(Context context) {
        mDefaultEmptyPoint = ContextCompat.getDrawable(context, R.drawable.ic_star_border_black_24dp);
        mDefaultFullPoint = ContextCompat.getDrawable(context, R.drawable.ic_star_black_24dp);
        mDefaultHalfPoint = ContextCompat.getDrawable(context, R.drawable.ic_star_half_black_24dp);
    }

    /**
     * Create containers for points
     *
     * @param context Context
     */
    public void initializePoints(Context context) {
        for (int i = 0; i < mNumberOfPoints; i++) {
            ImageView tempImage = new ImageView(context);
            tempImage.setId(i);
            tempImage.setOnClickListener(this);
            addView(tempImage);
            mRatingPoints.add(tempImage);
        }
    }

    /**
     *
     * @return Current rating
     */
    public float getRating(){
        return mRating;
    }
    /**
     *
     * @param numberOfPoints Number of points used
     */
    public void setNumberOfPoints(int numberOfPoints) {
        mNumberOfPoints = numberOfPoints;
    }

    /**
     * Sets custom drawables to be displayed instead of google material stars.
     * Caller of this method is responsible of resizing the drawables as needed
     *
     * @param fullPoint  Drawable to be shown when a full point is to be shown
     * @param halfPoint  Drawable to be shown when a half point is to be shown
     * @param emptyPoint Drawable to be shown when an empty point is to be shown
     */
    public void setPointDrawables(@Nullable Drawable fullPoint, @Nullable Drawable halfPoint, @Nullable Drawable emptyPoint) {
        mFullPoint = fullPoint;
        mHalfPoint = halfPoint;
        mEmptyPoint = emptyPoint;
        this.mode = mode;
        hasCustomDrawables = true;
    }


    /**
     * Sets correct drawables based on the given rating. Can be set to display wholes and halves.
     *
     * @param rating Rating to be shown
     * @param mode   Sets the rating to be displayed as whole numbers or halves.
     */
    public void setRating(final float rating, int mode) {
        mRating = rating;
        this.mode = mode;
        long iPart = (long) rating;
        float fPart = rating - iPart;
        Log.d(TAG, "setRating: ipart " + iPart + " " + fPart);
        if (hasCustomDrawables) {
            if (iPart >= mNumberOfPoints) {
                for (ImageView view : mRatingPoints) {
                    view.setImageDrawable(mFullPoint);
                }
            } else {
                if (mode == MODE_FULL) {
                    for (int i = 0; i < iPart; i++) {
                        mRatingPoints.get(i).setImageDrawable(mFullPoint);
                    }
                    if (fPart < 0.5) {
                        mRatingPoints.get((int) iPart).setImageDrawable(mEmptyPoint);
                    } else {
                        mRatingPoints.get((int) iPart).setImageDrawable(mFullPoint);
                    }
                    for (int i = (int) (iPart + 1); i < mNumberOfPoints; i++) {
                        mRatingPoints.get(i).setImageDrawable(mEmptyPoint);
                    }
                } else {
                    if (rating < mNumberOfPoints) {
                        for (int i = 0; i < iPart; i++) {
                            mRatingPoints.get(i).setImageDrawable(mFullPoint);
                        }
                        if (fPart < 0.25) {
                            mRatingPoints.get((int) iPart).setImageDrawable(mEmptyPoint);
                        } else if (fPart < 0.75) {
                            mRatingPoints.get((int) iPart).setImageDrawable(mHalfPoint);
                        }
                        for (int i = (int) iPart + 1; i < mNumberOfPoints; i++) {
                            mRatingPoints.get(i).setImageDrawable(mEmptyPoint);
                        }
                    }
                }
            }

        } else {

            if (iPart >= mNumberOfPoints) {
                for (ImageView view : mRatingPoints) {
                    view.setImageDrawable(mDefaultFullPoint);
                }
            } else {
                if (mode == MODE_FULL) {
                    for (int i = 0; i < iPart; i++) {
                        mRatingPoints.get(i).setImageDrawable(mDefaultFullPoint);
                    }
                    if (fPart < 0.5) {
                        mRatingPoints.get((int) iPart).setImageDrawable(mDefaultEmptyPoint);
                    } else {
                        mRatingPoints.get((int) iPart).setImageDrawable(mDefaultFullPoint);
                    }
                    for (int i = (int) (iPart + 1); i < mNumberOfPoints; i++) {
                        mRatingPoints.get(i).setImageDrawable(mDefaultEmptyPoint);
                    }

                } else {
                    if (rating < mNumberOfPoints) {
                        for (int i = 0; i < iPart; i++) {
                            mRatingPoints.get(i).setImageDrawable(mDefaultFullPoint);
                        }
                        if (fPart < 0.25) {
                            mRatingPoints.get((int) iPart).setImageDrawable(mDefaultEmptyPoint);
                        } else if (fPart < 0.75) {
                            mRatingPoints.get((int) iPart).setImageDrawable(mDefaultHalfPoint);
                        }
                        for (int i = (int) iPart + 1; i < mNumberOfPoints; i++) {
                            mRatingPoints.get(i).setImageDrawable(mDefaultEmptyPoint);
                        }
                    }
                }
            }
        }
        invalidate();
        requestLayout();
    }

    /**
     * Currently does not support half mode in selection!
     *
     * @param v Clicked ImageView
     */
    @Override
    public void onClick(View v) {

        setRating(v.getId() + 1, MODE_FULL);
    }
}

