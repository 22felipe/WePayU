package wepayu;

import wepayu.Exception.EmpregadoNaoExisteException;
import wepayu.models.Empregado;


import java.util.HashMap;

public class sistemaFolha {
    private   HashMap <String, Empregado> empregados =  new HashMap<>(); //tenho que ver o public

    //Limpa o sistema
    public void zerarSistema() {
        empregados.clear();
    }

    // Busca um atributo de um empregado
    public String getAtributo(String empId, String atributo) throws wepayu.Exception.EmpregadoNaoExisteException {
        Empregado e = empregados.get(empId);

        if (e == null) {
            throw new wepayu.Exception.EmpregadoNaoExisteException();
        }

        return e.getAtributo(atributo); // Chamaremos outra versão de getAtributo no próprio Empregado
    }

    public String criarEmpregado (String nome, String endereco, String tipo, String salarioStr) throws EmpregadoNaoExisteException {

        if (nome == null || nome.isEmpty())
            throw new RuntimeException("Nome nao pode ser nulo.");
        if (endereco == null || endereco.isEmpty())
            throw new RuntimeException("Endereco nao pode ser nulo.");
        if (tipo == null || tipo.isEmpty())
            throw new RuntimeException("Tipo invalido.");
        if (salarioStr == null || salarioStr.isEmpty())
            throw new RuntimeException("Salario nao pode ser nulo.");

        // Substitui vírgula por ponto
        salarioStr = salarioStr.replace(',', '.');

        double salario;
        try {
            salario = Double.parseDouble(salarioStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Salario deve ser numerico.");
        }

        if (salario < 0) throw new RuntimeException("Salario deve ser nao-negativo.");

        //cria um novo empregado com os parametros passados
        Empregado e = new Empregado(nome, endereco, tipo, salario);
        empregados.put(e.getEmp(), e); //colocar o empregado no Hashmap
        return e.getEmp();// retorna o id unico;

    }




}
