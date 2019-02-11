package application.app.com.greenDao_test1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;


/**
 * Clase principal, arranca la aplicación.
 * Inicializa los elementos visuales de la app y pasa la referencia de la vista
 * al {@link Controlador}
 */
public class MainActivity extends AppCompatActivity {

    private Button btnIns, btnCons, btnAct, btnElim;
    private TextView tCant, tCnsl;
    private ProgressBar prgrs;
    private Switch swtch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnIns = findViewById(R.id.btnInser);
        this.btnCons = findViewById(R.id.btnCons);
        this.btnAct = findViewById(R.id.btnAct);
        this.btnElim = findViewById(R.id.btnElim);
        this.prgrs = findViewById(R.id.prgrs);
        this.swtch = findViewById(R.id.swch);

        this.tCant = findViewById(R.id.tCant);
        this.tCnsl = findViewById(R.id.tCnsl);

        // pasa la referencia de la vista al controlador
        new Controlador(this);

    }

    public Button getBtnIns() {
        return btnIns;
    }

    public Button getBtnCons() {
        return btnCons;
    }

    public Button getBtnAct() {
        return btnAct;
    }

    public Button getBtnElim() {
        return btnElim;
    }

    public TextView gettCant() {
        return tCant;
    }

    public TextView gettCnsl() {
        return tCnsl;
    }

    public ProgressBar getPrgrs() { return prgrs; }

    public Switch getSwtch() { return swtch; }

}
