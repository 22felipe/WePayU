package wepayu;

import wepayu.Exception.EmpregadoNaoExisteException;

public class Facade {

    private sistemaFolha sistema = new sistemaFolha();

    public void zerarSistema() {
        sistema.zerarSistema();
    }

    public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException {
        return sistema.getAtributo(emp, atributo);
    }

    //criar empregado assalariado e horista
    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException {
        return sistema.criarEmpregado(nome, endereco, tipo, salario);
    }

    //criar empregado comissionado
    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException {
        return sistema.criarEmpregado(nome, endereco, tipo, salario);
    }
}
