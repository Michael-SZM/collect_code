//package com.michael.collect_code.spannable;
//
//import android.util.Property;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//final class FireworksSpanGroup {
//    private final float mAlpha;
//    private final ArrayList<MutableForegroundColorSpan> mSpans;
//    private FireworksSpanGroup(float alpha) {
//        mAlpha = alpha;
//        mSpans = new ArrayList<MutableForegroundColorSpan>();
//    }
//    public void addSpan(MutableForegroundColorSpan span) {
//        span.setAlpha((int) (mAlpha * 255));
//        mSpans.add(span);
//    }
//    public void init() {
//        Collections.shuffle(mSpans);
//    }
//    public void setAlpha(float alpha) {
//        int size = mSpans.size();
//        float total = 1.0f * size * alpha;
//        for(int index = 0 ; index < size; index++) {
//            MutableForegroundColorSpan span = mSpans.get(index);
//            if(total >= 1.0f) {
//                span.setAlpha(255);
//                total -= 1.0f;
//            } else {
//                span.setAlpha((int) (total * 255));
//                total = 0.0f;
//            }
//        }
//    }
//    public float getAlpha() { return mAlpha; }
//
//
//    private static final Property<FireworksSpanGroup, Float> FIREWORKS_GROUP_PROGRESS_PROPERTY =
//            new Property<FireworksSpanGroup, Float>(Float.class, "FIREWORKS_GROUP_PROGRESS_PROPERTY") {
//                @Override
//                public void set(FireworksSpanGroup spanGroup, Float value) {
//                    spanGroup.setProgress(value);
//                }
//                @Override
//                public Float get(FireworksSpanGroup spanGroup) {
//                    return spanGroup.getProgress();
//                }
//            };
//
//}