package com.example.segundoparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Editor_Activity extends AppCompatActivity {

    private TextView editTCancion, editTArtista,editTAlbum,editTDuracion, editTBuscarCancion;

    /*Lista de musica de la api*/
    private List<Music> musics;

    private String Id;
    private ProgressDialog dialog;
    private Music cancionEncontrada;
    private Button guardarCanciones, buscarCancion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_);

        this.setTitle("Agregar Cancion");

        guardarCanciones = findViewById(R.id.btnGuardarMusic);


        editTCancion = findViewById(R.id.editTextCancion);
        editTArtista = findViewById(R.id.editTextArtista);
        editTAlbum = findViewById(R.id.editTextAlbum);
        editTDuracion = findViewById(R.id.editTextDuracion);
        editTBuscarCancion = findViewById(R.id.editTextBuscarCancion);
        buscarCancion = findViewById(R.id.BtnBuscarCancion);

        dialog = new ProgressDialog(this);

        cancionEncontrada = new Music();

        guardarCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancionEncontrada!= null){
                    Intent intent = new Intent();
                    intent.putExtra("id",cancionEncontrada.getId());
                    intent.putExtra("cancion",cancionEncontrada.getCancion());
                    intent.putExtra("artista",cancionEncontrada.getArtista());
                    intent.putExtra("album",cancionEncontrada.getAlbum());
                    intent.putExtra("duracion",cancionEncontrada.getDuracion());
                    setResult(RESULT_OK,intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "GUARDADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "No hay busqueda", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buscarCancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarCancion();
            }
        });

    }

    public void consultarCancion(){
        dialog.setMessage("cargando");
        dialog.show();
        String titulo = editTBuscarCancion.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.deezer.com/search?q="+titulo;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray listMusic = response.getJSONArray("data");
                            musics = new ArrayList<>();
                            int i=0;

                            JSONObject objetoCancion = listMusic.getJSONObject(i);
                            cancionEncontrada.setId(String.valueOf(objetoCancion.getInt("id")));
                            cancionEncontrada.setCancion(objetoCancion.getString("title"));
                            cancionEncontrada.setDuracion(String.valueOf(objetoCancion.getString("duration")));
                            cancionEncontrada.setArtista(objetoCancion.getJSONObject("artist").getString("name"));
                            cancionEncontrada.setAlbum(objetoCancion.getJSONObject("album").getString("title"));


                            editTCancion.setText(cancionEncontrada.getCancion());
                            editTAlbum.setText(cancionEncontrada.getAlbum());
                            editTArtista.setText(cancionEncontrada.getArtista());
                            editTDuracion.setText(cancionEncontrada.getDuracion());

                            dialog.dismiss();

                        }catch (Exception e){
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"No se encontró",Toast.LENGTH_SHORT).show();
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
