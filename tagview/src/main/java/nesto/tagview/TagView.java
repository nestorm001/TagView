package nesto.tagview;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created on 2016/11/3.
 * By nesto
 */

public class TagView extends FrameLayout
        implements OnTagClickListener,
        OnTagLongClickListener {
    private RecyclerView recyclerView;
    private List<Tag> tags;
    private TextPaint paint;
    private Context context;
    private TagAdapter adapter;
    private GridLayoutManager layoutManager;
    private int width;
    private OnTagClickListener clickListener;
    private OnTagLongClickListener longClickListener;

    private static final int ADDITION_MARGIN = 2;
    private static final int ADDITION_PADDING = 16;
    private int padding = ADDITION_PADDING;
    private int margin = ADDITION_MARGIN;
    // 2 * margin + 2 * padding
    private Integer additionTotal;

    private static final int DEFAULT_TEXT_SIZE = 12;
    private int textSize = DEFAULT_TEXT_SIZE;

    public TagView(Context context) {
        super(context);
        initView(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        View view = inflate(context, R.layout.tag_view, null);
        addView(view);
        layoutManager = new GridLayoutManager(context, 1);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        tags = new ArrayList<>();
        adapter = new TagAdapter(context, tags);
        recyclerView.setAdapter(adapter);
        additionTotal = calculateAddition();
        initTextPaint();
    }

    private int calculateAddition() {
        return DpTrans.dp2px(context, 2 * margin + 2 * padding);
    }

    private void initTextPaint() {
        paint = new TextPaint();
        paint.setTextSize(DpTrans.dp2px(context, textSize));
    }

    private void setTagSize() {
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                String text = tags.get(position).tag;
                int size = (int) paint.measureText(text) + additionTotal;
                return Math.min(size, width);
            }
        });
    }

    public TagView textSize(@IntRange(from = 1) int dpValue) {
        textSize = dpValue;
        paint.setTextSize(DpTrans.dp2px(context, textSize));
        adapter.setTextSize(dpValue);
        setTagSize();
        return this;
    }

    public TagView textColor(@ColorInt int color) {
        adapter.setTextColor(color);
        return this;
    }

    public TagView backgroundColor(@ColorInt int color) {
        adapter.setBackground(color);
        return this;
    }

    public TagView backgroundDrawable(@DrawableRes int drawable) {
        adapter.setBackgroundDrawable(drawable);
        return this;
    }

    public TagView padding(@IntRange(from = 0) int padding) {
        this.padding = Math.max(padding, 8);
        additionTotal = calculateAddition();
        setTagSize();
        adapter.setPadding(DpTrans.dp2px(context, padding));
        return this;
    }

    public TagView margin(@IntRange(from = 0) int margin) {
        this.margin = margin;
        additionTotal = calculateAddition();
        setTagSize();
        adapter.setMargin(DpTrans.dp2px(context, margin));
        return this;
    }

    public TagView itemHeight(@IntRange(from = 1) int height) {
        adapter.setHeight(DpTrans.dp2px(context, height));
        return this;
    }

    public TagView corner(@IntRange(from = 0) int radius) {
        adapter.setRadius(DpTrans.dp2px(context, radius));
        return this;
    }

    public TagView dividerHeight(@IntRange(from = 0) int height) {
        adapter.setDivider(DpTrans.dp2px(context, height));
        return this;
    }


    public TagView addStringTags(Collection<String> tagCollection) {
        int start = tags.size();
        for (String s : tagCollection) {
            tags.add(new Tag(s, null, null));
        }
        adapter.notifyItemRangeInserted(start, tagCollection.size());
        return this;
    }

    public TagView addStringTag(String tag) {
        tags.add(new Tag(tag, null, null));
        adapter.notifyItemInserted(tags.size() - 1);
        return this;
    }

    public TagView addTags(Collection<Tag> tagCollection) {
        int start = tags.size();
        tags.addAll(tagCollection);
        adapter.notifyItemRangeInserted(start, tagCollection.size());
        return this;
    }

    public TagView addTag(Tag tag) {
        tags.add(tag);
        adapter.notifyItemInserted(tags.size() - 1);
        return this;
    }

    public TagView setListener(OnTagClickListener listener) {
        this.clickListener = listener;
        adapter.setClickListener(listener);
        return this;
    }

    public TagView setListener(OnTagLongClickListener listener) {
        this.longClickListener = listener;
        adapter.setLongClickListener(listener);
        return this;
    }

    @Override public void tagClicked(int position, String item) {
        if (clickListener != null) clickListener.tagClicked(position, item);
    }

    @Override public void tagLongClicked(int position, String item) {
        if (longClickListener != null) longClickListener.tagLongClicked(position, item);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = recyclerView.getMeasuredWidth() - recyclerView.getPaddingLeft()
                - recyclerView.getPaddingRight();
        // make sure the width is at least 1
        width = Math.max(1, width);
        layoutManager.setSpanCount(width);
        setTagSize();
        recyclerView.setLayoutManager(layoutManager);
    }

    public TagView removeAllTags() {
        int count = tags.size();
        tags.clear();
        adapter.notifyItemRangeRemoved(0, count);
        return this;
    }

    public TagView removeTag(int position) {
        tags.remove(position);
        adapter.notifyItemRemoved(position);
        return this;
    }
}
