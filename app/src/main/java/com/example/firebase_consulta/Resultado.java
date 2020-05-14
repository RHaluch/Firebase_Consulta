package com.example.firebase_consulta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Resultado extends AppCompatActivity {

    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        Bundle info = getIntent().getExtras();
        resultado = findViewById(R.id.resultado);
        resultado.setText(info.getString("resultado"));

    }

    public void voltarPrincipal(View view) {
        Intent telaPrincipal = new Intent(Resultado.this,MainActivity.class);
        startActivity(telaPrincipal);
        finish();
    }
}
