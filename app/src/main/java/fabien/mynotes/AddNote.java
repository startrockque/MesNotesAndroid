package fabien.mynotes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;

import fabien.bdd.NoteBDD;
import fabien.modele.Note;

/**
 * Created by Fabien on 10/06/2015.
 */
public class AddNote extends Activity {
    private NoteBDD noteBdd;
    private FormEditText matiere, note, coeff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        noteBdd = new NoteBDD(this);

        matiere = (FormEditText) findViewById(R.id.add_note_matiere);
        note = (FormEditText) findViewById(R.id.add_note_note);
        coeff = (FormEditText) findViewById(R.id.add_note_coeff);
    }

    public void addNewNote(View view) {
        boolean allValid = true;
        FormEditText[] formEditTexts = {matiere, note, coeff};
        for(FormEditText f : formEditTexts){
            allValid = f.testValidity() && allValid;
        }

        if (allValid) {
            noteBdd.open();
            noteBdd.insertNote(new Note(0, matiere.getText().toString(), Double.valueOf(note.getText().toString()), Integer.valueOf(coeff.getText().toString())));
            noteBdd.close();
            Toast.makeText(this, getResources().getString(R.string.add_ok), Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        finish();
    }
}
