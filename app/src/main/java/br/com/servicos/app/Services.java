package br.com.servicos.app;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by 15160210 on 26/08/2016.
 */
public class Services extends IntentService {
    public Services() {
        /*Por padrão é passado o nome da classe*/
        super("Services");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String entrada = intent.getStringExtra("entrada");
        /*Contabiliza 5 segundos */
        SystemClock.sleep(15000);

        String saida = entrada.toLowerCase();

        /*Broadcast - envia um sinal para todos os aplicativos do sistema android*/
        Intent broadcastIntent = new Intent();
        broadcastIntent.putExtra("saida", saida);

        /*Ação para enviar o Broadcast*/
        /*O aplicativo que tiver a ação para pegar esse pacote "br.com.servicos.app.OK" poderá ter o conteudo do mesmo*/
        broadcastIntent.setAction(MainActivity.ResponseReceiver.PACKBROADCAST);

        /*Componente Broadcast*/
        LocalBroadcastManager localBroadcastManager;
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        /*Envia o broadcast*/
        localBroadcastManager.sendBroadcast(broadcastIntent);

        EnviarNotificacao(saida);
    }
    private void EnviarNotificacao(String saida){
        NotificationCompat.Builder nb;
        nb = new NotificationCompat.Builder(this);

        nb.setSmallIcon(R.drawable.ic_assignment_black_24dp)
            .setContentTitle("Notificação")
            .setContentText(saida);

        NotificationManager notificationManager;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, nb.build());
    }
}
