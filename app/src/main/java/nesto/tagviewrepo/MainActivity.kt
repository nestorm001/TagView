package nesto.tagviewrepo

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import nesto.tagview.OnTagClickListener
import nesto.tagview.OnTagLongClickListener
import nesto.tagview.Tag
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TYPE_TEXT = 1
        val TYPE_BACKGROUND = 2
    }

    private var background: Int = 0
    private var text: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tags.addStringTag("fffffffffffffffffffffffffff")
                .addStringTag("ooooooooooooooooooooooooooo")
                .addStringTags(Arrays.asList("hello", "world"))
                .addTag(Tag("apple", Color.argb(0xff, 0x88, 0xff, 0xff),
                        Color.argb(0xff, 0x77, 0x66, 0x55)))
                .addTag(Tag("google", Color.argb(0xff, 0x00, 0x00, 0x00),
                        Color.argb(0xff, 0x77, 0x66, 0x55)))
                .addTag(Tag("microsoft", Color.argb(0xff, 0x11, 0x22, 0x33),
                        Color.argb(0xff, 0xee, 0xaa, 0xcc)))
                .backgroundColor(Color.argb(0xff, 0x7f, 0x7f, 0x7f))
                .textColor(Color.argb(0xff, 0xff, 0xff, 0xff))
                .setListener({
                    // do whatever you like
                } as OnTagClickListener)
                .setListener({
                    // do whatever you like
                } as OnTagLongClickListener)
                .textSize(12)
                .margin(2)
                .padding(16)
                .corner(5)
                .itemHeight(30)
                .dividerHeight(8)
        setWatcher()
    }

    private fun setWatcher() {
        backgroundR.addTextChangedListener(SimpleTextWatcher(TYPE_BACKGROUND))
        backgroundG.addTextChangedListener(SimpleTextWatcher(TYPE_BACKGROUND))
        backgroundB.addTextChangedListener(SimpleTextWatcher(TYPE_BACKGROUND))
        textR.addTextChangedListener(SimpleTextWatcher(TYPE_TEXT))
        textG.addTextChangedListener(SimpleTextWatcher(TYPE_TEXT))
        textB.addTextChangedListener(SimpleTextWatcher(TYPE_TEXT))
        setText()
        setBackground()
    }

    fun add(v: View) {
        val tagString = input.text.toString()
        if (tagString.isEmpty()) return
        tags.addTag(Tag(tagString, text, background))
    }

    private fun setText() {
        text = Color.rgb(getColor(textR), getColor(textG), getColor(textB))
        textColor.setBackgroundColor(text)
    }

    private fun setBackground() {
        background = Color.rgb(getColor(backgroundR), getColor(backgroundG), getColor(backgroundB))
        backgroundColor.setBackgroundColor(background)
    }

    private fun getColor(editText: EditText): Int {
        try {
            return Math.min(255, Integer.parseInt(editText.text.toString()))
        } catch (e: NumberFormatException) {
            return 0
        }
    }

    private inner class SimpleTextWatcher(private val type: Int) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {
            if (type == TYPE_TEXT) setText()
            if (type == TYPE_BACKGROUND) setBackground()
        }
    }
}
