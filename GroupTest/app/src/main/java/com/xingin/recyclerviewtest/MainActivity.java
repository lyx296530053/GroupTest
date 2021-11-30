package com.xingin.recyclerviewtest;


import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public RecyclerView recycler;
    private List<ItemBean> list;
    private List<ItemBean> list1;
    private RecyclerViewBaseAdapt listViewAdapt;
    private RecyclerViewBaseAdapt GridViewAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recycler);
        initData();
        initListView(list, true, false);
        setListener();


    }

    private void setListener() {
        listViewAdapt.setOnClickListener((v, p) -> {
            if (p == 0) {
                XmlResourceParser xml = getResources().getXml(R.xml.myxml);
                AttributeSet attributeSet = Xml.asAttributeSet(xml);
                try {
                    while (xml.getEventType() != XmlPullParser.END_DOCUMENT) {
                        if (xml.getEventType() == XmlPullParser.START_TAG) {
                            if (xml.getName().equals("intent")) {
                                Intent intent = Intent.parseIntent(getResources(), xml, attributeSet);
                                startActivity(intent);
                            }
                        }
                        xml.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class ItemBean {
        int icon;
        String title;
    }

    public interface OnClickListener {
        void onItemClick(View view, int position);
    }


    private class RecyclerViewBaseAdapt extends RecyclerView.Adapter<RecyclerViewBaseAdapt.InnerHolder> {
        private final List<?> mData;
        OnClickListener listener;

        public RecyclerViewBaseAdapt(List<?> mData) {
            this.mData = mData;
        }

        public void setOnClickListener(OnClickListener listener) {
            this.listener = listener;
        }

        public View getView(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public InnerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getView(parent, viewType);
            return new InnerHolder(view);

        }

        @Override
        public void onBindViewHolder(MainActivity.RecyclerViewBaseAdapt.InnerHolder holder, int position) {
            if (listener != null) {
                setOnClickListener(holder.viewById, holder.getLayoutPosition());
            }
            List<ItemBean> itemBeans = (List<ItemBean>) mData;
            holder.setData(itemBeans.get(position));
        }


        @Override
        public int getItemCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        public class InnerHolder extends RecyclerView.ViewHolder {

            private final ImageView viewById;
            private final TextView viewById1;

            public InnerHolder(View itemView) {
                super(itemView);
                viewById = (ImageView) itemView.findViewById(R.id.image_id);
                viewById1 = (TextView) itemView.findViewById(R.id.test_id);
            }

            public void setData(ItemBean itemBean) {
                viewById.setImageResource(itemBean.icon);
                viewById1.setText(itemBean.title);
            }


        }

        void setOnClickListener(View viewById, int position) {
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, position);
                }
            });
        }
    }

    private class ListViewAdapt extends RecyclerViewBaseAdapt {

        public ListViewAdapt(List<ItemBean> mData) {
            super(mData);
        }

        @Override
        public View getView(ViewGroup parent, int viewType) {

            View inflate = View.inflate(getApplicationContext(), R.layout.item_list_view, null);
            return inflate;
        }
    }

    private class GridViewAdapt extends RecyclerViewBaseAdapt {

        public GridViewAdapt(List<ItemBean> mData) {
            super(mData);
        }

        @Override
        public View getView(ViewGroup parent, int viewType) {
            View inflate = View.inflate(getApplicationContext(), R.layout.item_list_view, null);
            return inflate;
        }
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemBean itemBean = new ItemBean();
            itemBean.icon = R.mipmap.ic_launcher;
            itemBean.title = "我是第" + i + "条";
            list.add(itemBean);
        }
        list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemBean itemBean = new ItemBean();
            itemBean.icon = R.mipmap.ic_launcher;
            itemBean.title = "我是第" + i + "条";
            list1.add(itemBean);
        }

    }

    private void initListView(List<ItemBean> list, boolean vertical, boolean reverse) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(vertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        layoutManager.setReverseLayout(reverse);
        listViewAdapt = new ListViewAdapt(list);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(listViewAdapt);
    }

    private void initGridView(List<ItemBean> list, boolean vertical, boolean reverse) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(vertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
        layoutManager.setReverseLayout(reverse);
        GridViewAdapt = new GridViewAdapt(list);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(GridViewAdapt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.listview_vertical_stander:
                initListView(list, true, false);
                break;
            case R.id.listview_vertical_reverse:
                initListView(list, true, true);
                break;
            case R.id.listview_horizontal_reverse_stander:
                initListView(list, false, true);
                break;
            case R.id.listview_horizontal_stander:
                initListView(list, false, false);
                break;


            case R.id.gridview_vertical_stander:
                initGridView(list1, true, false);
                break;
            case R.id.gridview_vertical_reverse:
                initGridView(list1, true, true);
                break;
            case R.id.gridview_horizontal_reverse_stander:
                initGridView(list1, false, true);
                break;
            case R.id.gridview_horizontal_stander:
                initGridView(list1, false, false);
                break;


            case R.id.stagger_vertical_stander:
                break;
            case R.id.stagger_vertical_reverse:
                break;
            case R.id.stagger_horizontal_reverse_stander:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}