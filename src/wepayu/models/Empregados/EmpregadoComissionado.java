package wepayu.models.Empregados;

import wepayu.Exception.EmpregadoNaoExisteException;
import wepayu.models.registros.ResultadoDeVendas;
import wepayu.models.registros.cartaoDePonto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmpregadoComissionado extends Empregado {

    private List<ResultadoDeVendas> ResultadoDeVendas;

    private double taxaDecomissao;

    public double getTaxaDecomissao() {
        return taxaDecomissao;
    }

    // Construtor adaptado para tratar exceções
    public EmpregadoComissionado(String nome, String endereco, String tipo, double salario, String comissaoStr)
            throws EmpregadoNaoExisteException {

        super(nome, endereco, tipo, salario);
        this.ResultadoDeVendas = new ArrayList<>();

        if (comissaoStr == null || comissaoStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Comissao nao pode ser nula.");
        }

        double taxa;
        try {
            taxa = Double.parseDouble(comissaoStr.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Comissao deve ser numerica.");
        }

        if (taxa < 0) {
            throw new IllegalArgumentException("Comissao deve ser nao-negativa.");
        }

        this.taxaDecomissao = taxa;
    }

    @Override
    public String getAtributo(String atributo) {
        if ("comissao".equalsIgnoreCase(atributo)) {
            return String.format("%.2f", getTaxaDecomissao());
        }
        // para os outros atributos, usa a lógica já definida em Empregado
        return super.getAtributo(atributo);
    }

    public void adicionarResultadoDeVendas(String dataStr, Double valor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate data = LocalDate.parse(dataStr, formatter);

        if (ResultadoDeVendas == null) {
            ResultadoDeVendas = new ArrayList<>();
        }

        ResultadoDeVendas.add(new ResultadoDeVendas(data, valor));
    }

    public double getVendas(LocalDate inicial, LocalDate fim){
        double total = 0.0;
        for (ResultadoDeVendas c : ResultadoDeVendas) {
            LocalDate d = c.getData();
            if ((d.isEqual(inicial) || d.isAfter(inicial)) && (d.isBefore(fim))) {
                    total += c.getValor();

            }
        }
        return total;
    }

}
