package com.example.android.weareonthetag;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.widget.TextView;


public class ConnectToTagger extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_tagger);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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
            BluetoothAdapter btAdp = BluetoothAdapter.getDefaultAdapter();
            if(btAdp == null)
            {
                outputTxtView.setText("Bluetooth not provided on device");
            }
            else {
                if (btAdp.isEnabled() == true) {
                    outputTxtView.setText("Bluetooth is Enabled");
                } else {
                    outputTxtView.setText("Bluetooth is Disabled");
                }
            }
            return rootView;
        }
    }
}
