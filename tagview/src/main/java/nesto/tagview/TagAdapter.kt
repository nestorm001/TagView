package nesto.tagview

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.annotation.ColorInt
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created on 2016/11/3.
 * By nesto
 */

internal class TagAdapter(private val context: Context, private val items: List<Tag>) : RecyclerView.Adapter<TagAdapter.Holder>() {

    private var clickListener: ((Int, String) -> Unit)? = null
    private var longClickListener: ((Int, String) -> Unit)? = null
    private var defaultBackground: Int? = null
    private var defaultTextColor: Int? = null
    private var textSize: Int? = null
    private var margin: Int? = null
    private var divider: Int? = null
    private var paddingLeft: Int? = null
    private var paddingRight: Int? = null
    private var height: Int? = null
    private var radius: Int? = null
    private var backgroundDrawable: Int? = null

    fun setClickListener(clickListener: (Int, String) -> Unit) {
        this.clickListener = clickListener
    }

    fun setLongClickListener(longClickListener: (Int, String) -> Unit) {
        this.longClickListener = longClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.tag_item, parent, false)
        val holder = Holder(view)
        holder.textView = view.findViewById(R.id.textView) as TextView
        return holder
    }

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val tag = items[position]
        holder.textView.text = tag.tag
        holder.textView.setOnClickListener { clickListener?.invoke(position, tag.tag) }

        holder.textView.setOnLongClickListener { _ ->
            longClickListener?.invoke(position, tag.tag)
            true
        }

        val textColor = tag.textColor
        if (textColor != null) holder.textView.setTextColor(textColor)
        else if (defaultTextColor != null) holder.textView.setTextColor(defaultTextColor!!)

        val background = tag.backgroundColor
        if (background != null)
            holder.textView.setBackgroundDrawable(getBackground(background))
        else if (defaultBackground != null) {
            holder.textView.setBackgroundDrawable(getBackground(defaultBackground!!))
        }

        textSize?.let { holder.textView.textSize = it.toFloat() }

        if (paddingLeft != null && paddingRight != null) {
            holder.textView.setPadding(paddingLeft!!, 0, paddingRight!!, 0)
        }

        val params = holder.textView.layoutParams as LinearLayout.LayoutParams
        margin?.let {
            params.leftMargin = it
            params.rightMargin = it
        }
        divider?.let {
            params.topMargin = it / 2
            params.bottomMargin = it / 2
        }
        height?.let { params.height = it }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setBackground(color: Int) {
        defaultBackground = color
        notifyDataSetChanged()
    }

    fun setBackgroundDrawable(backgroundDrawable: Int) {
        this.backgroundDrawable = backgroundDrawable
        notifyDataSetChanged()
    }

    fun setTextColor(color: Int) {
        defaultTextColor = color
        notifyDataSetChanged()
    }

    fun setMargin(margin: Int) {
        this.margin = margin
        notifyDataSetChanged()
    }

    // min padding left 8dp
    // ugly thing here
    // min padding right 6dp, a bit smaller than left to avoid a wired thing that the length of
    // the text get from the text paint is a bit smaller than it's real length
    fun setPadding(padding: Int) {
        this.paddingLeft = Math.max(dp2px(context, 8f), padding)
        this.paddingRight = Math.max(dp2px(context, 6f), padding - 2)
        notifyDataSetChanged()
    }

    @Suppress("DEPRECATION")
    private fun getBackground(@ColorInt color: Int): Drawable {
        val drawable = context.resources
                .getDrawable(R.drawable.corner_gray_lighter) as GradientDrawable
        drawable.setColorFilter(color, PorterDuff.Mode.SRC)
        if (radius != null) drawable.cornerRadius = radius!!.toFloat()
        return drawable
    }

    fun setTextSize(dpValue: Int) {
        textSize = dpValue
        notifyDataSetChanged()
    }

    fun setHeight(height: Int) {
        this.height = height
        notifyDataSetChanged()
    }

    fun setRadius(radius: Int) {
        this.radius = radius
        notifyDataSetChanged()
    }

    fun setDivider(divider: Int) {
        this.divider = divider
    }

    internal inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var textView: TextView
    }
}