package com.github.hymanme.example;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.hymanme.tagflowlayout.OnTagClickListener;
import com.github.hymanme.tagflowlayout.TagAdapter;
import com.github.hymanme.tagflowlayout.TagFlowLayout;
import com.github.hymanme.tagflowlayout.bean.TagBean;
import com.github.hymanme.tagflowlayout.tags.ColorfulTagView;
import com.github.hymanme.tagflowlayout.tags.DefaultTagView;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {
    private TagFlowLayout mTagFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        List<TagBean> tagBeans = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            tagBeans.add(new TagBean(i, "tags+" + i));
        }
        mTagFlowLayout = (TagFlowLayout) findViewById(R.id.tag_flow_layout);
        mTagFlowLayout.setTagListener(new OnTagClickListener() {
            @Override
            public void onClick(TagFlowLayout parent, View view, int position) {
                Toast.makeText(ScrollingActivity.this, "click==" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(TagFlowLayout parent, View view, int position) {
                Toast.makeText(ScrollingActivity.this, "long click==" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
        MyTagAdapter tagAdapter = new MyTagAdapter();
        tagAdapter.addAllTags(tagBeans);
        mTagFlowLayout.setTagAdapter(tagAdapter);
    }

    class MyTagAdapter extends TagAdapter<TagBean> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DefaultTagView textView = new ColorfulTagView(ScrollingActivity.this);
            textView.setText(((TagBean) getItem(position)).getName());
            return textView;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            //想让哪个控件先执行，直接调用哪个控件的dispatchTouchEvent()方法
            //如果已经拖动到listView的底端，将事件分发给ScrollView

        }
        return super.dispatchTouchEvent(ev);
    }
}
