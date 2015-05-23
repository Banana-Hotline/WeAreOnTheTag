package com.example.android.weareonthetag;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements BluetoothMessageHandler {
    private static final int REQUEST_ENABLE_BT = 1;

    /**
     * Called when the activity is first created.
     */
    public static BluetoothAdapter bluetoothAdapter;
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_tagger);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ConnectHardwareFragment())
                    .commit();
        }
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connect_to_tagger, menu);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        Button connectDevicesBtn =(Button) rootView.findViewById(R.id.connectDevicesBtn);
        TextView outputTxtView =(TextView) rootView.findViewById(R.id.outputTxtView);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                connectDevicesBtn.setVisibility(View.VISIBLE);
                outputTxtView.setText("Bluetooth is Enabled.");
            } else if (resultCode == RESULT_CANCELED) {
                outputTxtView.setText("You must have Bluetooth enabled to connect to game devices.");
            } else {
                outputTxtView.setText("There was an issue trying to enable Bluetooth.");
            }
        }
    }

    @Override
    public void onBTDisabled(View v) {
        rootView = v;
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

}
