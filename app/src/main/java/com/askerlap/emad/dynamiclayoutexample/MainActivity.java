package com.askerlap.emad.dynamiclayoutexample;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private LinearLayout parentContainer;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentContainer = (LinearLayout)findViewById(R.id.main_container);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mSwipeRefreshLayout.setRefreshing(true);

                                        createDynamicLayout(7,parentContainer);
                                    }
                                }
        );


    }


    private void createDynamicLayout (int acual_size , LinearLayout parent) {

        mSwipeRefreshLayout.setRefreshing(true);

        int HALF_SIZE , INSIDE_LOOP_SIZE;
        if ( acual_size %2 == 0){
            HALF_SIZE = acual_size / 2 ;
        } else {
            HALF_SIZE = (acual_size / 2) + 1 ;
        }
        LinearLayout [] childLayout = new  LinearLayout[HALF_SIZE];
        LinearLayout.LayoutParams[] layoutParamses = new LinearLayout.LayoutParams[acual_size];
        TextView [] textViews = new TextView[acual_size];

        for ( int i= 0 ; i < HALF_SIZE ;i++){
            childLayout[i] = new LinearLayout(this);
            childLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            childLayout[i].setGravity(Gravity.CENTER);


            if ( i == HALF_SIZE-1 && acual_size %2 == 1){
                INSIDE_LOOP_SIZE = (i*2)+1 ;
            } else {
                INSIDE_LOOP_SIZE = (i*2)+2;
            }

            for ( int j=i*2 ; j< INSIDE_LOOP_SIZE ; j++){
                layoutParamses[j] = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParamses[j].setMargins(8,2,8,2);
                layoutParamses[j].weight = 1;
                childLayout[i].setLayoutParams(layoutParamses[j]);

                textViews[j] = new TextView(this);
                textViews[j].setText(j+"- hello from the other side of the world");
                textViews[j].setPadding(10,10,10,10);
                textViews[j].setBackgroundResource(R.drawable.background_border);
                textViews[j].setLayoutParams(layoutParamses[j]);
                childLayout[i].addView(textViews[j]);

            }
            parent.addView(childLayout[i]);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        x++;
        parentContainer.removeAllViews();
        createDynamicLayout(x,parentContainer);
    }
}
