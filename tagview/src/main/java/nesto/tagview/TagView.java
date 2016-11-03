package nesto.tagview;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created on 2016/11/3.
 * By nesto
 */

public class TagView extends FrameLayout implements OnTagClickListener {
    private RecyclerView recyclerView;
    private List<Tag> tags;
    private TextPaint paint;
    private Context context;
    private TagAdapter adapter;
    private GridLayoutManager layoutManager;
    private int width;
    private OnTagClickListener listener;

    private static final int ADDITION_MARGIN = 2;
    private static final int ADDITION_PADDING = 16; // 2 * 2dp margin + 2 * 16dp padding
    private int padding = ADDITION_PADDING;
    private int margin = ADDITION_MARGIN;
    private Integer additionTotal;

    private static final int DEFAULT_TEXT_SIZE = 12;
    private int textSize = DEFAULT_TEXT_SIZE;

    public TagView(Context context) {
        super(context);
        this.context = context;
        View view = inflate(context, R.layout.tag_view, null);
        addView(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        tags = new ArrayList<>();
        adapter = new TagAdapter(context, tags);
        recyclerView.setAdapter(adapter);
        additionTotal = calculateAddition();
        initTextPaint();
        initTagHolder();
    }

    private int calculateAddition() {
        return dp2px(2 * margin + 2 * padding);
    }

    private void initTextPaint() {
        paint = new TextPaint();
        paint.setTextSize(dp2px(textSize));
    }

    private void initTagHolder() {
        recyclerView.post(() -> {
            width = recyclerView.getMeasuredWidth() - recyclerView.getPaddingLeft()
                    - recyclerView.getPaddingRight();
            layoutManager = new GridLayoutManager(context, width);
            setTagSize();
            recyclerView.setLayoutManager(layoutManager);
        });
    }

    private void setTagSize() {
        if (layoutManager == null) return;
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                String text = tags.get(position).tag;
                int size = (int) paint.measureText(text) + additionTotal;
                return Math.min(size, width);
            }
        });
    }

    public TagView textSize(int dpValue) {
        textSize = dpValue;
        paint.setTextSize(dp2px(textSize));
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

    public TagView padding(int padding) {
        this.padding = padding;
        additionTotal = calculateAddition();
        setTagSize();
        return this;
    }

    public TagView margin(int margin) {
        this.margin = margin;
        additionTotal = calculateAddition();
        setTagSize();
        adapter.setMargin(dp2px(margin));
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
        this.listener = listener;
        adapter.setListener(listener);
        return this;
    }

    private int dp2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override public void tagClicked(String item) {
        if (listener != null) listener.tagClicked(item);
    }
}
