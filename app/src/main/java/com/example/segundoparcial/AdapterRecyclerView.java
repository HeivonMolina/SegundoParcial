package com.example.segundoparcial;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.musicaViewHolder> {

    /*Lista de musica de la api*/
    private List<Music> music;
    /*Lista de musica guardada por el usuario*/
    private List<Music> musicPlayList;

    private LayoutInflater mInflater;
    private Context context;
    String url = "https://api.deezer.com/playlist/908622995";


    public AdapterRecyclerView(Context context, List<Music> musicPlayList){
        mInflater = LayoutInflater.from(context);
        this.musicPlayList = musicPlayList;
        this.context = context;
    }

    @NonNull
    @Override
    public musicaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_list_view, parent, false);
        return new musicaViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull musicaViewHolder holder, int position) {
        Music mCurrent = musicPlayList.get(position);
        holder.cancion.setText(mCurrent.getCancion()+"-"+mCurrent.getArtista()+"-"+mCurrent.getDuracion());
    }


    @Override
    public int getItemCount() {
        return musicPlayList.size();
    }



    public class musicaViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView cancion,artista, duracion;
        private AdapterRecyclerView adapter;


        public musicaViewHolder(@NonNull View itemView, AdapterRecyclerView adapter) {
            super(itemView);
            cancion = itemView.findViewById(R.id.TextViewCancion);
            artista = itemView.findViewById(R.id.TextViewArtista);
            duracion = itemView.findViewById(R.id.TextViewDuracion);

            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Music music = musicPlayList.get(position);

        }
    }
}
