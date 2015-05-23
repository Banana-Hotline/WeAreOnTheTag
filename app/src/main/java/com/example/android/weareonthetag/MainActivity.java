package com.example.android.weareonthetag;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Set;

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

    /**
     * A fragment containing a view for connecting to tagger hardware.
     */
    public static class ConnectHardwareFragment extends Fragment {
        BluetoothAdapter bta = MainActivity.bluetoothAdapter;
        ArrayAdapter mArrayAdapter;
        View rootView;
        Button connectDevicesBtn;
        TextView outputTxtView;
        BluetoothMessageHandler mListener;

        public ConnectHardwareFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_connect_hardware, container, false);
            outputTxtView = (TextView) rootView.findViewById(R.id.outputTxtView);
            connectDevicesBtn = (Button) rootView.findViewById(R.id.connectDevicesBtn);
            mArrayAdapter = new ArrayAdapter(getActivity(), R.layout.list_item_bt_devices, R.id.list_item_bt_device);
            connectDevicesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckForKnownDevices();
                    return;
                }
            });
            CheckBlueToothState();
            return rootView;
        }

        public boolean CheckForKnownDevices() {
            Set<BluetoothDevice> pairedDevices = bta.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListView
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
            return true;
        }

        private void CheckBlueToothState() {
            if (bluetoothAdapter == null) {
                outputTxtView.setText("Bluetooth NOT supported");
            } else {
                if (bluetoothAdapter.isEnabled()) {
                    if (bluetoothAdapter.isDiscovering()) {
                        outputTxtView.setText("Bluetooth is currently in device discovery process.");
                    } else {
                        outputTxtView.setText("Bluetooth is Enabled.");
                        connectDevicesBtn.setVisibility(View.VISIBLE);
                    }
                } else {
                    outputTxtView.setText("Bluetooth is NOT Enabled!");
                    mListener.onBTDisabled(rootView);
                }
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
                try {
                    mListener = (BluetoothMessageHandler) activity;
                } catch (ClassCastException e) {
                    throw new ClassCastException(activity.toString() + " must implement ExampleFragmentCallbackInterface ");
                }
        }
    }

}
