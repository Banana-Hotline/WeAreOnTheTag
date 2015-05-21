package com.example.android.weareonthetag;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.bluetooth.BluetoothAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

public class ConnectToTagger extends ActionBarActivity {
    private static final int REQUEST_ENABLE_BT = 1;

    /** Called when the activity is first created. */

    TextView outputTxtView;
    BluetoothAdapter bluetoothAdapter;
    Button connectDevicesBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_connect_to_tagger);

        outputTxtView = (TextView)findViewById(R.id.outputTxtView);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        connectDevicesBtn = (Button)findViewById(R.id.connectDevicesBtn);

        CheckBlueToothState();
    }
    private void CheckBlueToothState(){
        if (bluetoothAdapter == null){
            outputTxtView.setText("Bluetooth NOT support");
        }else{
            if (bluetoothAdapter.isEnabled()){
                if(bluetoothAdapter.isDiscovering()){
                    outputTxtView.setText("Bluetooth is currently in device discovery process.");
                }else{
                    outputTxtView.setText("Bluetooth is Enabled.");
                    connectDevicesBtn.setVisibility(View.VISIBLE);
                }
            }else{
                outputTxtView.setText("Bluetooth is NOT Enabled!");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
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
        if (resultCode == RESULT_OK) {
            connectDevicesBtn.setVisibility(View.VISIBLE);
            outputTxtView.setText("Bluetooth is Enabled.");
        } else if (resultCode == RESULT_CANCELED) {
            outputTxtView.setText("You must have Bluetooth enabled to connect to game devices.");
        }
        else{
            outputTxtView.setText("There was an issue trying to enable Bluetooth.");
        }

    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_connect_to_tagger, container, false);
            TextView outputTxtView = (TextView)rootView.findViewById(R.id.outputTxtView);
            Button connectDevicesBtn = (Button) rootView.findViewById(R.id.connectDevicesBtn);

            return rootView;
        }
    }
}
