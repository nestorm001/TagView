package nesto.tagview

import android.support.annotation.ColorInt

/**
 * Created on 2016/11/3.
 * By nesto
 */
class Tag {
    val tag: String
    val textColor: Int?
    val backgroundColor: Int?

    constructor(tag: String) {
        this.tag = tag
        this.textColor = null
        this.backgroundColor = null
    }

    constructor(tag: String, @ColorInt textColor: Int?, @ColorInt backgroundColor: Int?) {
        this.tag = tag
        this.textColor = textColor
        this.backgroundColor = backgroundColor
    }
}
