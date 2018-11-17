package in.kestone.sap.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.kestone.sap.R;
import in.kestone.sap.holder.Help;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {
    Context context;
    ArrayList<Help> list;

    public HelpAdapter(Activity activity, ArrayList<Help> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.custom_layout_help, parent, false );
        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Help rowItem = list.get( position );
        holder.name.setText( rowItem.getName() );
        holder.email.setText( rowItem.getEmail() );
        holder.phone.setText( rowItem.getPhoneNumber() );

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, phone;
        View view;
        CardView card_list;

        public ViewHolder(View itemView) {
            super( itemView );

            name = itemView.findViewById( R.id.tvName );
            email = itemView.findViewById( R.id.tvEmail );
            phone = itemView.findViewById( R.id.tvPhone );

        }
    }


}
