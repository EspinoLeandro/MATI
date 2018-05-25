package layout;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cj.mati.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adaptadores.CapturistasAdaptador;
import Firebase.Capturista;
import Firebase.Referencias;
import Utilidades.Funciones;

public class administrador_capturista_recycle extends Fragment implements CapturistasAdaptador.OnCapturistaListener {

    private static final String KEY_IS_EDIT = "key_is_edit";
    private static final String KEY_IS_DELETE = "key_is_delete";
    private RecyclerView capturistas;
    private List<Capturista> capturistasList = new ArrayList<>();
    private CapturistasAdaptador adapter;
    private boolean isEdit, isDelete;


    public static administrador_capturista_recycle newInstance(boolean isEdit, boolean isDelete) {
        administrador_capturista_recycle capturista_recycle = new administrador_capturista_recycle();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_IS_EDIT, isEdit);
        bundle.putBoolean(KEY_IS_DELETE, isDelete);
        capturista_recycle.setArguments(bundle);
        return capturista_recycle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_administrador_capturista_recycle, container, false);

        capturistas = (RecyclerView) view.findViewById(R.id.recycleCapturistas);
        adapter = new CapturistasAdaptador(capturistasList, getContext());
        adapter.setListener(this);
        capturistas.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        capturistas.setAdapter(adapter);

        if (getArguments() != null) {
            isEdit = getArguments().getBoolean(KEY_IS_EDIT);
            isDelete = getArguments().getBoolean(KEY_IS_DELETE);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCapturistas();
    }

    private void getCapturistas() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        } catch (Exception e) {

        }
        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);


        myRef.child(Referencias.CAPTURISTAS_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                capturistasList.clear();
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {
                    Log.i("INFOs", snapshot.getValue().toString());
                    Capturista obj = snapshot.getValue(Capturista.class);

                    capturistasList.add(obj);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showEditFragment(Capturista capturista) {
        Administrador__Capturista__Editar editFragment = new Administrador__Capturista__Editar();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_capturistas, editFragment)
                .addToBackStack(administrador_capturista_recycle.class.getName())
                .commit();
        editFragment.setCapturista(capturista);
    }

    private void showAlertDialogDelete(final String usuario) {
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setTitle("Eliminar capturista");
        dialog.setMessage(String.format("¿Esta seguro de que desea eliminar el capturista?"));
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete(usuario);
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void delete(String usuario) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        } catch (Exception e) {

        }
        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);

        myRef.child(Referencias.CAPTURISTAS_REFERENCE).child(usuario).setValue(null);

        getCapturistas();
    }

    @Override
    public void onClickItemCapturista(Capturista capturista) {
        if (isEdit) {
            showEditFragment(capturista);
        }
    }

    @Override
    public void onClickLongItemCapturista(String usuario) {
        if (isDelete) {
            showAlertDialogDelete(usuario);
        }
    }
}
