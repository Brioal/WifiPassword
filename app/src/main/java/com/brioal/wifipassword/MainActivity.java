package com.brioal.wifipassword;

import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.brioal.wifipassword.base.WifiItem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private ExpandableListView listView;
    private List<String> strings;
    private List<WifiItem> wifiList;
    private MyAdapter adapter;
    private List<String> listID;
    private List<String> listPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ExpandableListView) findViewById(R.id.main_expandlist);
        initData();
    }

    private void initData() {
        strings = new ArrayList<>();
        wifiList = new ArrayList<>();
        strings.add("cd data/" + ShellUtils.COMMAND_LINE_END);
        strings.add("cd misc/" + ShellUtils.COMMAND_LINE_END);
        strings.add("cd wifi/" + ShellUtils.COMMAND_LINE_END);
        strings.add("ls" + ShellUtils.COMMAND_LINE_END);
        strings.add("cat wpa_supplicant.conf" + ShellUtils.COMMAND_LINE_END);
        ShellUtils.CommandResult result = ShellUtils.execCommand(strings, true, true);
        //获取返回的结果
        String wifis = result.successMsg;
        wifis = wifis.replaceAll("key_mgmt=NONE", "psk=\"无密码\"");
        System.out.println(wifis);
        Pattern pattern = null;
        Matcher matcher = null;
        listID = new ArrayList<>();
        listPass = new ArrayList<>();
        //获取用户名
        String rexId = "ssid=[\\S]+[\\s]";
        pattern = Pattern.compile(rexId);
        matcher = pattern.matcher(wifis);

        while (matcher.find()) {
            listID.add(matcher.group());
        }

        //获取密码
        String rexPass = "psk=\"[^\"]+\"";
        pattern = Pattern.compile(rexPass);
        matcher = pattern.matcher(wifis);
        while (matcher.find()) {
            listPass.add(matcher.group());
        }

        for (int i = 0; i < listID.size(); i++) {
            String mId = listID.get(i);
            String mPass = listPass.get(i);
            mId = mId.substring(6, mId.length() - 2);
            mPass = mPass.substring(5, mPass.length() - 1);
            WifiItem item = new WifiItem(mId, mPass);
            wifiList.add(item);
        }

        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private class MyAdapter implements ExpandableListAdapter {


        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getGroupCount() {
            return listID.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return listID.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return listPass.get(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return groupPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MainActivity.this);
            textView.setTextSize(22);

            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setText("     "+wifiList.get(groupPosition).getmId());
            textView.setSingleLine();
            textView.setMaxWidth(100);
            textView.setTypeface(Typeface.SANS_SERIF);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            return textView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MainActivity.this);
            textView.setTextSize(15);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setText("      "+wifiList.get(groupPosition).getmPass());
            textView.setTypeface(Typeface.SANS_SERIF);

            return textView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void onGroupExpanded(int groupPosition) {

        }

        @Override
        public void onGroupCollapsed(int groupPosition) {

        }

        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return 0;
        }

        @Override
        public long getCombinedGroupId(long groupId) {
            return 0;
        }
    }


}
