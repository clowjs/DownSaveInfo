package com.example.downsaveinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // 1 - variavel EditText no Java para capturar o conteudo inserido pelo usuario (associado ao componente 'editTextXML' no Layout)
    private EditText mensagemInserida;

    // 8 - Variavel Button do Java associada ao componentes "downloadXML" no Layout
    private Button downloadButton;

    // 2 - String contendo a mensagem a ser enviada
    private String mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 3 - Associando a variavel mensagemInserida do Java com o componente EditText do arquivo XML
        mensagemInserida = findViewById(R.id.editTextXML);

        // 9 - Associando a variavel downloadbutton do Java com o componente downloadXML do arquivo XML
        downloadButton = findViewById(R.id.downloadXML);

        // 10 - Criando o metodo onClick associado ao botao para vizualizacao do Download
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalisadorXml analisadorXml = new AnalisadorXml(mensagem);
                analisadorXml.process();
                mensagem = analisadorXml.getConteudo();
                mensagemInserida.setText(mensagem);
            }
        });

        // ? - Criando uma instancia do AsyncTask e executando a tarefa utilizando o metodo execute()
        MinhaAsync minhaAsync = new MinhaAsync();
        minhaAsync.execute("https://www.w3schools.com/xml/note.xml");
    }
    // 4 - Criando o metodo para envio da mensagem para a nova tela
    public void disparoNovaTela(View v) {

        // 4.1 - Atribuicao do valor digitado pelo usuario no campo EditTextXML para a variavle mensagem
        mensagem = mensagemInserida.getText().toString();

        // 4.2 - Criação da Intent para chamda da segunda tela com a mensagem enviada
        Intent myIntent = new Intent(this, Tela2.class);

        // 4.3 - Insere a mensagem na intent utilizando o metodo putExtra()
        myIntent.putExtra("mensagemEnviada", mensagem);

        // 4.3 - Inicia a activity Tela2
        startActivity(myIntent);

    }

    // 5 - Criando o metodo para realizar o download do conteudo XML do site, utilizando a classe HttpURLConnection
    private String downloadXMLFile(String theUrl) {
        try {
            // Cria uma instancia da classe URL com a url que sera utilizada para fazer o download
            URL myUrl = new URL(theUrl);

            // Abre a conexao
            HttpURLConnection myConnection = (HttpURLConnection) myUrl.openConnection();

            // Verifica se conexao foi bem sucedida e exibe no Logcat
            int response = myConnection.getResponseCode();
            Log.d("Download", "The response code is: " + response);

            // Cria a variavel data que conterá o conteudo do stream de bytes
            InputStream data = myConnection.getInputStream();

            // Usa a classe InputStreamReader para converter de bytes para chars
            InputStreamReader caracteres = new InputStreamReader(data);

            // Criacao de uma array de char para leitura de 500 em 500 caracteres
            char[] inputBuffer = new char[500];

            // Criacao de uma instanca da classe StringBuilder para formar a string final de interesse
            StringBuilder tempBuffer = new StringBuilder();

            // Criacao de uma variavel de controle que conte os caracteres lidos dentro do laco while para formacao da string
            int charRead;

            // Laço de leitura e formaçao da String
            while (true) {
                // le os caracteres com tamanho maximo de inputBuffer(500) e informa o numero lido
                charRead = caracteres.read(inputBuffer);

                // se for -1 nao ha mais caracteres e sai do laço
                if (charRead <= 0) {
                    break;
                }

                tempBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));

            }
            return tempBuffer.toString();

            // Como os metodos openConnection(), getResponseCode() e getInputStream() podem lancar execoes
            // de IO pois interagem com recursos externos, precisamos do bloco catch para capturar
            // eventuais excecoes
        } catch (IOException e) {
            Log.d("Download", "IO Exception durante a execução: " + e.getMessage());
        }

        // e nesse caso o metodo returnar null
        return null;
    }
    // Precisamos dar permissao de acesso a internet ao aplicativo no arquvio Manifest
    // <uses-permission android:name="android.permission.INTERNET"></uses-permission>

    // 6 - Criamos a classe que extende AsyncTask, que através de uma
    // thread paralela fara o chamado do metodo DownloadXMLFile para baixarmos o conteudo
    // do feed

    private class MinhaAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // Evocamos o metodo DownloadXMLFile passando a URL como parametro
            mensagem = downloadXMLFile(params[0]);

            // Avisa logando no Logcat casso tenha ocorrido um problema fazendo o download
            if (mensagem == null) {
                Log.d("Download", "Erro fazendo download.");
            }

            return mensagem;
        }
    }
}