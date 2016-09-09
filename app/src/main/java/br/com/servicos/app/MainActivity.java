package br.com.servicos.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_processar;
    EditText txt_palavra;

    ResponseReceiver receiver;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        btn_processar = (Button) findViewById(R.id.btn_processar);
        txt_palavra = (EditText) findViewById(R.id.txt_palavra);

        btn_processar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entrada = txt_palavra.getText().toString();

                Intent intent = new Intent(context, Services.class);
                intent.putExtra("entrada", entrada);

                //Inicia o serviço
                startService(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* Filter resolve(filtra) a tag do Broadcast
        * Resolve apenas um especifico*/
        IntentFilter broadcastFilter = new IntentFilter(ResponseReceiver.PACKBROADCAST);
        receiver = new ResponseReceiver();

        /*Registra quem vai receber os broadcast de acordo com o filtro*/
        LocalBroadcastManager localBroadcastManager;
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.registerReceiver(receiver, broadcastFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        /*Tira o registro de quem estava recebendo o serviço*/
        LocalBroadcastManager localBroadcastManager;
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.unregisterReceiver(receiver);
    }

    /* Classe para pegar o BroadCast enviado*/
    public class ResponseReceiver extends BroadcastReceiver {
        public static final String PACKBROADCAST = "br.com.servicos.app.OK";
        @Override
        public void onReceive(Context context, Intent intent) {
            String resultado = intent.getStringExtra("saida");
            Toast.makeText(context, "O resultado é:"+resultado, Toast.LENGTH_LONG).show();
        }
    }
}
