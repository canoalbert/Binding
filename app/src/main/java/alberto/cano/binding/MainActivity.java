package alberto.cano.binding;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import alberto.cano.binding.databinding.ActivityMainBinding;
import alberto.cano.binding.modelos.Alumno;

public class MainActivity extends AppCompatActivity {

    public int posicion;
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addAlumnoLauncher;
    private ActivityResultLauncher<Intent> editAlumnoLauncher;

    private ArrayList<Alumno> listaAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarLauncher();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        listaAlumno = new ArrayList<>();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir la ventana add alumno
                addAlumnoLauncher.launch(new Intent(MainActivity.this, addAlumnoActivity.class));
            }
        });


    }

    private void inicializarLauncher() {
        addAlumnoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK){
                            if (result.getData() != null && result.getData().getExtras() != null){
                                Alumno alumno = (Alumno) result.getData().getExtras().getSerializable("ALUMNO");
                                //Toast.makeText(MainActivity.this, alumno.toString(), Toast.LENGTH_SHORT).show();
                                listaAlumno.add(alumno);
                                monstrarAlumnos();
                                //Falta mostrar información
                                //1- Un elemento para mostrar la información
                                //2- Un conjunto de datos a mostrar
                                //3- Un contenedor donde mostrar cada uno de los elementos


                            }else {
                                Toast.makeText(MainActivity.this, "NO HAY INFIRMACIÓN", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "ACCION CANCELADA", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );

        editAlumnoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK){
                            if(result.getData() != null && result.getData().getExtras() != null){
                                Alumno alumno = (Alumno) result.getData().getExtras().getSerializable("ALUMNO");
                                listaAlumno.set(posicion, alumno);
                                mostrarAlumnos();
                            }
                        }
                    }
                });


    }

    private void mostrarAlumnos() {
    }

    private void monstrarAlumnos() {
        binding.contentMain.contenedorMain.removeAllViews();
        for (Alumno alumno: listaAlumno){
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

            View alumnoView = layoutInflater.inflate(R.layout.alumno_fila_view, null);
            TextView lbNombre = alumnoView.findViewById(R.id.lbNombreAlumnoView);
            TextView lbApellidos = alumnoView.findViewById(R.id.lbApellidosAlumnoView);
            TextView lbCiclos = alumnoView.findViewById(R.id.lbCiclolumnoView);
            TextView lbGrupos = alumnoView.findViewById(R.id.lbGrupoAlumnoView);

            //Ventana EditAlumnoActivity
            alumnoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, EditAlumnoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ALUMNO", alumno);
                    intent.putExtras(bundle);
                    posicion = listaAlumno.indexOf(alumno);
                    editAlumnoLauncher.launch(intent);
                }
            });


            lbNombre.setText(alumno.getNombre());
            lbApellidos.setText(alumno.getApellidos());
            lbCiclos.setText(alumno.getCiclo());
            //lbGrupo.setText(alumno.getGrupo());
            lbGrupos.setText(String.valueOf(alumno.getGrupo()));

            binding.contentMain.contenedorMain.addView(alumnoView);
        }
    }

}