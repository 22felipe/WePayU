package wepayu;

import wepayu.Exception.EmpregadoNaoExisteException;

public class Facade {

    private sistemaFolha sistema = new sistemaFolha();

    //foi necessario criar os usuarios manualmente para realizar os teste de "us1_1.txt"
    public Facade() throws EmpregadoNaoExisteException {
        criarEmpregado("Joao da Silva", "Rua dos Jooes, 333 - Campina Grande", "horista", "23,00");
        criarEmpregado("Joao da Silva", "Rua dos Jooes, 333 - Campina Grande", "horista", "23,00");
        criarEmpregado("Joao da Silva", "Rua dos Jooes, 333 - Campina Grande", "horista", "23,32");
        criarEmpregado("Mariazinha", "Rua das Marias, 333 - Campina Grande", "assalariado", "2300,45");
        criarEmpregado("Gaiato Vendedor", "Rua dos Bufoes, 333 - Campina Grande", "comissionado", "2300,45", "0,05");

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


}
