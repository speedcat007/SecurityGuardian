package cn.edu.njust.securityguardian.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.update.UmengUpdateAgent;
import com.yzy.supercleanmaster.base.BaseFragment;
import com.yzy.supercleanmaster.model.SDCardInfo;
import com.yzy.supercleanmaster.utils.AppUtils;
import com.yzy.supercleanmaster.utils.StorageUtil;
import com.yzy.supercleanmaster.widget.circleprogress.ArcProgress;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.njust.securityguardian.R;
import cn.edu.njust.securityguardian.ui.AutoStartManageActivity;
import cn.edu.njust.securityguardian.ui.MemoryCleanActivity;
import cn.edu.njust.securityguardian.ui.RubbishCleanActivity;
import cn.edu.njust.securityguardian.ui.SoftwareManageActivity;
import cn.edu.njust.securityguardian.ui.TrafficManageActivity;
import cn.edu.njust.securityguardian.ui.VirusKillActivity;


public class MainFragment extends BaseFragment {

    @InjectView(R.id.arc_store)
    ArcProgress arcStore;

    @InjectView(R.id.arc_process)
    ArcProgress arcProcess;
    @InjectView(R.id.capacity)
    TextView capacity;

    Context mContext;

    private Timer timer;
    private Timer timer2;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        mContext = getActivity();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        fillData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        UmengUpdateAgent.update(getActivity());
    }

    private void fillData() {
        // TODO Auto-generated method stub
        timer = null;
        timer2 = null;
        timer = new Timer();
        timer2 = new Timer();


        long l = AppUtils.getAvailMemory(mContext);
        long y = AppUtils.getTotalMemory(mContext);
        final double x = (((y - l) / (double) y) * 100);
        //   arcProcess.setProgress((int) x);

        arcProcess.setProgress(0);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (arcProcess.getProgress() >= (int) x) {
                            timer.cancel();
                        } else {
                            arcProcess.setProgress(arcProcess.getProgress() + 1);
                        }

                    }
                });
            }
        }, 50, 20);

        SDCardInfo mSDCardInfo = StorageUtil.getSDCardInfo();
        SDCardInfo mSystemInfo = StorageUtil.getSystemSpaceInfo(mContext);

        long nAvailaBlock;
        long TotalBlocks;
        if (mSDCardInfo != null) {
            nAvailaBlock = mSDCardInfo.free + mSystemInfo.free;
            TotalBlocks = mSDCardInfo.total + mSystemInfo.total;
        } else {
            nAvailaBlock = mSystemInfo.free;
            TotalBlocks = mSystemInfo.total;
        }

        final double percentStore = (((TotalBlocks - nAvailaBlock) / (double) TotalBlocks) * 100);

        capacity.setText(StorageUtil.convertStorage(TotalBlocks - nAvailaBlock) + "/" + StorageUtil.convertStorage(TotalBlocks));
        arcStore.setProgress(0);

        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (arcStore.getProgress() >= (int) percentStore) {
                            timer2.cancel();
                        } else {
                            arcStore.setProgress(arcStore.getProgress() + 1);
                        }

                    }
                });
            }
        }, 50, 20);


    }

    @OnClick(R.id.card1)
    void speedUp() {
        startActivity(MemoryCleanActivity.class);
    }


    @OnClick(R.id.card2)
    void rubbishClean() {
        startActivity(RubbishCleanActivity.class);
    }

    @OnClick(R.id.card3)
    void AutoStartManage() {
        startActivity(AutoStartManageActivity.class);
    }

    @OnClick(R.id.card4)
    void SoftwareManage() { startActivity(SoftwareManageActivity.class); }

    @OnClick(R.id.card5)
    void VirusKill() { startActivity(VirusKillActivity.class); }

    @OnClick(R.id.card6)
    void FlowManage() { startActivity(TrafficManageActivity.class); }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        timer2.cancel();
        super.onDestroy();
    }
}
