# FloatLayout
流式布局

博客地址：[自定义View-FloatLayout](http://www.fanandjiu.com/article/e95ea743.html)

app模块是其例子，参考效果如下：
![](https://android-1300729795.cos.ap-chengdu.myqcloud.com/project/Self_View/FloatLayout/float_layout.png)

### 1.添加依赖

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
~~~java
allprojects {
    repositories {
        ...
	maven { url 'https://jitpack.io' }
    }
}
~~~

Step 2. Add the dependency

~~~java
dependencies {
	  implementation 'com.github.xiaoshitounen:FloatLayout:1.0.0'
}
~~~

### 二. Xml文件中的静态使用
~~~xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <swu.xl.floatlayout.FloatLayout
        android:layout_width="320dp"
        android:layout_height="wrap_content"
        android:background="@color/colorAccent"
        >
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="UI界面"
            />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="四大组件，七大布局，三大存储"
            />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="数据库"
            android:layout_marginRight="20dp"
            android:layout_marginBottom="20dp"
            />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="自定义View"
            />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="外部存储"
            />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="性能优化"
            />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="UI界面"
            />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="四大组件，七大布局，三大存储"
            />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="数据库"
            android:layout_marginRight="20dp"
            android:layout_marginBottom="20dp"
            />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="自定义View"
            />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="外部存储"
            />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="性能优化"
            />

    </swu.xl.floatlayout.FloatLayout>

</LinearLayout>
~~~

**① 属性**
- horizontalSpace：水平间距
- verticalSpace：垂直间距
