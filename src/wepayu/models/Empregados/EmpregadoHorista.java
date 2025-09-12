package wepayu.models.Empregados;

import wepayu.Exception.EmpregadoNaoExisteException;

public class EmpregadoHorista  extends Empregado {

    public EmpregadoHorista() throws EmpregadoNaoExisteException {
    }

    public EmpregadoHorista(String nome, String endereco, String tipo, double salario) throws EmpregadoNaoExisteException {
        super(nome, endereco, tipo, salario);
    }
}
