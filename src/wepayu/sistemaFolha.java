package wepayu;

import wepayu.Exception.EmpregadoNaoExisteException;
import wepayu.models.Empregados.Empregado;
import wepayu.models.Empregados.EmpregadoAssalariado;
import wepayu.models.Empregados.EmpregadoComissionado;
import wepayu.models.Empregados.EmpregadoHorista;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class sistemaFolha {
    //private   HashMap <String, Empregado> empregados =  new HashMap<>();
    private LinkedHashMap<String, Empregado> empregados = new LinkedHashMap<>();

    //transformar uma string em um Localdate
    private LocalDate parseData(String dataStr, String msgErro) {
        try {
            String[] partes = dataStr.split("/");
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int ano = Integer.parseInt(partes[2]);
            return LocalDate.of(ano, mes, dia);
        } catch (Exception e) {
            throw new RuntimeException(msgErro);
        }
    }

    //Retorna um inteiro, se tiver casas decimais nulas, ou um double se tiver valores decimais
    private String formatHoras(double valor) {
        if(valor == 0) return "0";
        if(valor == Math.floor(valor)) return String.valueOf((int) valor);
        return String.valueOf(valor).replace('.', ',');  // substitui ponto por vírgula
    }

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
            if (e.getNome().equals(nome)) {
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

    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) {
        Empregado e = empregados.get(emp);
        if (!(e instanceof EmpregadoHorista)) {
            throw new RuntimeException("Empregado nao eh horista.");
        }

        LocalDate ini = parseData(dataInicial, "Data inicial invalida.");
        LocalDate fim = parseData(dataFinal, "Data final invalida.");
        if (ini.isAfter(fim)) {
            throw new RuntimeException("Data inicial nao pode ser posterior aa data final.");
        }

        double horas = ((EmpregadoHorista) e).getHorasNormaisTrabalhadas(ini, fim);
        String horasStr = formatHoras(horas);
        String.valueOf(horas).replace('.', ',');  // substitui ponto por vírgula
        return horasStr;
    }

    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) {
        Empregado e = empregados.get(emp);
        if (!(e instanceof EmpregadoHorista)) {
            throw new RuntimeException("Empregado nao eh horista.");
        }

        LocalDate ini = parseData(dataInicial, "Data inicial invalida.");
        LocalDate fim = parseData(dataFinal, "Data final invalida.");
        if (ini.isAfter(fim)) {
            throw new RuntimeException("Data inicial nao pode ser posterior aa data final.");
        }

        double horas = ((EmpregadoHorista) e).getHorasExtrasTrabalhadas(ini, fim);
        String horasStr = formatHoras(horas);
        String.valueOf(horas).replace('.', ',');  // substitui ponto por vírgula
        return horasStr;
    }

    public String getVendasRealizadas (String emp, String dataInicial, String dataFinal){
        Empregado e = empregados.get(emp);
        if (!(e instanceof EmpregadoComissionado)) {
            throw new RuntimeException("Empregado nao eh comissionado.");
        }

        LocalDate ini = parseData(dataInicial, "Data inicial invalida.");
        LocalDate fim = parseData(dataFinal, "Data final invalida.");
        if (ini.isAfter(fim)) {
            throw new RuntimeException("Data inicial nao pode ser posterior aa data final.");
        }

        double vendas = ((EmpregadoComissionado) e).getVendas(ini, fim);
        // formatador para padrão brasileiro (vírgula decimal, ponto milhar)
        DecimalFormat df = new DecimalFormat("#0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        String vendasStr = df.format(vendas);

        return vendasStr;

    }

    public void lancaCartao (String emp, String data, String horasStr) throws EmpregadoNaoExisteException {
        Empregado e=  empregados.get(emp);

        if(emp.isEmpty()){
            throw new RuntimeException("Identificacao do empregado nao pode ser nula.");
        }
        if (e == null) {
            throw new wepayu.Exception.EmpregadoNaoExisteException();
        }
        if (!(e instanceof EmpregadoHorista)) {
            throw new RuntimeException("Empregado nao eh horista.");
        }

        //checar a data
        parseData(data, "Data invalida.");

        // Valida horas e converte vírgula para ponto
        horasStr = horasStr.replace(',', '.');
        double horas;
        try {
            horas = Double.parseDouble(horasStr);
        } catch (Exception ex) {
            throw new RuntimeException("Horas invalidas.");
        }

        if (horas <= 0) {
            throw new RuntimeException("Horas devem ser positivas.");
        }

        // Adiciona cartão de ponto
        EmpregadoHorista horista = (EmpregadoHorista) e;
        horista.adicionarCartaoDePonto(data, horas);
    }

    public void lancaVenda (String emp, String data, String valorStr) throws EmpregadoNaoExisteException {
        Empregado e=  empregados.get(emp);

        if(emp.isEmpty()){
            throw new RuntimeException("Identificacao do empregado nao pode ser nula.");
        }
        if (e == null) {
            throw new wepayu.Exception.EmpregadoNaoExisteException();
        }
        if (!(e instanceof EmpregadoComissionado)) {
            throw new RuntimeException("Empregado nao eh comissionado.");
        }

        // valida valor
        if (valorStr == null || valorStr.trim().isEmpty()) {
            throw new RuntimeException("Valor deve ser positivo.");
        }

        // converte vírgula para ponto
        valorStr = valorStr.replace(',', '.');

        double valor;
        try {
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Valor deve ser numerico.");
        }

        if (valor <= 0) {
            throw new RuntimeException("Valor deve ser positivo.");
        }

        // valida tipo
        if (!(e instanceof EmpregadoComissionado)) {
            throw new RuntimeException("Empregado nao eh comissionado.");
        }

        // valida data
        parseData(data, "Data invalida.");

        // adiciona a venda
        EmpregadoComissionado comissionado = (EmpregadoComissionado) e;
        comissionado.adicionarResultadoDeVendas(data, valor);
    }
}

