package com.example.surya.insignia2k16.events_main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

/**
 * Created by surya on 25-06-2016.
 */
public class CardAnimate {
    public static void animater(Events_Adapter.MyViewHolder holder, boolean b) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(holder.itemView,"translationY", b ? 300 : -300 ,0);
        animator.setDuration(300);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator);
        set.start();



    }
}
