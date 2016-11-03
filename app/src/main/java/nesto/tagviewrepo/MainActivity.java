package nesto.tagviewrepo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;

import nesto.tagview.OnTagClickListener;
import nesto.tagview.Tag;
import nesto.tagview.TagView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TagView tagView = (TagView) findViewById(R.id.tags);
        tagView.addStringTags(Arrays.asList("1", "hehe", "lalala", "23333", "尼斯市地方"))
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
    }
}
