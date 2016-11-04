package nesto.tagview;

/**
 * Created on 2016/11/3.
 * By nesto
 */
public class Tag {
    public final String tag;
    public final Integer textColor;
    public final Integer backgroundColor;

    public Tag(String tag) {
        this.tag = tag;
        this.textColor = null;
        this.backgroundColor = null;
    }

    public Tag(String tag, Integer textColor, Integer backgroundColor) {
        this.tag = tag;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

}