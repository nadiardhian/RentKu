package com.example.zulfikaranshari.rentku;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by OJI on 09/04/2018.
 */

public class pencarianAdapter extends RecyclerView.Adapter<pencarianAdapter.ViewHolder> {
    List<pencarianModel> kendaraanList;
    Context context;
    String url;
    DatabaseReference databaseReference;

    public pencarianAdapter(Context context, List<pencarianModel> TempList) {
        databaseReference=FirebaseDatabase.getInstance().getReference(PencarianAwal.kendaraan);
        this.kendaraanList=TempList; //refrensi variable

        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu, parent, false);  //refrensi variable

        ViewHolder viewHolder=new ViewHolder(view); //refrensi variable ke holder

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final pencarianModel UploadInfo=kendaraanList.get(position);
        int totalHarga = Integer.valueOf(UploadInfo.getHarga())*PencarianAwal.iHarga;
        holder.merk.setText(UploadInfo.getMerk()); //inisiasi ke holder
        holder.noPolisi.setText(UploadInfo.getNo_polisi());
        holder.harga.setText(String.valueOf(totalHarga));
        holder.namaPemilik.setText(UploadInfo.getNama_pemilik());
        url=UploadInfo.getGambar();
        // holder.namaUser.setText(UploadInfo.getUserKomen()); //inisiasi ke holder
        holder.namaPemilik.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent x=new Intent(view.getContext(), ActivityProfilePenyewa.class);
                x.putExtra("namaPemilik", UploadInfo.getNama_pemilik());
                view.getContext().startActivity(x);
            }
        });
        //Loading image from Glide library.
        Glide.with(context).load(url).into(holder.gambarKendaraan);
        holder.pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(v.getContext(), Activity_Pembayaran.class);
                i.putExtra("merk", UploadInfo.getMerk());
                i.putExtra("noPolisi", UploadInfo.getNo_polisi());
                i.putExtra("harga", UploadInfo.getHarga());
                i.putExtra("namaPemilik", UploadInfo.getNama_pemilik());
                i.putExtra("db", PencarianAwal.kendaraan);
                i.putExtra("tanggalPinjam", PencarianAwal.tanggalPinjam);
                i.putExtra("tanggalKembali", PencarianAwal.tanggalKembali);
                v.getContext().startActivity(i);

            }
        });
    }


    @Override
    public int getItemCount() {

        return kendaraanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView merk, noPolisi, harga, namaPemilik;
        public ImageView gambarKendaraan;
        public Button pilih;

        public ViewHolder(View itemView) {
            super(itemView);
            pilih=(Button) itemView.findViewById(R.id.btn_pilih);
            //imageView = (ImageView) itemView.findViewById(R.id.imageView);
            merk=(TextView) itemView.findViewById(R.id.merkKendaraan);
            noPolisi=(TextView) itemView.findViewById(R.id.no_kendaraan);
            harga=(TextView) itemView.findViewById(R.id.harga_kendaraan);
            namaPemilik=(TextView) itemView.findViewById(R.id.pemilikKendaraan);
            gambarKendaraan=(ImageView) itemView.findViewById(R.id.gambarKendaraan);


        }


    }

}
