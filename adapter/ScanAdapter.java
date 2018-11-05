package in.kestone.sap.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import in.kestone.sap.R;
import in.kestone.sap.holder.Detail;
import in.kestone.sap.holder.Scan;

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ViewHolder> {
    Context context;
    ArrayList<Scan> list;

    public ScanAdapter(Activity activity, ArrayList<Scan> list) {

        this.list = list;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.custom_layout, parent, false );

        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Scan rowItem = list.get( position );
        holder.tvScanData.setText( rowItem.getScanCode() );
        holder.tvDateTime.setText( rowItem.getScanON() );
        Random rnd = new Random();
        int color = Color.argb( 255, rnd.nextInt( 256 ), rnd.nextInt( 256 ),
                rnd.nextInt( 256 ) );
        holder.view.setBackgroundColor( color );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvScanData, tvDateTime;
        View view;
        CardView card_list;

        public ViewHolder(View itemView) {
            super( itemView );

            tvScanData = itemView.findViewById( R.id.tvScanData );
            tvDateTime = itemView.findViewById( R.id.tvDateTime );
            view = itemView.findViewById( R.id.view );

        }
    }


}
