package wepayu.models.Empregados;

import wepayu.Exception.EmpregadoNaoExisteException;

public class EmpregadoComissionado extends Empregado {

    private double taxaDecomissao;

    public double getTaxaDecomissao() {
        return taxaDecomissao;
    }

    // Construtor adaptado para tratar exceções
    public EmpregadoComissionado(String nome, String endereco, String tipo, double salario, String comissaoStr)
            throws EmpregadoNaoExisteException {

        super(nome, endereco, tipo, salario);

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
}
