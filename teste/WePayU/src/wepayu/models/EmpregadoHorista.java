package wepayu.models;

import wepayu.Exception.EmpregadoNaoExisteException;

public class EmpregadoHorista  extends  Empregado {

    double salarioPorHora;

    public EmpregadoHorista() throws EmpregadoNaoExisteException {
    }

    public EmpregadoHorista(String nome, String endereco, String tipo, int salario) throws EmpregadoNaoExisteException {
        super(nome, endereco, tipo, salario);
    }
}
