package alberto.cano.binding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import alberto.cano.binding.databinding.ActivityAddAlumnoBinding;
import alberto.cano.binding.modelos.Alumno;

public class addAlumnoActivity extends AppCompatActivity {

    private ActivityAddAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_alumno);
        binding = ActivityAddAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RECOGER LA INFORMACIÓN PARA CREAR EL ALUMNO
                Alumno alumno = crearAlumno();
                if (alumno != null){
                    //HACER EL INTENT
                    Intent intent = new Intent();
                    //PONEE EL BUNDLE
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ALUMNO", alumno);
                    intent.putExtras(bundle);
                    //COMUNICAR EL RESULTADO CORRECTO
                    setResult(RESULT_OK, intent);
                    //TERMINAR
                    finish();
                }else {
                    Toast.makeText(addAlumnoActivity.this, "Faltan Datos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private Alumno crearAlumno() {
        if (binding.txtNombreAddAlumno.getText().toString().isEmpty()){
            return null;
        }

        if (binding.txtApellidosAddAlumno.getText().toString().isEmpty()){
            return null;
        }
        if (binding.spCiclosAlumno.getSelectedItemPosition() == 0){
            return null;
        }
        if (!binding.rbGrupoAAddAlumno.isChecked()
        && !binding.rbGrupoBAddAlumno.isChecked()
        && !binding.rbGrupoCAddAlumno.isChecked()){
            return null;
        }

        RadioButton rb = findViewById(binding.rgGrupoAddAlumno.getCheckedRadioButtonId());
        char grupo = (rb.getText().toString().split(" ")[1].charAt(0));
        Alumno alumno = new Alumno(
                binding.txtNombreAddAlumno.getText().toString(),
                binding.txtApellidosAddAlumno.getText().toString(),
                binding.spCiclosAlumno.getSelectedItem().toString(),
                String.valueOf(grupo));

        return alumno;
    }

}