package org.relevandopeligros.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.relevandopeligros.data.Peligro;
import org.relevandopeligros.data.PeligrosResponse;
import org.relevandopeligros.relevandopeligrosapp.R;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slapsh);
        getActionBar().hide();
        getLoaderManager().initLoader(0, savedInstanceState, new RegistrarPeligroLoaderCallbacks(SplashActivity.this)).forceLoad();

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                startActivity(new Intent(SplashActivity.this, PeligroListActivity.class));
//                SplashActivity.this.finish();
//            }
//        };
//
//
//        new Thread(runnable).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.slapsh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class PeligrosTaskLoader extends AsyncTaskLoader<List<Peligro>> {
        public PeligrosTaskLoader(Context context) {
            super(context);
        }

        @Override
        public List<Peligro> loadInBackground() {
            Gson gson = new Gson();
            List<Peligro> peligroList = Collections.emptyList();

            try {
                InputStream is = getContext().getResources().getAssets().open("master-1.json");
                PeligrosResponse peligrosResponse = gson.fromJson(new InputStreamReader(is), PeligrosResponse.class);
                peligroList = peligrosResponse.getInfo().getPeligros();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return peligroList;
        }
    }


    class RegistrarPeligroLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Peligro>> {
        private final Context context;

        RegistrarPeligroLoaderCallbacks(Context context) {
            this.context = context;
        }

        @Override
        public Loader<List<Peligro>> onCreateLoader(int id, Bundle args) {
            return new PeligrosTaskLoader(context);
        }

        @Override
        public void onLoadFinished(Loader<List<Peligro>> loader, List<Peligro> data) {
            Intent intent = new Intent(SplashActivity.this, PeligroListActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtra(PeligroListActivity.PELIGRO_LIST, Lists.newArrayList(data));
//            bundle.putSerializable(PeligroListActivity.PELIGRO_LIST, Lists.newArrayList(data));

            startActivity(intent);
            SplashActivity.this.finish();
        }

        @Override
        public void onLoaderReset(Loader<List<Peligro>> loader) {
        }

    }

}
