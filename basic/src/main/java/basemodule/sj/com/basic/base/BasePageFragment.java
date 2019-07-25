package basemodule.sj.com.basic.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle2.components.RxFragment;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * content:Fragment懒加载 Fragment基类
 * author：sj
 * time: 2017/6/20 18:03
 * email：13658029734@163.com
 * phone:13658029734
 */

public abstract class BasePageFragment extends RxFragment {

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    private View rootView;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        /* EventBus.getDefault().register(this);*/
    }

    @Override
    public void onStop() {
        super.onStop();
        /*EventBus.getDefault().unregister(this);*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDataInitiated = false;
        unbinder.unbind();
        if (rootView != null) {
            rootView = null;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(initLayout(), null);
            unbinder = ButterKnife.bind(this, rootView);
            initLayoutView();
        } else {
            //避免重复添加View
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    protected abstract int initLayout();

    protected abstract void initLayoutView();

    //强制再次刷新
    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    //懒加载模式之加载数据
    public abstract void fetchData();

    /**
     * EventBus 事件接收方法
     *
     * @param event
     */
  /*  @Subscribe
    public void onEventMainThread(BaseEvent event) {
    }*/
}
