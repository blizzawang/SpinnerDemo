package com.itcast.test0430;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.iv_down_arrow)
    ImageView ivDownArrow;

    /**
     *
     */
    private PopupWindow popupWindow;
    private ListView listView;

    private ArrayList<String> msgs;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        listView = new ListView(this);
        listView.setBackgroundColor(Color.WHITE);
        //准备数据
        msgs = new ArrayList<>();
        for(int i = 0;i < 500;i++) {
            msgs.add(i + "--aaaaaa---" + i);
        }
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1、得到数据
                String msg = msgs.get(position);
                //2、设置到输入框
                etInput.setText(msg);

                if(popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
    }

    @OnClick(R.id.et_input)
    public void onViewClick(View view){
        if(popupWindow == null){
            popupWindow = new PopupWindow(this);
            popupWindow.setWidth(etInput.getWidth());
            int height = DensityUtil.dipToPx(this,400);
            popupWindow.setHeight(height);

            popupWindow.setContentView(listView);
            popupWindow.setFocusable(true);//设置焦点
        }

        popupWindow.showAsDropDown(etInput,0,0);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(MainActivity.this,R.layout.item_main,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_msg = convertView.findViewById(R.id.tv_msg);
                viewHolder.iv_delete = convertView.findViewById(R.id.iv_delete);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //根据位置得到数据
            final String msg = msgs.get(position);
            viewHolder.tv_msg.setText(msg);

            //设置删除
            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //1、从集合删除
                    msgs.remove(msg);
                    //2、刷新UI---也就是 刷新适配器
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
    static class ViewHolder{
        TextView tv_msg;
        ImageView iv_delete;
    }
}
