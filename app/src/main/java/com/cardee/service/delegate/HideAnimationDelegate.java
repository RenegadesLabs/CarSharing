package com.cardee.service.delegate;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public class HideAnimationDelegate {

    private static final int ANIMATION_DURATION = 250;
    private static final float DISTANCE_RATIO = 1.5f;

    private ValueAnimator hideAnimator;
    private ValueAnimator showAnimator;
    private boolean hidden;


    public HideAnimationDelegate(@NonNull final View target) {
        target.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                init(view);
            }
        });
    }

    private void init(final View target) {
        if (hideAnimator != null && showAnimator != null) {
            return;
        }
        hideAnimator = ValueAnimator
                .ofFloat(target.getX(), target.getX() - target.getWidth() * DISTANCE_RATIO);
        hideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) valueAnimator.getAnimatedValue();
                target.setX(value);
            }
        });
        hideAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                hidden = true;
                target.setAlpha(0);
            }
        });
        hideAnimator.setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateInterpolator());
        showAnimator = ValueAnimator
                .ofFloat(target.getX() - target.getWidth() * DISTANCE_RATIO, target.getX());
        showAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) valueAnimator.getAnimatedValue();
                target.setX(value);
            }
        });
        showAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                target.setAlpha(1);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                hidden = false;
            }
        });
        showAnimator.setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateInterpolator());
    }

    public void hide() {
        if (hideAnimator.isRunning()) {
            return;
        }
        if (showAnimator.isRunning()) {
            showAnimator.cancel();
        }
        hideAnimator.start();
    }

    public void show() {
        if (showAnimator.isRunning()) {
            return;
        }
        if (hideAnimator.isRunning()) {
            hideAnimator.cancel();
        }
        showAnimator.start();
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isAnimating() {
        return hideAnimator.isRunning() || showAnimator.isRunning();
    }
}
