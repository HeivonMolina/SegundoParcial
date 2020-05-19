package com.example.segundoparcial;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class Music {
    @NonNull
    @PrimaryKey
    private String id;
    private String cancion;
    private String artista;
    private String album;
    private String duracion;

    public Music() {
    }

    public Music(String id, String cancion, String artista, String album, String duracion) {
        this.id = id;
        this.cancion = cancion;
        this.artista = artista;
        this.album = album;
        this.duracion = duracion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCancion() {
        return cancion;
    }

    public void setCancion(String cancion) {
        this.cancion = cancion;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
