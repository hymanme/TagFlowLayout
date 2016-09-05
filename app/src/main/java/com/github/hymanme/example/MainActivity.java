package com.github.hymanme.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.hymanme.tagflowlayout.OnTagClickListener;
import com.github.hymanme.tagflowlayout.TagAdapter;
import com.github.hymanme.tagflowlayout.TagFlowLayout;
import com.github.hymanme.tagflowlayout.bean.TagBean;
import com.github.hymanme.tagflowlayout.view.ColorfulTagView;
import com.github.hymanme.tagflowlayout.view.DefaultTagView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TagFlowLayout mTagFlowLayout;
    private FloatingActionButton fab;
    private EditText et_title;
    private Button btn_title;
    private SeekBar sb_animation;
    private SeekBar sb_min_height;
    private SeekBar sb_max_height;
    private CheckBox cb_scrollable;
    private boolean canScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTagFlowLayout = (TagFlowLayout) findViewById(R.id.tag_flow_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        et_title = (EditText) findViewById(R.id.et_title);
        btn_title = (Button) findViewById(R.id.btn_title);
        sb_animation = (SeekBar) findViewById(R.id.sb_animation);
        sb_min_height = (SeekBar) findViewById(R.id.sb_min_height);
        sb_max_height = (SeekBar) findViewById(R.id.sb_max_height);
        cb_scrollable = (CheckBox) findViewById(R.id.cb_scrollable);


        List<TagBean> tagBeans = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            tagBeans.add(new TagBean(i, "tags+" + i));
        }
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
        MyTagAdapter tagAdapter = new MyTagAdapter();
        tagAdapter.addAllTags(tagBeans);
        mTagFlowLayout.setTagAdapter(tagAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mTagFlowLayout.setTitle("大家都不想搜");
//                mTagFlowLayout.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
//                mTagFlowLayout.setTitleTextSize(12);
//                mTagFlowLayout.setMinVisibleHeight(100);
//                mTagFlowLayout.setMaxVisibleHeight(400);
//                mTagFlowLayout.setTagListener(new OnTagClickListener() {
//                    @Override
//                    public void onClick(TagFlowLayout parent, View view, int position) {
//
//                    }
//
//                    @Override
//                    public void onLongClick(TagFlowLayout parent, View view, int position) {
//
//                    }
//                });
//                mTagFlowLayout.setAnimationDuration(600);
//                mTagFlowLayout.setBackGroundColor(getResources().getColor(R.color.primary_text));
//                mTagFlowLayout.setCanScroll(canScroll = !canScroll);
                startActivity(new Intent(MainActivity.this, ScrollingActivity.class));
            }
        });

        btn_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTagFlowLayout.setTitle(et_title.getText().toString());
            }
        });

        sb_animation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTagFlowLayout.setAnimationDuration(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_min_height.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTagFlowLayout.setMinVisibleHeight(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_max_height.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTagFlowLayout.setMaxVisibleHeight(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        cb_scrollable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTagFlowLayout.setCanScroll(isChecked);
            }
        });
    }

    class MyTagAdapter extends TagAdapter<TagBean> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DefaultTagView textView = new ColorfulTagView(MainActivity.this);
            textView.setText(((TagBean) getItem(position)).getName());
            return textView;
        }
    }
}
