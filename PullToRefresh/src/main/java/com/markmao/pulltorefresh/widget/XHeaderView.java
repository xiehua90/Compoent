package com.markmao.pulltorefresh.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.markmao.pulltorefresh.R;

/**
 * The header view for {@link com.markmao.pulltorefresh.widget.XListView} and
 * {@link com.markmao.pulltorefresh.widget.XScrollView}
 *
 * @author markmjw
 * @date 2013-10-08
 */
public class XHeaderView extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

    private final int ROTATE_ANIM_DURATION = 180;

    private FrameLayout mContainer;

    private ImageView mArrowImageView;

    private ProgressBar mProgressBar;

    private TextView mHintTextView;

    private LinearLayout mHeaderContent;

    private int mState = STATE_NORMAL;

    private int mHeaderHeight;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;


    private boolean mIsFirst;

    public XHeaderView(Context context) {
        super(context);
        initView(context);
    }

    public XHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // Initial set header view height 0
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        mContainer = (FrameLayout) FrameLayout.inflate(context, R.layout.vw_header, null);
        addView(mContainer, lp);
        mHeaderContent = (LinearLayout) findViewById(R.id.header_content);
        mArrowImageView = (ImageView) findViewById(R.id.header_arrow);
        mHintTextView = (TextView) findViewById(R.id.header_hint_text);
        mProgressBar = (ProgressBar) findViewById(R.id.header_progressbar);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);

        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
        mHeaderHeight = (int) getResources().getDimension(R.dimen.header_height);
    }

    public void setState(int state) {
        if (state == mState && mIsFirst) {
            mIsFirst = true;
            return;
        }

        if (state == STATE_REFRESHING) {
            // show progress
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            // show arrow image
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_READY) {
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }

                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }

                mHintTextView.setText(R.string.header_hint_refresh_normal);
                break;

            case STATE_READY:
                if (mState != STATE_READY) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mHintTextView.setText(R.string.header_hint_refresh_ready);
                }
                break;

            case STATE_REFRESHING:
                mHintTextView.setText(R.string.header_hint_refresh_loading);
                break;

            default:
                break;
        }

        mState = state;
    }

    /**
     * Set the header view visible height.
     *
     * @param height
     */
    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    /**
     * Get the header view visible height.
     *
     * @return
     */
    public int getVisibleHeight() {
        return mContainer.getHeight();
    }

    public void setContentVisible(int visible) {
        mHeaderContent.setVisibility(visible);
    }

    public int getFixedHeight() {
        return mHeaderHeight;
    }

    public void setFixedHeight(int height) {
        mHeaderHeight = height;
    }

}