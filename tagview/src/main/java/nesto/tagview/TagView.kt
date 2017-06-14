package nesto.tagview

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import android.support.v7.widget.GridLayoutManager
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.tag_view.view.*

/**
 * Created on 2016/11/3.
 * By nesto
 */

class TagView : FrameLayout {

    companion object {
        private val ADDITION_MARGIN = 2
        private val ADDITION_PADDING = 16

        private val DEFAULT_TEXT_SIZE = 12
    }

    private lateinit var tags: MutableList<Tag>
    private lateinit var paint: TextPaint
    private lateinit var adapter: TagAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var spanWidth: Int = 0
    private var clickListener: ((Int, String) -> Unit)? = null
    private var longClickListener: ((Int, String) -> Unit)? = null
    private var padding = ADDITION_PADDING
    private var margin = ADDITION_MARGIN
    // 2 * margin + 2 * padding
    private var additionTotal: Int = 0

    private var textSize = DEFAULT_TEXT_SIZE

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.tag_view, null)
        addView(view)
        layoutManager = GridLayoutManager(context, 1)
        tags = mutableListOf<Tag>()
        adapter = TagAdapter(context, tags)
        recyclerView.adapter = adapter
        additionTotal = calculateAddition()
        initTextPaint()
    }

    private fun calculateAddition(): Int {
        return dp2px(context!!, (2 * margin + 2 * padding).toFloat())
    }

    private fun initTextPaint() {
        paint = TextPaint()
        paint.textSize = dp2px(context!!, textSize.toFloat()).toFloat()
    }

    private fun setTagSize() {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val text = tags[position].tag
                val size = paint.measureText(text).toInt() + additionTotal
                return Math.min(size, spanWidth)
            }
        }
    }

    fun textSize(@IntRange(from = 1) dpValue: Int): TagView {
        textSize = dpValue
        paint.textSize = dp2px(context, textSize.toFloat()).toFloat()
        adapter.setTextSize(dpValue)
        setTagSize()
        return this
    }

    fun textColor(@ColorInt color: Int): TagView {
        adapter.setTextColor(color)
        return this
    }

    fun backgroundColor(@ColorInt color: Int): TagView {
        adapter.setBackground(color)
        return this
    }

    fun backgroundDrawable(@DrawableRes drawable: Int): TagView {
        adapter.setBackgroundDrawable(drawable)
        return this
    }

    fun padding(@IntRange(from = 0) padding: Int): TagView {
        this.padding = Math.max(padding, 8)
        additionTotal = calculateAddition()
        setTagSize()
        adapter.setPadding(dp2px(context, padding.toFloat()))
        return this
    }

    fun margin(@IntRange(from = 0) margin: Int): TagView {
        this.margin = margin
        additionTotal = calculateAddition()
        setTagSize()
        adapter.setMargin(dp2px(context, margin.toFloat()))
        return this
    }

    fun itemHeight(@IntRange(from = 1) height: Int): TagView {
        adapter.setHeight(dp2px(context, height.toFloat()))
        return this
    }

    fun corner(@IntRange(from = 0) radius: Int): TagView {
        adapter.setRadius(dp2px(context!!, radius.toFloat()))
        return this
    }


    fun dividerHeight(@IntRange(from = 0) height: Int): TagView {
        adapter.setDivider(dp2px(context!!, height.toFloat()))
        return this
    }

    fun addStringTags(tagCollection: Collection<String>): TagView {
        val start = tags.size
        for (s in tagCollection) {
            tags.add(Tag(s, null, null))
        }
        adapter.notifyItemRangeInserted(start, tagCollection.size)
        return this
    }

    fun addStringTag(tag: String, position: Int? = null): TagView {
        if (position == null) {
            tags.add(Tag(tag, null, null))
        } else {
            tags.add(position, Tag(tag, null, null))
        }
        adapter.notifyItemInserted(tags.size - 1)
        return this
    }

    fun addTags(tagCollection: Collection<Tag>): TagView {
        val start = tags.size
        tags.addAll(tagCollection)
        adapter.notifyItemRangeInserted(start, tagCollection.size)
        return this
    }

    fun addTag(tag: Tag, position: Int? = null): TagView {
        if (position == null) {
            tags.add(tag)
        } else {
            tags.add(position, tag)
        }
        adapter.notifyItemInserted(tags.size - 1)
        return this
    }

    fun setOnTagClickListener(listener: (Int, String) -> Unit): TagView {
        clickListener = listener
        adapter.setClickListener(listener)
        return this
    }

    fun setOnTagLongClickListener(listener: (Int, String) -> Unit): TagView {
        longClickListener = listener
        adapter.setLongClickListener(listener)
        return this
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        spanWidth = recyclerView.measuredWidth - recyclerView.paddingLeft
        -recyclerView.paddingRight
        // make sure the width is at least 1
        spanWidth = Math.max(1, spanWidth)
        layoutManager.spanCount = spanWidth
        setTagSize()
        recyclerView.layoutManager = layoutManager
    }

    fun removeAllTags(): TagView {
        val count = tags.size
        tags.clear()
        adapter.notifyItemRangeRemoved(0, count)
        return this
    }

    fun removeTag(position: Int): TagView {
        tags.removeAt(position)
        adapter.notifyItemRemoved(position)
        return this
    }
}
