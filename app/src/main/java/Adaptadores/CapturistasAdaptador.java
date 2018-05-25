package Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cj.mati.R;

import java.util.List;

import Firebase.Capturista;
import layout.administrador_tienda_recycle;


public class CapturistasAdaptador extends RecyclerView.Adapter<CapturistasAdaptador.ViewHolder> {

    private List<Capturista> capturistas;
    private Context context;
    private OnCapturistaListener listener;

    public void setListener(OnCapturistaListener listener) {
        this.listener = listener;
    }

    public CapturistasAdaptador(List<Capturista> capturistas, Context context) {
        this.capturistas = capturistas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_capturistas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Capturista capturista = capturistas.get(position);
        holder.nombre.setText(capturista.getNombre());
        holder.email.setText(capturista.getCorreo());
        holder.apellidos.setText(capturista.getPaterno() + " " + capturista.getMaterno());
        holder.telefono.setText(capturista.getTelefono());

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (listener != null) {
                    listener.onClickLongItemCapturista(capturista.getUsuario());
                }
                return false;
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClickItemCapturista(capturista);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return capturistas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre, apellidos, telefono, email;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nombre = (TextView) itemView.findViewById(R.id.item_capturistas_nombre);
            apellidos = (TextView) itemView.findViewById(R.id.item_capturistas_apellidos);
            email = (TextView) itemView.findViewById(R.id.item_capturistas_email);
            telefono = (TextView) itemView.findViewById(R.id.item_capturistas_telefono);
        }
    }

    public interface OnCapturistaListener {
        void onClickItemCapturista(Capturista capturista);
        void onClickLongItemCapturista(String  usuario);
    }
}
