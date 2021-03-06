package com.summerdeveloper.rameshwar.twentyone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.method.CharacterPickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.summerdeveloper.rameshwar.twentyone.dao.DBHelper;
import com.summerdeveloper.rameshwar.twentyone.model.Task;
import com.summerdeveloper.rameshwar.twentyone.onboard.OnBoardActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CustomAdapter listAdapter;
    private ListView listView;
    private ArrayList<Task> arrayList;
    private DBHelper db=new DBHelper(this);
    //private ArrayAdapter listAdapter;
    private  ProgressBar progressBar;
    private Task task;
    private TextView tvMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvMessage=(TextView)findViewById(R.id.tvMessage);

        /* The below line is used to TEST the onboarding expreience */
        //PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().clear().apply();

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);

        if(!preferences.getBoolean("onboarding_complete",false))
        {
            Intent i=new Intent(this, OnBoardActivity.class);
            startActivity(i);
            finish();
            return;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = (ListView) findViewById(R.id.list);
        arrayList = db.getAllTasks();
        if(arrayList==null || arrayList.size()==0)
        {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText("There are no tasks yet");
            listView.setVisibility(View.GONE);
        }
        else
        {
            tvMessage.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

        listAdapter=new CustomAdapter(arrayList,HomeActivity.this);
        listView.setAdapter(listAdapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DEBUG", "Long pressed");
                final int pos = position;

                new AlertDialog.Builder(HomeActivity.this)
                        .setMessage("Do you wish to remove this task?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = arrayList.get(pos).getTaskID();
                                arrayList.remove(pos);
                                db.remove(id);
                                listAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                }
                        )
                        .show();
                return true;
            }


        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),DetailActivity.class);
                task=arrayList.get(position);
                i.putExtra("taskID", task.getTaskID());
                i.putExtra("position",position);
                progressBar=(ProgressBar)view.findViewById(R.id.row_progress);
                Log.d("DEBUG","Before");
                startActivityForResult(i,1);
                Log.d("DEBUG","After");

                //update progress
               // listAdapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(progressBar!=null && task!=null)
            progressBar.setProgress(task.getNoOfCompletedDays());
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addtask) {
            // Handle add task
            if(arrayList!=null)
            {
                if(arrayList.size()>=10)
                {
                    new AlertDialog.Builder(HomeActivity.this)
                            .setTitle("Alert")
                            .setMessage("There are already 10 activities! Too many cooks spoil the broth :)")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            })
                            .show();
                    return true;
                }
            }
            Intent i=new Intent(getApplicationContext(),AddTask.class);
            startActivity(i);
        } else if (id == R.id.nav_removetask) {
            Toast.makeText(this,"Long Press the task you want to delete",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_viewprogress) {

        } else if (id == R.id.nav_rate) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
