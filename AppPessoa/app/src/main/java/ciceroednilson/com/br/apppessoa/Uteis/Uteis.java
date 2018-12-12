package ciceroednilson.com.br.apppessoa.Uteis;

import android.app.AlertDialog;
import android.content.Context;

import ciceroednilson.com.br.apppessoa.R;

/**
 * Created by cicero.machado on 18/03/2016.
 */
public class Uteis {


    public static void alert(Context context, String mensagem){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
        alertDialog.setTitle(R.string.app_name);

        //MENSAGEM A SER EXIBIDA
        alertDialog.setMessage(mensagem);

        //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
        alertDialog.setPositiveButton("OK", null);

        //MOSTRA A MENSAGEM NA TELA
        alertDialog.show();

    }


}
