package com.polyclinic.basemoudle.pop;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polyclinic.basemoudle.R;
import com.polyclinic.library.base.BasePopowindow;
import com.polyclinic.library.view.LoadingView;

/**
 * @author Lxg
 * @create 2020/3/11
 * @Describe
 */
public class WaitingPopowindow extends BasePopowindow {
    LoadingView lvLoading;
    private TextView tvProgress;

    public WaitingPopowindow(Context context) {
        super(context, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        initView();
        setBackgroundDrawable(null);

    }

    private void initView() {
        lvLoading = view.findViewById(R.id.lv_loading);
        tvProgress = view.findViewById(R.id.tv_progress);
    }

    public void startLoadind() {
        lvLoading.start();
    }

    public void stopLoading() {
        lvLoading.stop();
    }

    public void showDissMiss(){
        lvLoading.stop();
        dismiss();
    }


    @Override
    public int getLayoutId() {
        return R.layout.basemodel_pop_waiting;
    }
    public void setProgress(String progress){
        tvProgress.setText(progress);
    }

}
