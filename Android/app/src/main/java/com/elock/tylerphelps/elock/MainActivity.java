package com.elock.tylerphelps.elock;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.pubnub.api.PubNub;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.models.consumer.*;
import com.pubnub.api.callbacks.PNCallback;

public class MainActivity extends AppCompatActivity {

    PubNub pubnub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("pub-c-a8732a53-6069-4292-8981-a1a9a230172f");
        pnConfiguration.setPublishKey("sub-c-a27f6252-e02e-11e6-989b-02ee2ddab7fe");

        pubnub = new PubNub(pnConfiguration);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pubnub.publish()
                        .message("Unlock from Android")
                        .channel("eLockServer")
                        .shouldStore(true)
                        .usePOST(true)
                        .async(new PNCallback<PNPublishResult>() {
                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                if (status.isError()) {
                                    // something bad happened.
                                    System.out.println("error happened while publishing: " + status.toString());
                                } else {
                                    System.out.println("publish worked! timetoken: " + result.getTimetoken());
                                }
                            }
                        });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
