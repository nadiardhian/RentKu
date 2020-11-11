package com.example.zulfikaranshari.rentku;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by OJI on 17/04/2018.
 */

public class BelumLunasAdapter extends RecyclerView.Adapter<BelumLunasAdapter.ViewHolder> {

    Context context; //inisiasi variable
    List<pembayaranModel> pembayaranList; //

    public BelumLunasAdapter(Context context, List<pembayaranModel> TempList) {

        this.pembayaranList = TempList; //refrensi variable

        this.context = context;
    }

    @Override
    public BelumLunasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_belumlunas, parent, false);  //refrensi variable

        ViewHolder viewHolder = new ViewHolder(view); //refrensi variable ke holder

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BelumLunasAdapter.ViewHolder holder, int position) {
        pembayaranModel l = pembayaranList.get(position);
        holder.rbMerk.setText(l.getMerk());
        holder.rbNoPolisi.setText(l.getNo_polisi());
        holder.rbNamaPemilik.setText(l.getNama_pemilik());
        holder.rbTanggalPinjam.setText(l.getTanggalPinjam());
        holder.rbTanggalKembali.setText(l.getTanggalKembali());
        holder.rbHarga.setText("Rp."+l.getHarga());
        holder.rbStatus.setText(l.getStatus());
        if(holder.rbStatus.getText().equals("Selesai")){
        holder.rbStatus.setBackgroundColor(context.getResources().getColor(R.color.greens));}
    }

    @Override
    public int getItemCount() {
        return pembayaranList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView rbMerk, rbNoPolisi, rbNamaPemilik, rbTanggalPinjam, rbTanggalKembali, rbHarga, rbStatus;


        public ViewHolder(View itemView) {
            super(itemView);
            rbMerk = (TextView) itemView.findViewById(R.id.rbMerk);
            rbNoPolisi = (TextView) itemView.findViewById(R.id.rbNoPolisi);
            rbNamaPemilik = (TextView) itemView.findViewById(R.id.rbNamaPemilik);
            rbTanggalPinjam = (TextView) itemView.findViewById(R.id.rbTanggalPinjam);
            rbTanggalKembali = (TextView) itemView.findViewById(R.id.rbTanggalKembali);
            rbHarga = (TextView) itemView.findViewById(R.id.rbHarga);
            rbStatus = (TextView) itemView.findViewById(R.id.status);


        }


    }
}
