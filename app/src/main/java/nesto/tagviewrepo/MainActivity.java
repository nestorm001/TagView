package nesto.tagviewrepo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nesto.tagview.OnTagClickListener;
import nesto.tagview.Tag;
import nesto.tagview.TagView;

public class MainActivity extends AppCompatActivity {

    public static final int TYPE_TEXT = 1;
    public static final int TYPE_BACKGROUND = 2;

    @BindView(R.id.background_r) EditText backgroundR;
    @BindView(R.id.background_g) EditText backgroundG;
    @BindView(R.id.background_b) EditText backgroundB;
    @BindView(R.id.background_color) View backgroundColor;
    @BindView(R.id.text_r) EditText textR;
    @BindView(R.id.text_g) EditText textG;
    @BindView(R.id.text_b) EditText textB;
    @BindView(R.id.text_color) View textColor;
    @BindView(R.id.input) EditText input;
    @BindView(R.id.tags) TagView tags;

    private int background;
    private int text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tags.addStringTags(Arrays.asList("1", "hehe", "lalala", "23333"))
                .addTag(new Tag("heheda", Color.argb(0xff, 0x88, 0xff, 0xff),
                        Color.argb(0xff, 0x77, 0x66, 0x55)))
                .addTag(new Tag("dadahe", Color.argb(0xff, 0x00, 0x00, 0x00),
                        Color.argb(0xff, 0x77, 0x66, 0x55)))
                .addTag(new Tag("hehshf", Color.argb(0xff, 0x11, 0x22, 0x33),
                        Color.argb(0xff, 0xee, 0xaa, 0xcc)))
                .backgroundColor(Color.argb(0xff, 0x88, 0xff, 0xff))
                .textColor(Color.argb(0xff, 0xff, 0xff, 0xff))
                .textSize(14)
                .setListener(new OnTagClickListener() {
                    @Override public void tagClicked(String item) {
                        Log.d("wtf", item);
                    }
                })
                .margin(2)
                .padding(18);
        setWatcher();
    }

    private void setWatcher() {
        backgroundR.addTextChangedListener(new SimpleTextWatcher(TYPE_BACKGROUND));
        backgroundG.addTextChangedListener(new SimpleTextWatcher(TYPE_BACKGROUND));
        backgroundB.addTextChangedListener(new SimpleTextWatcher(TYPE_BACKGROUND));
        textR.addTextChangedListener(new SimpleTextWatcher(TYPE_TEXT));
        textG.addTextChangedListener(new SimpleTextWatcher(TYPE_TEXT));
        textB.addTextChangedListener(new SimpleTextWatcher(TYPE_TEXT));
        setText();
        setBackground();
    }

    @OnClick(R.id.add) public void onClick() {
        String tagString = input.getText().toString();
        if (tagString.isEmpty()) return;
        tags.addTag(new Tag(tagString, text, background));
    }

    private void setText() {
        text = Color.rgb(getColor(textR), getColor(textG), getColor(textB));
        textColor.setBackgroundColor(text);
    }

    private void setBackground() {
        background = Color.rgb(getColor(backgroundR), getColor(backgroundG),
                getColor(backgroundB));
        backgroundColor.setBackgroundColor(background);
    }

    private int getColor(EditText editText) {
        try {
            return Math.min(255, Integer.parseInt(editText.getText().toString()));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private class SimpleTextWatcher implements TextWatcher {

        private int type;

        SimpleTextWatcher(int type) {
            this.type = type;
        }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override public void afterTextChanged(Editable s) {
            if (type == TYPE_TEXT) setText();
            if (type == TYPE_BACKGROUND) setBackground();
        }
    }
}
