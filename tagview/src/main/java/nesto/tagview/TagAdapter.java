package nesto.tagview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created on 2016/11/3.
 * By nesto
 */

class TagAdapter extends RecyclerView.Adapter<TagAdapter.Holder> {

    private LayoutInflater inflater;
    private List<Tag> items;
    private OnTagClickListener clickListener = null;
    private OnTagLongClickListener longClickListener = null;
    private Context context;
    private Integer defaultBackground;
    private Integer defaultTextColor;
    private Integer textSize;
    private Integer margin;
    private Integer divider;
    private Integer paddingLeft;
    private Integer paddingRight;
    private Integer height;
    private Integer radius;
    private Integer backgroundDrawable;

    TagAdapter(Context context, List<Tag> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    void setClickListener(OnTagClickListener clickListener) {
        this.clickListener = clickListener;
    }

    void setLongClickListener(OnTagLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tag_item, parent, false);
        Holder holder = new Holder(view);
        holder.textView = (TextView) view.findViewById(R.id.textView);
        return holder;
    }

    @SuppressWarnings("deprecation") @Override
    public void onBindViewHolder(Holder holder, int position) {
        Tag tag = items.get(position);
        holder.textView.setText(tag.tag);
        holder.textView.setOnClickListener(v -> {
            if (clickListener != null) clickListener.tagClicked(tag.tag);
        });

        holder.textView.setOnLongClickListener(v -> {
            if (longClickListener != null) longClickListener.tagLongClicked(tag.tag);
            return true;
        });

        Integer textColor = tag.textColor;
        if (textColor != null) holder.textView.setTextColor(textColor);
        else if (defaultTextColor != null) holder.textView.setTextColor(defaultTextColor);

        Integer background = tag.backgroundColor;
        if (background != null) holder.textView.setBackgroundDrawable(getBackground(background));
        else if (defaultBackground != null) {
            holder.textView.setBackgroundDrawable(getBackground(defaultBackground));
        }

        if (textSize != null) holder.textView.setTextSize(textSize);


        if (paddingLeft != null) {
            holder.textView.setPadding(paddingLeft, 0, paddingRight, 0);
        }

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                holder.textView.getLayoutParams();

        if (margin != null) {
            params.leftMargin = margin;
            params.rightMargin = margin;
        }
        if (divider != null) {
            params.topMargin = divider / 2;
            params.bottomMargin = divider / 2;
        }
        if (height != null) {
            params.height = height;
        }
    }

    @Override public int getItemCount() {
        return items.size();
    }

    void setBackground(int color) {
        defaultBackground = color;
        notifyDataSetChanged();
    }

    void setBackgroundDrawable(int backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
        notifyDataSetChanged();
    }

    void setTextColor(int color) {
        defaultTextColor = color;
        notifyDataSetChanged();
    }

    void setMargin(int margin) {
        this.margin = margin;
        notifyDataSetChanged();
    }

    // min padding left 8dp
    // ugly thing here
    // min padding right 6dp, a bit smaller than left to avoid a wired thing that the length of
    // the text get from the text paint is a bit smaller than it's real length
    void setPadding(int padding) {
        this.paddingLeft = Math.max(DpTrans.dp2px(context, 8), padding);
        this.paddingRight = Math.max(DpTrans.dp2px(context, 6), padding - 2);
        notifyDataSetChanged();
    }

    @SuppressWarnings("deprecation") private Drawable getBackground(@ColorInt int color) {
        GradientDrawable drawable = (GradientDrawable) context.getResources()
                .getDrawable(R.drawable.corner_gray_lighter);
        drawable.setColorFilter(color, PorterDuff.Mode.SRC);
        if (radius != null) drawable.setCornerRadius(radius);
        return drawable;
    }

    void setTextSize(int dpValue) {
        textSize = dpValue;
        notifyDataSetChanged();
    }

    void setHeight(int height) {
        this.height = height;
        notifyDataSetChanged();
    }

    void setRadius(int radius) {
        this.radius = radius;
        notifyDataSetChanged();
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView textView;

        Holder(View itemView) {
            super(itemView);
        }
    }
}