# TagFlowLayout

[GIF图演示，请戳我](http://ww2.sinaimg.cn/mw690/005X6W83jw1f7j44gjx2ng308w0dcx6w.gif)

![tagFlowLayout2](http://ww2.sinaimg.cn/mw690/005X6W83gw1f7itnhdmwjj30c00lcjtd.jpg)

#How to use
jcent please wait...

#### 1. 定义xml布局
```Java 
    <com.github.hymanme.tagflowlayout.TagFlowLayout
        android:id="@+id/tag_flow_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        app:expandHint="查看更多"
        app:foldHint="点击缩回">
```

#### 2. 初始化

```Java
     mTagFlowLayout = (TagFlowLayout) findViewById(R.id.tag_flow_layout);
     mTagFlowLayout.setTitle("大家都不想搜");
     mTagFlowLayout.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark)
     mTagFlowLayout.setTitleTextSize(12);
     //最小显示高度(单位dp)
     mTagFlowLayout.setMinVisibleHeight(100);
     //最大显示高度(单位dp)
     mTagFlowLayout.setMaxVisibleHeight(400);
     mTagFlowLayout.setAnimationDuration(600);
     //每一个标签的圆角角度
     mTagFlowLayout.setBackGroundColor(getResources().getColor(R.color.primary_text));
     //scollview内容是否可以上下滑动
     mTagFlowLayout.setCanScroll(canScroll = !canScroll);
```

#### 3. 设置adapter和监听，最好先设置监听后设置adapter
```Java
    //设置监听(单击和长按事件)
    mTagFlowLayout.setTagListener(new OnTagClickListener() {
            @Override
            public void onClick(TagFlowLayout parent, View view, int position) {
                Toast.makeText(MainActivity.this, "click==" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(TagFlowLayout parent, View view, int position) {
                Toast.makeText(MainActivity.this, "long click==" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    //设置adapter
    MyTagAdapter tagAdapter = new MyTagAdapter();
    mTagFlowLayout.setTagAdapter(tagAdapter);
    //给adapter绑定数据
    tagAdapter.addAllTags(tagBeans);
    //自定义Adapter：MyTagAdapter，其中TagBean为泛型类，即每一个tag的实体类
    //在getView()里面自定义tag标签的样式
    //默认提供了两个实例tag：DefaultTagView，ColorfulTagView
    //DefaultTagView：默认tag
    //ColorfulTagView：彩色的tag
    //当然还可以自己自定义
    class MyTagAdapter extends TagAdapter<TagBean> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DefaultTagView textView = new ColorfulTagView(MainActivity.this);
            textView.setText(((TagBean) getItem(position)).getName());
            return textView;
        }
    }
```

####可选项(部分属性可直接在xml布局中指定)

    //标题
    private String title;
    //展开后显示的提示文字
    private String foldHint;
    //折叠起来后显示的提示文字
    private String expandHint;
    //标题文字颜色
    private int titleTextColor;
    //控件背景颜色
    private int backGroundColor;
    //查看更多文字颜色
    private int hintTextColor;
    //分割线颜色
    private int dividerColor;
    //标签之间的横向间距
    private int tagsHorizontalSpace;
    //标签之间的纵向间距
    private int tagsVerticalSpace;
    //查看更多前面显示的小图标
    private Drawable indicateImage;
    //内容区域最少显示高度(px)
    private int minVisibleHeight;
    //内容区域最大显示高度
    private int maxVisibleHeight;
    //标题字体大小(单位sp)
    private float titleTextSize;
    //提示字体大小
    private float hintTextSize;
    //展开和折叠动画持续时间
    private int animationDuration;

    //点击监听事件
    private OnTagClickListener mListener;
    //设置adapter
    private TagAdapter mTagAdapter;
