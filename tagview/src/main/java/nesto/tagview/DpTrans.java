package nesto.tagview;

import android.content.Context;

/**
 * Created on 2016/11/4.
 * By nesto
 */

class DpTrans {
    static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
