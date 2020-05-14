package com.example.firebase_consulta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editNome, faixaMenor, faixaMaior, salarioMaior, filhosMaior;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = findViewById(R.id.editNome);
        faixaMenor = findViewById(R.id.faixaMenor);
        faixaMaior = findViewById(R.id.faixaMaior);
        salarioMaior = findViewById(R.id.salarioMaior);
        filhosMaior = findViewById(R.id.filhosMaior);

        findViewById(R.id.btnNome).setOnClickListener(this);
        findViewById(R.id.btnSalario).setOnClickListener(this);
        findViewById(R.id.btnPets).setOnClickListener(this);
        findViewById(R.id.btnSalarioFilhos).setOnClickListener(this);

    }

    @Override
    protected void onStart(){
        super.onStart();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View view) {
        Query query;
        CollectionReference pessoas = db.collection("exemplo");
        switch (view.getId()) {
            case R.id.btnNome:
                query = pessoas.whereEqualTo("nome", editNome.getText().toString());
                break;

            case R.id.btnSalario:
                query = pessoas.whereGreaterThan("salario", Double.parseDouble(faixaMenor.getText().toString())).
                        whereLessThan("salario", Double.parseDouble(faixaMaior.getText().toString()));
                break;

            case R.id.btnPets:
                query = pessoas;
                break;

            case R.id.btnSalarioFilhos:
                query = pessoas.whereGreaterThan("salario", Double.parseDouble(salarioMaior.getText().toString()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        buscarDados(query, view.getId());
    }

    private void buscarDados(Query query, final int idBotao){
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<Pessoa> lista = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Pessoa p = new Pessoa();
                        p.setAtivo(doc.getBoolean("ativo"));
                        p.setDataAniversario(doc.getDate("dataAniversario"));
                        p.setNome(doc.getString("nome"));
                        p.setPets((List) doc.get("pets"));
                        p.setSalario(doc.getDouble("salario"));
                        p.setQtde_filhos(Math.toIntExact(doc.getLong("qtde_filhos")));
                        if(idBotao!=R.id.btnSalarioFilhos || (p.getQtde_filhos() > Integer.parseInt(filhosMaior.getText().toString()))) {
                            lista.add(p);
                        }
                    }

                    if(idBotao==R.id.btnPets){
                        Set<Pessoa> pets = new HashSet<>();
                        for (int i = 0; i<lista.size()-1; i++) {
                            for (int j = 0; j < lista.get(i).getPets().size(); j++) {
                                for (int k = i + 1; k < lista.size(); k++) {
                                    for (int l = 0; l < lista.get(k).getPets().size(); l++) {
                                        if (lista.get(i).getPets().get(j).toString().contains(lista.get(k).getPets().get(l).toString())) {
                                            pets.add(lista.get(i));
                                            pets.add(lista.get(k));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        lista.clear();
                        lista.addAll(pets);
                    }

                    String resultado = "";
                    if(lista.isEmpty()){
                        resultado = "Não foram encontrados resultados!";
                    }else{
                        resultado = lista.toString();
                    }

                    Intent telaResultado = new Intent(MainActivity.this, Resultado.class);
                    Bundle info = new Bundle();
                    info.putString("resultado", resultado);
                    telaResultado.putExtras(info);
                    startActivity(telaResultado);
                    finish();
                }
            }
        });
    }
}
