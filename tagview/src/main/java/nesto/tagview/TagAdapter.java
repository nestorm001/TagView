package nesto.tagview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
    private OnTagClickListener listener = null;
    private Context context;
    private Integer defaultBackground;
    private Integer defaultTextColor;
    private Integer textSize;
    private Integer margin;

    TagAdapter(Context context, List<Tag> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    public void setListener(OnTagClickListener listener) {
        this.listener = listener;
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
            if (listener != null) listener.tagClicked(tag.tag);
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

        if (margin != null) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                    holder.textView.getLayoutParams();
            params.leftMargin = margin;
            params.rightMargin = margin;
        }
    }

    @Override public int getItemCount() {
        return items.size();
    }

    void setBackground(int color) {
        defaultBackground = color;
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

    @SuppressWarnings("deprecation") private Drawable getBackground(@ColorInt int color) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.corner_gray_lighter);
        drawable.setColorFilter(color, PorterDuff.Mode.SRC);
        return drawable;
    }

    void setTextSize(int dpValue) {
        textSize = dpValue;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView textView;

        Holder(View itemView) {
            super(itemView);
        }
    }
}