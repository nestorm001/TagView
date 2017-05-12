# TagView  

<a href="http://www.methodscount.com/?lib=cn.nesto%3Atagview%3A0.3.1"><img src="https://img.shields.io/badge/Methods and size-core: 118 | deps: 18818 | 26 KB-e91e63.svg"/></a>

A custom view to show tags or similar staff, which moves item to the next line when no space left on the current.

From v0.3.0 this library is converted to Kotlin, the last Java version is v0.2.0, you can get it by
```GRADLE
compile 'cn.nesto:tagview:0.2.0'
```

## Preview

<p align="center">
    <img src="/screenshots/preview_0.1.1.png" alt="a simple demo use TagView" width="405" height="720">
</p>

## Setup and usage  

### Installation [ ![Download](https://api.bintray.com/packages/nestorm001/maven/tagview/images/download.svg) ](https://bintray.com/nestorm001/maven/tagview/_latestVersion)

```GRADLE
compile 'cn.nesto:tagview:0.3.1'
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

```Kotlin
    val tagView = TagView(context)
    theViewGroupYouWantToAddTagView.addView(tagView)
```
As you get your `TagView`, set it up and add whatever tag you like. All the number you set, like textSize, margin, padding, is a dp size. All these settings are optional. You know, I'm lazzzzy, all this attributes cannot be set in xml yet.   
```Kotlin
    tagView.addStringTag("fffffffffffffffffffffffffff")
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
           .setOnTagClickListener { /*do whatever you want*/ }
           .setOnTagLongClickListener { /*do whatever you want*/ }
           .textSize(12)
           .margin(2)
           .padding(16)
           .corner(5)
           .itemHeight(30)
           .dividerHeight(8)
```

There are several ways for you to add tags. You can simply add `String` or `String` list or add an entity called `Tag`, with tag string and color.
```Kotlin
    tagView.addStringTags(listOf("hello", "world"))
    tagView.addStringTag("hello")
    tagView.addTags(listOf(Tag("hello"), 
                                  Tag("world", 0xffffff, 0x000000), 
                                  Tag("sample", null, 0x123456)))
    tagView.addTag(Tag("hello", 0x654321, null))
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
