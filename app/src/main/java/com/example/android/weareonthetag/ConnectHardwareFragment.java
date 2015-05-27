package com.example.android.weareonthetag;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Set;

/**
 * A fragment containing a view for connecting to tagger hardware.
 */
public class ConnectHardwareFragment extends Fragment {
    BluetoothAdapter bta;
    ArrayAdapter mArrayAdapter;
    View rootView;
    Button connectDevicesBtn;
    BluetoothMessageHandler mListener;

    public ConnectHardwareFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_connect_hardware, container, false);
        connectDevicesBtn = (Button) rootView.findViewById(R.id.refreshDevicesBtn);
        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        bta = MainActivity.bluetoothAdapter;
        mArrayAdapter = new ArrayAdapter(getActivity(), R.layout.list_item_bt_devices, R.id.list_item_bt_device);
        lv.setAdapter(mArrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rootView.findViewById(R.id.startButton).setVisibility(View.VISIBLE);
            }
        });
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
            mArrayAdapter.clear();
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
        return true;
    }

    private void CheckBlueToothState() {
        if (MainActivity.bluetoothAdapter == null) {
        } else {
            if (MainActivity.bluetoothAdapter.isEnabled()) {
                if (MainActivity.bluetoothAdapter.isDiscovering()) {
                } else {
                    connectDevicesBtn.setVisibility(View.VISIBLE);
                    CheckForKnownDevices();
                }
            } else {
                mListener.onBTDisabled(this);
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
