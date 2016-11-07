# TagView  

<a href="http://www.methodscount.com/?lib=cn.nesto%3Atagview%3A0.1.1"><img src="https://img.shields.io/badge/Methods and size-core: 118 | deps: 18818 | 26 KB-e91e63.svg"/></a>

A custom view to show tags or similar staff, which moves item to the next line when no space left on the current.

## Preview

<p align="center">
    <img src="/screenshots/preview_0.1.1.png" alt="a simple demo use TagView" width="405" height="720">
</p>

## Setup and usage  

### Installation [ ![Download](https://api.bintray.com/packages/nestorm001/maven/tagview/images/download.svg) ](https://bintray.com/nestorm001/maven/tagview/_latestVersion)

```GRADLE
compile 'cn.nesto:tagview:0.1.1'
```

Assuming you have installed jCenter provider:

```GRADLE
allprojects {
    repositories {
        jcenter()
    }
}
```
or just import `TagView` to your project.

### Usage

Add your `TagView` in layout xml or simply add it programmatically.

```Java
    TagView tagView = new TagView(context);
    theViewGroupYouWantToAddTagView.addView(tagView);
```
As you get your `TagView`, set it up and add whatever tag you like. All the number you set, like textSize, margin, padding, is a dp size. All these settings are optional. You know, I'm lazzzzy, all this attributes cannot be set in xml yet.   
```Java
    tagView.textColor(Color.argb(0xff, 0xff, 0xff, 0xff))
           .backgroundColor(Color.argb(0xff, 0x7f, 0x7f, 0x7f))
           .setListener(new OnTagClickListener() {
               @Override public void tagClicked(String item) {
                   // do whatever you like
               }
           })
           .setListener(new OnTagLongClickListener() {
               @Override public void tagLongClicked(String item) {
                   // do whatever you like
               }
           })
           .textSize(10)
           .margin(2)
           .padding(16)
           .corner(5)
           .itemHeight(30);
```

There are several ways for you to add tags. You can simply add `String` or `String` list or add an entity called `Tag`, with tag string and color.
```Java
    tagView.addStringTags(Arrays.asList("hello", "world"));
    tagView.addStringTag("hello");
    tagView.addTags(Arrays.asList(new Tag("hello"), 
                                  new Tag("world", 0xffffff, 0x000000), 
                                  new Tag("sample", null, 0x123456)));
    tagView.addTag(new Tag("hello", 0x654321, null));
```

## TODOs

* Add TagView attributes in xml, e.g. item margin, item max padding, colors.
* ~~Add a method to set item corner radius.~~
* Drag tag.
* Set the max width of item, e.g. half of the view.

## License [![license for TagView](http://www.wtfpl.net/wp-content/uploads/2012/12/wtfpl-badge-4.png)](http://www.wtfpl.net)
```
            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

 Copyright (C) 2016 Nesto <nestorm001@gmail.com>

 Everyone is permitted to copy and distribute verbatim or modified
 copies of this license document, and changing it is allowed as long
 as the name is changed.

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.
 ```
