package ciceroednilson.com.br.apppessoa.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ciceroednilson.com.br.apppessoa.Uteis.DatabaseUtil;
import ciceroednilson.com.br.apppessoa.model.PessoaModel;

/**
 * Created by cicero.machado on 18/03/2016.
 */
public class PessoaRepository {

    DatabaseUtil databaseUtil;

    /***
     * CONSTRUTOR
     * @param context
     */
    public PessoaRepository(Context context){

        databaseUtil =  new DatabaseUtil(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param pessoaModel
     */
    public void salvar(PessoaModel pessoaModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("ds_nome",       pessoaModel.getNome());
        contentValues.put("ds_endereco",   pessoaModel.getEndereco());
        contentValues.put("fl_sexo",       pessoaModel.getSexo());
        contentValues.put("dt_nascimento", pessoaModel.getDataNascimento());
        contentValues.put("fl_estadoCivil",pessoaModel.getEstadoCivil());
        contentValues.put("fl_ativo",      pessoaModel.getRegistroAtivo());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        databaseUtil.getConexaoDataBase().insert("tb_pessoa",null,contentValues);

    }

    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param pessoaModel
     */
    public void atualizar(PessoaModel pessoaModel){

        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("ds_nome",       pessoaModel.getNome());
        contentValues.put("ds_endereco",   pessoaModel.getEndereco());
        contentValues.put("fl_sexo",       pessoaModel.getSexo());
        contentValues.put("dt_nascimento", pessoaModel.getDataNascimento());
        contentValues.put("fl_estadoCivil",pessoaModel.getEstadoCivil());
        contentValues.put("fl_ativo",      pessoaModel.getRegistroAtivo());

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.getConexaoDataBase().update("tb_pessoa", contentValues, "id_pessoa = ?", new String[]{Integer.toString(pessoaModel.getCodigo())});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */
    public Integer excluir(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtil.getConexaoDataBase().delete("tb_pessoa","id_pessoa = ?", new String[]{Integer.toString(codigo)});
    }

    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param codigo
     * @return
     */
    public PessoaModel getPessoa(int codigo){


        Cursor cursor =  databaseUtil.getConexaoDataBase().rawQuery("SELECT * FROM tb_pessoa WHERE id_pessoa= "+ codigo,null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        PessoaModel pessoaModel =  new PessoaModel();

        //ADICIONANDO OS DADOS DA PESSOA
        pessoaModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_pessoa")));
        pessoaModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));
        pessoaModel.setEndereco(cursor.getString(cursor.getColumnIndex("ds_endereco")));
        pessoaModel.setSexo(cursor.getString(cursor.getColumnIndex("fl_sexo")));
        pessoaModel.setDataNascimento(cursor.getString(cursor.getColumnIndex("dt_nascimento")));
        pessoaModel.setEstadoCivil(cursor.getString(cursor.getColumnIndex("fl_estadoCivil")));
        pessoaModel.setRegistroAtivo((byte)cursor.getShort(cursor.getColumnIndex("fl_ativo")));

        //RETORNANDO A PESSOA
        return pessoaModel;

    }

    /***
     * CONSULTA TODAS AS PESSOAS CADASTRADAS NA BASE
     * @return
     */
    public List<PessoaModel> selecionarTodos(){

        List<PessoaModel> pessoas = new ArrayList<PessoaModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id_pessoa,      ");
        stringBuilderQuery.append("        ds_nome,        ");
        stringBuilderQuery.append("        ds_endereco,    ");
        stringBuilderQuery.append("        fl_sexo,        ");
        stringBuilderQuery.append("        dt_nascimento,  ");
        stringBuilderQuery.append("        fl_estadoCivil, ");
        stringBuilderQuery.append("        fl_ativo        ");
        stringBuilderQuery.append("  FROM  tb_pessoa       ");
        stringBuilderQuery.append(" ORDER BY ds_nome       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.getConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        PessoaModel pessoaModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVA PESSOAS */
            pessoaModel =  new PessoaModel();

            //ADICIONANDO OS DADOS DA PESSOA
            pessoaModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_pessoa")));
            pessoaModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));
            pessoaModel.setEndereco(cursor.getString(cursor.getColumnIndex("ds_endereco")));
            pessoaModel.setSexo(cursor.getString(cursor.getColumnIndex("fl_sexo")));
            pessoaModel.setDataNascimento(cursor.getString(cursor.getColumnIndex("dt_nascimento")));
            pessoaModel.setEstadoCivil(cursor.getString(cursor.getColumnIndex("fl_estadoCivil")));
            pessoaModel.setRegistroAtivo((byte)cursor.getShort(cursor.getColumnIndex("fl_ativo")));

            //ADICIONANDO UMA PESSOA NA LISTA
            pessoas.add(pessoaModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return pessoas;

    }
}
