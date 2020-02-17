package com.sj.basemodule;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sj.basemodule.adapter.DiffDemoCallback;
import com.sj.basemodule.adapter.DiffUtilAdapter;
import com.sj.basemodule.adapter.DiffUtilDemoEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * recycleView 局部刷新实现
 * Created by limuyang
 * Date: 2019/7/14
 */
public class DiffUtilActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Button itemChangeBtn;
    private Button notifyChangeBtn;
    private DiffUtilAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diffutil);
        /*     setBackBtn();*/
        /*setTitle("DiffUtil Use");*/

        findView();
        initRv();
        initClick();
    }

    private void findView() {
        mRecyclerView = findViewById(R.id.diff_rv);
        itemChangeBtn = findViewById(R.id.item_change_btn);
        notifyChangeBtn = findViewById(R.id.notify_change_btn);
    }

    private void initRv() {
        mAdapter = new DiffUtilAdapter(getDiffUtilDemoEntities());
        mAdapter.bindToRecyclerView(mRecyclerView);

        View view = getLayoutInflater().inflate(R.layout.head_view, mRecyclerView, false);
        view.findViewById(R.id.iv).setVisibility(View.GONE);
        mAdapter.addHeaderView(view);
    }

    public static List<DiffUtilDemoEntity> getDiffUtilDemoEntities() {
        List<DiffUtilDemoEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new DiffUtilDemoEntity(
                    i,
                    "Item " + i,
                    "This item " + i + " content",
                    "06-12")
            );
        }
        return list;
    }

    private void initClick() {
        itemChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //多个view的局部刷新
                DiffDemoCallback callback = new DiffDemoCallback(getNewList());
                mAdapter.setNewDiffData(callback);
                // use async
                // mAdapter.setNewDiffDataAsync(callback, mRecyclerView);
            }
        });

        notifyChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 特定的某个view的局部刷新
                mAdapter.getData().set(0, new DiffUtilDemoEntity(
                        1,
                        "😊😊Item " + 0,
                        "Item " + 0 + " content have change (notifyItemChanged)",
                        "06-12"));
                mAdapter.notifyItemChanged(0 + mAdapter.getHeaderLayoutCount(), DiffUtilAdapter.ITEM_0_PAYLOAD);
            }
        });
    }


    /**
     * get new data
     *
     * @return
     */
    private List<DiffUtilDemoEntity> getNewList() {
        List<DiffUtilDemoEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            /*
            Simulate deletion of data No. 1 and No. 3
            模拟删除1号和3号数据
             */
            if (i == 1 || i == 3)
                continue;

            /*
            Simulate modification title of data No. 0
            模拟修改0号数据的title
             */
            if (i == 0) {
                list.add(new DiffUtilDemoEntity(
                        i,
                        "😊Item " + i,
                        "This item " + i + " content",
                        "06-12")
                );
                continue;
            }

            /*
            Simulate modification content of data No. 4
            模拟修改4号数据的content发生变化
             */
            if (i == 4) {
                list.add(new DiffUtilDemoEntity(
                        i,
                        "Item " + i,
                        "Oh~~~~~~, Item " + i + " content have change",
                        "06-12")
                );
                continue;
            }

            list.add(new DiffUtilDemoEntity(
                    i,
                    "Item " + i,
                    "This item " + i + " content",
                    "06-12")
            );
        }
        return list;
    }
}
