package com.mark.markaop;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mark.aoplibrary.annotation.CheckLogin;
import com.mark.aoplibrary.annotation.CheckNet;
import com.mark.aoplibrary.annotation.Logger;
import com.mark.aoplibrary.annotation.SingleClick;
import com.mark.aoplibrary.annotation.TimeLog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView = (ListView) findViewById(R.id.listView);
        // 添加ListItem，设置事件响应
        mListView.setAdapter(new DemoListAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SingleClick
            public void onItemClick(AdapterView<?> arg0, View v, int index,
                                    long arg3) {
                onListItemClick(index);
            }
        });
    }

    void onListItemClick(int index) {
        Method[] Methods = MainActivity.class.getDeclaredMethods();
        for (Method method : Methods) {
            if (DEMOS[index].methodName.equals(method.getName())) {
                Log.e(TAG, method.getName() + "====================star==================>");
                try {
                    method.invoke(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, method.getName() + "====================end===================>");
                break;
            }
        }
    }

    @Logger
    private void loggerTest(){

    }
    @CheckLogin(isLogin = true)
    private void checkLoginTest(){
        Log.e(TAG, "checkLoginTest: 用户已经登陆了，dothing" );
    }
    @CheckNet
    private void checkNetTest(){
        Log.e(TAG, "checkNetTest: 网络已连接，dothing" );
    }

    @TimeLog
    private void timeLogTest(){
        SystemClock.sleep(300);
        Log.e(TAG, "timeLogTest: 执行完毕" );
    }

    private static final DemoInfo[] DEMOS = {
            new DemoInfo("@Logger的使用",
                    "日志切面编程测试，函数调用前后打印日志", "loggerTest"),
            new DemoInfo("@CheckLogin的使用",
                    "检查登陆切面编程测试，如果没有登陆会跳转登陆界面", "checkLoginTest"),
            new DemoInfo("@CheckNet的使用",
                    "检查网络切面编程测试，如果没有网络就跳转网络设置页面", "checkNetTest"),
            new DemoInfo("@TimeLog的使用",
                    "时间差切面编程测试，函数调用后，打印函数所需要的时间", "timeLogTest"),
            new DemoInfo("@Logger的使用",
                    "日志切面编程测试，函数调用前后打印日志", ""),
            new DemoInfo("@Logger的使用",
                    "日志切面编程测试，函数调用前后打印日志", "")
    };

    private class DemoListAdapter extends BaseAdapter {
        public DemoListAdapter() {
            super();
        }

        @Override
        public View getView(int index, View convertView, ViewGroup parent) {
            convertView = View.inflate(MainActivity.this,
                    R.layout.demo_info_item, null);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView desc = (TextView) convertView.findViewById(R.id.desc);
            title.setText(DEMOS[index].title);
            desc.setText(DEMOS[index].desc);
            if (index >= 25) {
                title.setTextColor(Color.YELLOW);
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return DEMOS.length;
        }

        @Override
        public Object getItem(int index) {
            return DEMOS[index];
        }

        @Override
        public long getItemId(int id) {
            return id;
        }
    }

    private static class DemoInfo {
        private String title;
        private String desc;
        private String methodName;

        public DemoInfo(String title, String desc, String methodName) {
            this.title = title;
            this.desc = desc;
            this.methodName = methodName;
        }
    }
}
