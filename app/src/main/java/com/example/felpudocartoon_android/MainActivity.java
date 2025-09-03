package com.example.felpudocartoon_android;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String[] listaNomes = {"Felpudo", "Fofura", "Lesmo", "Bugado", "Uruca", "Racing", "iOS",
            "Android", "Realidade Aumentada", "Sound FX", "3D Studio Max", "Games"};

    int[] listaIcones = {R.drawable.felpudo, R.drawable.fofura, R.drawable.lesmo, R.drawable.bugado,
            R.drawable.uruca, R.drawable.carrinho, R.drawable.ios, R.drawable.android,
            R.drawable.realidade_aumentada, R.drawable.sound_fx, R.drawable.max, R.drawable.games};

    String[] listaDescricao = {"Um amigo leal e fofo, que traz a suavidade das nuvens para o Reino do Arco-Íris.",
            "Um pequeno desejo que irradia alegria e bondade.",
            "Calmo e tranquilo, ele se move devagar, mas com um propósito.",
            "Um desejo travesso e imprevisível.",
            "Uma criatura misteriosa que prefere a solidão.",
            "A emoção da velocidade e da adrenalina.",
            "Um ícone de elegância e fluidez.",
            "A liberdade e a personalização em sua forma mais pura.",
            "Uma visão mágica do mundo real.",
            "A melodia de uma aventura.",
            "O mestre da criação.",
            "A diversão sem limites."};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       /* ArrayAdapter<String> meuAdaptador = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, listaNomes);

        ListView minhaLista = findViewById(R.id.minhaLista);
        minhaLista.setAdapter(meuAdaptador);
*/
        ListView minhaLista = findViewById(R.id.minhaLista);
        MeuAdaptador meuAdaptador = new MeuAdaptador(getApplicationContext(), R.layout.minha_celula);

        minhaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DadosPersonagem dadosPersonagem = (DadosPersonagem) parent.getItemAtPosition(position);
                criarAlerta(dadosPersonagem.getTitulo(), dadosPersonagem.getDescricao(), dadosPersonagem.getIcone());
            }
        });




        int i=0;

        for(String nome:listaNomes){
            try {
                if(i < listaNomes.length){
                    DadosPersonagem dadosPersonagem = new DadosPersonagem(listaIcones[i], listaNomes[i], listaDescricao[i]);
                    meuAdaptador.add(dadosPersonagem);
                    i++;
                }
                else {
                    break;
                }
            } catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        minhaLista.setAdapter(meuAdaptador);
    }
    public void criarAlerta(String title, String message, int icon){
        AlertDialog.Builder meuAlerta = new AlertDialog.Builder(MainActivity.this);

        meuAlerta.setTitle(title);
        meuAlerta.setMessage(message);
        meuAlerta.setCancelable(true);
        meuAlerta.setIcon(icon);

        meuAlerta.setPositiveButton("Confirma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Confirma!", Toast.LENGTH_SHORT).show();
            }
        });
        meuAlerta.create();
        meuAlerta.show();

    }
}
class ViewPersonagem{
    ImageView icone;
    TextView titulo;
    TextView descricao;
}
class DadosPersonagem{
    private final int icone;
    private final String titulo;
    private final String descricao;

    public DadosPersonagem(int icone, String titulo, String descricao) {
        this.icone = icone;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public int getIcone() {
        return icone;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}


class MeuAdaptador extends ArrayAdapter<DadosPersonagem>{

    public MeuAdaptador(@NonNull Context context, int data) {
        super(context, R.layout.minha_celula, data);
    }

    @Override
    public void add(@Nullable DadosPersonagem object) {
        super.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View minhaView;
        minhaView = convertView;
        ViewPersonagem viewPersonagem;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            minhaView = inflater.inflate(R.layout.minha_celula,parent,false);

            viewPersonagem = new ViewPersonagem();
            viewPersonagem.icone = (ImageView) minhaView.findViewById(R.id.meuIcone);
            viewPersonagem.titulo = (TextView) minhaView.findViewById(R.id.meuTitulo);
            viewPersonagem.descricao = (TextView) minhaView.findViewById(R.id.meuDescricao);

            minhaView.setTag(viewPersonagem);
        } else{
            viewPersonagem = (ViewPersonagem) minhaView.getTag();
        }

        DadosPersonagem dadosPersonagem;
        dadosPersonagem = (DadosPersonagem) this.getItem(position);


        viewPersonagem.icone.setImageResource(dadosPersonagem.getIcone());
        viewPersonagem.titulo.setText(dadosPersonagem.getTitulo());
        viewPersonagem.descricao.setText(dadosPersonagem.getDescricao());

        return minhaView;
    }
}