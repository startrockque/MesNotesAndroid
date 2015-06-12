package fabien.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fabien.adapters.NoteAdapter;
import fabien.bdd.NoteBDD;
import fabien.modele.Note;

/**
 * Created by Fabien on 10/06/2015.
 */
public class MainActivity extends Activity {
    private List<Note> listeNotes = new ArrayList<Note>();
    private NoteBDD noteBdd;
    private static final int CODE_DE_L_AJOUT = 1;
    private TextView maMoyenne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        maMoyenne = (TextView) findViewById(R.id.moyenne);

        noteBdd = new NoteBDD(this);

        loadData();

        setList();
    }

    private void loadData() {
        noteBdd.open();
        for (int i = 1; i <= noteBdd.getTaille(); i++) {
            Note note = noteBdd.getNoteWithId(i);
            if (!listeNotes.contains(note))
                listeNotes.add(note);
        }
        noteBdd.close();
    }

    private void setList() {
        NoteAdapter noteAdapter = new NoteAdapter(this, listeNotes);
        ListView listeNotesView = (ListView) findViewById(R.id.liste_notes);
        listeNotesView.setAdapter(noteAdapter);

        maMoyenne.setText(getResources().getString(R.string.accueil_ma_moyenne, moyenne(listeNotes)));
    }

    private double moyenne(List<Note> listeNotes) {
        double totalNote = 0;
        int totalCoeff = 0;
        for (Note n : listeNotes){
            totalNote += n.getNote() * n.getCoeff();
            totalCoeff += n.getCoeff();
        }
        return totalNote/totalCoeff;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadData();
        setList();
    }

    public void addNote(View view) {
        startActivityForResult(new Intent(getApplicationContext(), AddNote.class), CODE_DE_L_AJOUT);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }
}
