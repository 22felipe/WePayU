package wepayu;

import wepayu.Exception.EmpregadoNaoExisteException;
import wepayu.models.Empregados.Empregado;
import wepayu.models.Empregados.EmpregadoAssalariado;
import wepayu.models.Empregados.EmpregadoComissionado;
import wepayu.models.Empregados.EmpregadoHorista;


import java.util.HashMap;
import java.util.LinkedHashMap;

public class sistemaFolha {
    //private   HashMap <String, Empregado> empregados =  new HashMap<>();
    private LinkedHashMap<String, Empregado> empregados = new LinkedHashMap<>();

    //Limpa o sistema
    public void zerarSistema() {
        empregados.clear();
    }

    // Busca um atributo de um empregado
    public String getAtributo(String empId, String atributo) throws wepayu.Exception.EmpregadoNaoExisteException {
        Empregado e = empregados.get(empId);

        if (empId == null || empId.trim().isEmpty()) {
            throw new RuntimeException("Identificacao do empregado nao pode ser nula.");
        }

        if (e == null) {
            throw new wepayu.Exception.EmpregadoNaoExisteException();
        }

        return e.getAtributo(atributo); // Chamaremos outra versão de getAtributo no próprio Empregado
    }

    //criarEmpregado assalariado e horista
    public String criarEmpregado (String nome, String endereco, String tipo, String salarioStr) throws EmpregadoNaoExisteException {

        if("comissionado".equalsIgnoreCase(tipo))
            throw new RuntimeException(("Tipo nao aplicavel."));
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

        Empregado e;

        // Decide qual tipo de empregado criar
        switch (tipo.toLowerCase()) {
            case "assalariado":
                e = new EmpregadoAssalariado(nome, endereco, "assalariado", salario);
                break;
            case "horista":
                e = new EmpregadoHorista(nome, endereco,"horista", salario); // aqui salário = valor hora
                break;
            default:
                throw new RuntimeException("Tipo invalido.");
        }

        empregados.put(e.getEmp(), e);
        return e.getEmp(); // retorna o id único
    }

    //criarEmpregadoComissionado
    public String criarEmpregado (String nome, String endereco, String tipo, String salarioStr, String comissaoStr) throws EmpregadoNaoExisteException {

        if("horista".equalsIgnoreCase(tipo))
            throw new RuntimeException(("Tipo nao aplicavel."));
        if("assalariado".equalsIgnoreCase(tipo))
            throw new RuntimeException(("Tipo nao aplicavel."));
        if (nome == null || nome.isEmpty())
            throw new RuntimeException("Nome nao pode ser nulo.");
        if (endereco == null || endereco.isEmpty())
            throw new RuntimeException("Endereco nao pode ser nulo.");
        if (tipo == null || tipo.isEmpty())
            throw new RuntimeException("Tipo invalido.");
        if (salarioStr == null || salarioStr.isEmpty())
            throw new RuntimeException("Salario nao pode ser nulo.");
        if (comissaoStr == null || comissaoStr.isEmpty())
            throw new RuntimeException("Comissao nao pode ser nula.");

        // Substitui vírgula por ponto em salario
        salarioStr = salarioStr.replace(',', '.');

        double salario;
        try {
            salario = Double.parseDouble(salarioStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Salario deve ser numerico.");
        }

        if (salario < 0) throw new RuntimeException("Salario deve ser nao-negativo.");

        // Substitui vírgula por ponto em comissao
        comissaoStr = comissaoStr.replace(',', '.');

        Empregado e;

        e = new EmpregadoComissionado(nome, endereco, "comissionado",salario, comissaoStr);


        empregados.put(e.getEmp(), e);
        return e.getEmp(); // retorna o id único
    }

    public String getEmpregadoPorNome(String nome, int indice) {
        if (nome == null) {
            throw new RuntimeException("Nome nao pode ser nulo.");
        }
        if (nome.trim().isEmpty()) {
            throw new RuntimeException("Empregado nao existe.");
        }

        int count = 0;
        for (Empregado e : empregados.values()) {
            if (e.getNome().contains(nome)) {
                count++;
                if (count == indice) {
                    return e.getEmp();
                }
            }
        }
        throw new RuntimeException("Nao ha empregado com esse nome.");
    }

    public String removerEmpregado(String emp) {
        if (emp == null || emp.trim().isEmpty()) {
            throw new RuntimeException("Identificacao do empregado nao pode ser nula.");
        }

        Empregado removido = empregados.remove(emp);
        if (removido == null) {
            throw new RuntimeException("Empregado nao existe.");
        }

        return emp; // retorna o ID do empregado removido
    }

}

