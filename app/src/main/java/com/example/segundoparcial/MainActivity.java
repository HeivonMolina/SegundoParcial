package com.example.segundoparcial;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*Lista de musica guardada por el usuario*/
    private List<Music> musicPlayList;

    private RecyclerView recyclerView;
    private AdapterRecyclerView adapter;
    private FloatingActionButton guardarCanciones;
    private int CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Mi lista de Canciones");
        descargarPlayList();
        recyclerView = findViewById(R.id.recyclerView);
        guardarCanciones = findViewById(R.id.guardara);


        guardarCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Editor_Activity = new Intent(MainActivity.this, Editor_Activity.class);
                startActivityForResult(Editor_Activity,CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE){
            if (resultCode == RESULT_OK){

                Music music = new Music();
                assert data != null;
                music.setId(data.getStringExtra("id"));
                music.setCancion(data.getStringExtra("cancion"));
                music.setArtista(data.getStringExtra("artista"));
                music.setAlbum(data.getStringExtra("album"));
                music.setDuracion(data.getStringExtra("duracion"));

                musicPlayList.add(music);
                llenarRecycler();
            }
        }
    }


    public void llenarRecycler(){

        if (musicPlayList == null || musicPlayList.isEmpty()){
            Toast.makeText(this,"cargando Play List",Toast.LENGTH_SHORT).show();
        }else {
            adapter = new AdapterRecyclerView(this, musicPlayList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        llenarRecycler();
    }

    public void descargarPlayList(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.deezer.com/playlist/93489551";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray listMusic = response.getJSONObject("tracks").getJSONArray("data");
                            musicPlayList = new ArrayList<>();
                            int valor = listMusic.length(),i=0;

                            while (i<valor){
                                Music music = new Music();
                                JSONObject objetoCancion = listMusic.getJSONObject(i);
                                music.setId(objetoCancion.getString("id"));
                                music.setCancion(objetoCancion.getString("title"));
                                music.setDuracion(objetoCancion.getString("duration"));
                                music.setArtista(objetoCancion.getJSONObject("artist").getString("name"));
                                music.setAlbum(objetoCancion.getJSONObject("album").getString("title"));
                                musicPlayList.add(music);
                                i++;
                            }

                            llenarRecycler();

                        }catch (Exception e){

                            Toast.makeText(getApplicationContext(),"Error al realizar la peticion",Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);
    }
}
