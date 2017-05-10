package nesto.tagview

import android.content.Context

/**
 * Created on 2016/11/4.
 * By nesto
 */

fun dp2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}
