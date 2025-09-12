package wepayu.models.Empregados;

import wepayu.Exception.EmpregadoNaoExisteException;

public class EmpregadoAssalariado extends Empregado {

    public EmpregadoAssalariado(String nome, String endereco, String tipo, double salario) throws EmpregadoNaoExisteException {
        super(nome, endereco, tipo, salario);
    }
}



