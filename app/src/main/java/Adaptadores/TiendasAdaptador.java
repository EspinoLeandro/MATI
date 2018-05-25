package Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cj.mati.R;

import java.util.List;

import Firebase.Tienda;
import layout.administrador_tienda_recycle;

/**
 * Created by Itzel on 15/01/2018.
 */

public class TiendasAdaptador  extends RecyclerView.Adapter<TiendasAdaptador.ViewHolder>{

    private List<Tienda> Tiendas;
    private Context context;
    private OnTiendasListener listener;


    public TiendasAdaptador(List<Tienda> tiendas, Context context) {
        this.Tiendas = tiendas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tiendas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Tienda tienda = Tiendas.get(position);
        holder.negocio.setText(tienda.getNombreNegocio());
        holder.encargado.setText(tienda.getNombreEncargado());
        holder.direccion.setText(tienda.getColonia() + ", " + tienda.getCalle());

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (listener != null) {
                   listener.onClickLongItemTiendas();
                }
                return false;
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                   listener.onClickItemTiendas(tienda);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Tiendas.size();
    }

    public void setListener(administrador_tienda_recycle listener) {
        this.listener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView negocio, encargado, direccion;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            negocio = (TextView) itemView.findViewById(R.id.item_tiendas_nombrenegocio);
            encargado = (TextView) itemView.findViewById(R.id.item_tiendas_nombreencargado);
            direccion = (TextView) itemView.findViewById(R.id.item_tiendas_direccion);
        }
    }

    public interface OnTiendasListener {
        void onClickItemTiendas(Tienda tienda);
        void onClickLongItemTiendas();
    }

}
