package com.example.downsaveinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Tela2 extends AppCompatActivity {

    // 1 - Variavel que vai conter a mensagem enviada/recebida
    String mensagemRecebida;

    // 2 - Variavel do tipo TextView no codigo Java associada ao TextView mensagemRecebidaXML no Layout
    TextView exibeMensagem;

    // 6 - Criacao das variaveis para os botoes salvar, apagar e visualizar
    private Button salvar;
    private Button apagar;
    private Button visualizar;

    // 9 - criacao da variavel do banco de dados
    private BancoDeDados db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela2);

        // 3 - Associa a variavel mensagemInserida ao Java com o TextView "mensagemRecebidaXML" do arquivo XML
        exibeMensagem = findViewById(R.id.mensagemRecebidaXML);

        // 8 - Associacao das variaveis salvar, apagar e visualizar
        salvar = findViewById(R.id.salvarButton);
        apagar = findViewById(R.id.apagarButton);
        visualizar = findViewById(R.id.visualizarButton);

        // 10 - Criacao da variavel do banco de dados
        db = new BancoDeDados(this);

        // 4 - Utilizamos o metodo getExtras() para recuperar a mensagem enviada
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        String mensagemRecebida = (String) bd.get("mensagemEnviada");

        // 5 - Exibe a mensagem na Tela2
//        exibeMensagem.setText(mensagemRecebida);

        // 11 - Criacao do metodo onClick associado ao botao salvar
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = db.salvaMensagem(mensagemRecebida);

                if (id != -1)
                    Toast.makeText(Tela2.this, "Mensagem salva com sucesso!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Tela2.this, "Erro ao salvar mensagem!", Toast.LENGTH_LONG).show();
            }
        });

        // 12 - Criacao do metodo onClick associado ao botao Visualizar
        visualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibeMensagem.setText(db.findAll());
            }
        });

        // 13 - Criacao do metodo onClick associado ao botao Apagar
        apagar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count = db.apagaMensagem();
                if (count == 0) {
                    Toast.makeText(Tela2.this, "Nenhuma mensagem para apagar!", Toast.LENGTH_SHORT).show();
                    exibeMensagem.setText("");
                } else {
                    Toast.makeText(Tela2.this, "Mensagem apagada!", Toast.LENGTH_SHORT).show();
                    exibeMensagem.setText("");
                }
            }
        });
    }
}