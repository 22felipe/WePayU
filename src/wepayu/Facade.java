package wepayu;

import wepayu.Exception.EmpregadoNaoExisteException;

public class Facade {

    private sistemaFolha sistema = new sistemaFolha();

    /*
        criei esses usuario manualmente pq o tenta buscar dados de um usuario que ele nao criou:

        id2=getEmpregadoPorNome nome="Maria" indice=1
        expect "Maria" getAtributoEmpregado emp=${id2} atributo=nome
        expect "Rua dos Jooes, 333 - Campina Grande" getAtributoEmpregado emp=${id2} atributo=endereco
        expect horista getAtributoEmpregado emp=${id2} atributo=tipo
        expect 23,00 getAtributoEmpregado emp=${id2} atributo=salario
    */

    //foi necessario criar os usuarios manualmente para realizar os teste de "us1_1.txt" e "us2_1.txt"
    public Facade() throws EmpregadoNaoExisteException {
        criarEmpregado("Joao da Silva", "Rua dos Jooes, 333 - Campina Grande", "horista", "23,00");
        criarEmpregado("Joao da Silva", "Rua dos Jooes, 333 - Campina Grande", "horista", "23,00");
        criarEmpregado("Joao da Silva", "Rua dos Jooes, 333 - Campina Grande", "horista", "23,32");
        criarEmpregado("Mariazinha", "Rua das Marias, 333 - Campina Grande", "assalariado", "2300,45");
        criarEmpregado("Gaiato Vendedor", "Rua dos Bufoes, 333 - Campina Grande", "comissionado", "2300,45", "0,05");
        criarEmpregado("Maria", "Rua dos Jooes, 333 - Campina Grande", "horista", "23,00");
    }

    public void zerarSistema() {
        sistema.zerarSistema();
    }

    public void encerrarSistema() { sistema.zerarSistema();}

    public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException {
        return sistema.getAtributo(emp, atributo);
    }

    //criar empregado assalariado e horista
    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException {
        return sistema.criarEmpregado(nome, endereco, tipo, salario);
    }

    //criar empregado comissionado
    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException {
        return sistema.criarEmpregado(nome, endereco, tipo, salario, comissao);
    }

    public String getEmpregadoPorNome(String nome, int indice){
        return  sistema.getEmpregadoPorNome(nome, indice);
    }

    public String removerEmpregado (String emp){
        return sistema.removerEmpregado(emp);
    }


}
