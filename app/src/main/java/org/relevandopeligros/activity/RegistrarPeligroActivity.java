package org.relevandopeligros.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.relevandopeligros.data.Peligro;
import org.relevandopeligros.relevandopeligrosapp.R;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegistrarPeligroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_peligro);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new RegistrarPeligroFragment(), RegistrarPeligroFragment.TAG)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registrar_peligro, menu);
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

    static final int REQUEST_IMAGE_CAPTURE_1 = 1;
    static final int REQUEST_IMAGE_CAPTURE_2 = 2;
    static final int REQUEST_IMAGE_CAPTURE_3 = 3;

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class RegistrarPeligroFragment extends Fragment {

        public static String TAG = RegistrarPeligroFragment.class.getSimpleName();


        public RegistrarPeligroFragment() {
        }

        @InjectView(R.id.imagen_peligro_1)
        ImageView registrarPeligro1;

        @InjectView(R.id.imagen_peligro_2)
        ImageView registrarPeligro2;

        @InjectView(R.id.imagen_peligro_3)
        ImageView registrarPeligro3;

        @InjectView(R.id.registrar_peligro_title)
        EditText titulo;

        @InjectView(R.id.registrar_peligro_descipcion)
        EditText descripcion;

        Bitmap photo1;
        Bitmap photo2;
        Bitmap photo3;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_registrar_peligro, container, false);
            ButterKnife.inject(this, rootView);
            if (savedInstanceState != null && savedInstanceState.containsKey("cameraImageUri")) {
                fileUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
            }
            return rootView;
        }


        @OnClick(R.id.take_picture_1)
        void dispatchTakePictureIntent() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
                fileUri = Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg"));
                Log.i("MzX", fileUri.toString());

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE_1);
            }
        }

        Uri fileUri;

        @OnClick(R.id.take_picture_2)
        void dispatchTakePictureIntent2() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_2);
            }
        }

        @OnClick(R.id.take_picture_3)
        void dispatchTakePictureIntent3() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_3);
            }
        }

        @OnClick(R.id.submit_peligro)
        void registrarPeligro() {
            Peligro peligro = new Peligro();
            peligro.setTitulo(titulo.getText().toString());
            peligro.setDescripcion(descripcion.getText().toString());
            ImmutableList.Builder<Peligro.ImagenPeligro> peligros = ImmutableList.builder();
            if (photo1 != null) {
                Peligro.ImagenPeligro imagenPeligro = new Peligro.ImagenPeligro();
                try {
                    imagenPeligro.setPath(RegistrarPeligroActivity.convertInputStreamToString(photo1));
                    peligros.add(imagenPeligro);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (photo2 != null) {
                Peligro.ImagenPeligro imagenPeligro = new Peligro.ImagenPeligro();
                try {
                    imagenPeligro.setPath(RegistrarPeligroActivity.convertInputStreamToString(photo2));
                    peligros.add(imagenPeligro);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (photo3 != null) {
                Peligro.ImagenPeligro imagenPeligro = new Peligro.ImagenPeligro();

                try {
                    imagenPeligro.setPath(RegistrarPeligroActivity.convertInputStreamToString(photo3));
                    peligros.add(imagenPeligro);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            peligro.setImagenes(peligros.build());

        }


        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.reset(this);
        }

        @Override
        public void onDetach() {
            super.onDetach();

            // Reset the active callbacks interface to the dummy implementation.

        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            if (fileUri != null) {
                outState.putString("cameraImageUri", fileUri.toString());
            }
        }

        static final int REQUEST_IMAGE_CAPTURE_1 = 1;
        static final int REQUEST_IMAGE_CAPTURE_2 = 2;
        static final int REQUEST_IMAGE_CAPTURE_3 = 3;

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                switch (requestCode) {
                    case REQUEST_IMAGE_CAPTURE_1: {
                        ViewGroup.LayoutParams layoutParams = registrarPeligro1.getLayoutParams();
                        layoutParams.height = 300;
                        registrarPeligro1.setLayoutParams(layoutParams);
                        registrarPeligro1.setImageBitmap(photo);
                        photo1 = photo;
                    }
                    break;
                    case REQUEST_IMAGE_CAPTURE_2: {
                        ViewGroup.LayoutParams layoutParams = registrarPeligro2.getLayoutParams();
                        layoutParams.height = 300;
                        registrarPeligro2.setLayoutParams(layoutParams);
                        registrarPeligro2.setImageBitmap(photo);
                        photo2 = photo;
                    }
                    break;
                    case REQUEST_IMAGE_CAPTURE_3: {
                        ViewGroup.LayoutParams layoutParams = registrarPeligro3.getLayoutParams();
                        layoutParams.height = 300;
                        registrarPeligro3.setLayoutParams(layoutParams);
                        registrarPeligro3.setImageBitmap(photo);
                        photo3 = photo;
                    }
                    break;
                }
            }
        }

    }


//    private class GsonUploader extends AsyncTask<Void, Void, String> {
//
//        private final Peligro peligro;
//
//        public GsonUploader(Peligro peligro){
//            this.peligro = peligro;
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            // TODO Auto-generated method stub
//            String result = "";
//
//            HttpResponse response;
//
//
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            gson.toJson(peligro,Peligro.class);
//
//
//            HttpClient httpClient = new DefaultHttpClient();
//
//            // using POST method
//            HttpPost httpPostRequest = new HttpPost("URl");
//
////            StringEntity se = new StringEntity( peligro.toString());
////            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
////            post.setEntity(se);
////            response = client.execute(post);
////            /*Checking response */
////            /*if(response!=null){
////                InputStream in = response.getEntity().getContent(); //Get the data in the entity
////*/
////            int statusCode = response.getStatusLine().getStatusCode();
////
////            // return result;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
////            uploadStatus.setText("Uploading image to server");
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
////            uploadStatus.setText(result);
//        }
//
//    }

    static String convertInputStreamToString(Bitmap bitmap)
            throws IOException {


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);


        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(bs));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        bos.close();
        return result;

    }

}
