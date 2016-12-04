package com.systemcare.systemcare;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.systemcare.systemcare.api.ApiClient;
import com.systemcare.systemcare.api.ApiInterface;
import com.systemcare.systemcare.classes.Monitoramento;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BluetoothSPP.OnDataReceivedListener {

    private BluetoothSPP btSpp = new BluetoothSPP(getBaseContext());
    private TextView tvBpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothSettings();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvBpm = (TextView) findViewById(R.id.tv_bpm);
        tvBpm.setTypeface(digitalFont());

        Log.d("OnCreate", "OnCreate");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return (id == R.id.action_settings) || super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_report) {
            Intent i = new Intent(MainActivity.this, RelatorioActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_about) {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_signout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void bluetoothSettings() {

        btSpp.setupService();
        btSpp.startService(BluetoothState.DEVICE_OTHER);

        if(btSpp.isBluetoothEnabled()) {
            connectDevices();
        } else {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, BluetoothState.REQUEST_ENABLE_BT);
            Toast.makeText(getBaseContext(), "O bluetooth deve estar habilitado", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void connectDevices() {
        Log.d("BLUETEETH", "connectDevices");
        try {
            Intent i = new Intent(getApplicationContext(), DeviceList.class);
            startActivityForResult(i, BluetoothState.REQUEST_CONNECT_DEVICE);
        }catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK) {
                Log.d("BLUETOOTH", data.toString());
                // btSpp.setupService();
                // btSpp.startService(BluetoothState.DEVICE_OTHER);
                try {
                    btSpp.connect(data);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
                btSpp.setOnDataReceivedListener(MainActivity.this);
            }
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                btSpp.setupService();
                btSpp.startService(BluetoothState.DEVICE_OTHER);
            }
        }
    }

    private Typeface digitalFont() {
        AssetManager am = getApplicationContext().getAssets();
        return Typeface.createFromAsset(am, String.format("fonts/%s", "DS-DIGI.TTF"));
    }

    @Override
    public void onDataReceived(byte[] data, String message) {
        Log.d("BLUETOOTH", message);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<Monitoramento> monitoramentoCall = api.postMonitoramento(new Monitoramento(1, Integer.valueOf(message)));
        monitoramentoCall.enqueue(new Callback<Monitoramento>() {
            @Override
            public void onResponse(Call<Monitoramento> call, Response<Monitoramento> response) {
                tvBpm.setText(response.body().getValor() + " bpm");
                findViewById(R.id.iv_logo).setVisibility(ImageView.GONE);
            }

            @Override
            public void onFailure(Call<Monitoramento> call, Throwable t) {

            }
        });
    }
}
