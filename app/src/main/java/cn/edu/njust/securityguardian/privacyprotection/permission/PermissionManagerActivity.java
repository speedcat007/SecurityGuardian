package cn.edu.njust.securityguardian.privacyprotection.permission;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzy.supercleanmaster.base.BaseSwipeBackActivity;
import com.yzy.supercleanmaster.widget.textcounter.CounterView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.njust.securityguardian.R;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by fookey on 15-2-17.
 */
public class PermissionManagerActivity extends BaseSwipeBackActivity {


    @InjectView(R.id.textCounter)
    CounterView textCounter;
    @InjectView(R.id.header)
    RelativeLayout header;
    @InjectView(R.id.tv_permission_card_1)
    TextView tvPermissionCard1;
    @InjectView(R.id.card1)
    LinearLayout card1;
    @InjectView(R.id.tv_permission_card_2)
    TextView tvPermissionCard2;
    @InjectView(R.id.card2)
    LinearLayout card2;
    @InjectView(R.id.tv_permission_card_3)
    TextView tvPermissionCard3;
    @InjectView(R.id.card3)
    LinearLayout card3;
    @InjectView(R.id.tv_permission_card_4)
    TextView tvPermissionCard4;
    @InjectView(R.id.card4)
    LinearLayout card4;
    @InjectView(R.id.tv_permission_card_5)
    TextView tvPermissionCard5;
    @InjectView(R.id.card5)
    LinearLayout card5;
    @InjectView(R.id.tv_permission_card_6)
    TextView tvPermissionCard6;
    @InjectView(R.id.card6)
    LinearLayout card6;
    @InjectView(R.id.tv_permission_card_7)
    TextView tvPermissionCard7;
    @InjectView(R.id.card7)
    LinearLayout card7;
    @InjectView(R.id.tv_permission_card_8)
    TextView tvPermissionCard8;
    @InjectView(R.id.card8)
    LinearLayout card8;
    @InjectView(R.id.tv_permission_card_9)
    TextView tvPermissionCard9;
    @InjectView(R.id.card9)
    LinearLayout card9;
    @InjectView(R.id.layout_container)
    RelativeLayout layoutContainer;
    @InjectView(R.id.progressBar2)
    CircularProgressBar progressBar2;
    @InjectView(R.id.progressBarText)
    TextView progressBarText;
    @InjectView(R.id.progressBar)
    LinearLayout progressBar;
    @InjectView(R.id.tv_permission_card_10)
    TextView tvPermissionCard10;
    @InjectView(R.id.card10)
    LinearLayout card10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_manager);
        ButterKnife.inject(this);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
