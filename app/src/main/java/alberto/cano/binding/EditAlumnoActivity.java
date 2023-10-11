package alberto.cano.binding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import alberto.cano.binding.databinding.ActivityEditAlumnoBinding;
import alberto.cano.binding.modelos.Alumno;

public class EditAlumnoActivity extends AppCompatActivity {
    private ActivityEditAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_alumno);
        binding = ActivityEditAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Alumno alumno = (Alumno) bundle.getSerializable("ALUMNO");
        //Toast.makeText(this, alumno.toString(), Toast.LENGTH_SHORT).show();

        rellenarAlumno(alumno);

        binding.btnEditarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.btnCancelarEditarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void rellenarAlumno(Alumno alumno) {
        binding.txtNombreEditarAlumno.setText(alumno.getNombre());
        binding.txtApellidosEditarAlumno.setText(alumno.getApellidos());
        //Guardar la posicion
        int posicion = 0;
        switch (alumno.getCiclo()){
            case "SMR": posicion = 1;
            break;
            case "DAM": posicion = 2;
            break;
            case "DAW": posicion = 3;
            break;
            case "3D": posicion = 4;
            break;
            case "MARKETING": posicion = 5;
            break;
        }
        binding.spCiclosEditarAlumno.setSelection(posicion);

        switch (alumno.getGrupo()){
            case "A": binding.rbGrupoAEditarAlumno.setChecked(true);
            break;
            case "B": binding.rbGrupoBEditarAlumno.setChecked(true);
            break;
            case "C": binding.rbGrupoCEditarAlumno.setChecked(true);
            break;
        }

    }
}