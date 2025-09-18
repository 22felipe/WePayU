package wepayu.models.Empregados;

import wepayu.Exception.EmpregadoNaoExisteException;
import wepayu.models.registros.cartaoDePonto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmpregadoHorista  extends Empregado {

    private List<cartaoDePonto> cartaoDePontos;

    public EmpregadoHorista() throws EmpregadoNaoExisteException {
    }

    public EmpregadoHorista(String nome, String endereco, String tipo, double salario) throws EmpregadoNaoExisteException {
        super(nome, endereco, tipo, salario);
        this.cartaoDePontos=  new ArrayList<>();
    }

    public double getHorasNormaisTrabalhadas(LocalDate inicial, LocalDate fim) {
        double total = 0.0;
        for (cartaoDePonto c : cartaoDePontos) {
            LocalDate d = c.getData();
            if ((d.isEqual(inicial) || d.isAfter(inicial)) && (d.isBefore(fim))) {
                total += Math.min(c.getHoras(), 8);
            }
        }

        return total;
    }

    public double getHorasExtrasTrabalhadas(LocalDate inicial, LocalDate fim) {
        double total = 0.0;
        for (cartaoDePonto c : cartaoDePontos) {
            LocalDate d = c.getData();
            if ((d.isEqual(inicial) || d.isAfter(inicial)) && (d.isBefore(fim))) {
                if (c.getHoras() > 8) {
                    total += (c.getHoras() - 8);
                }
            }
        }
        return total;
    }

    public void adicionarCartaoDePonto(String dataStr, Double horas) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate data = LocalDate.parse(dataStr, formatter);

        if (cartaoDePontos == null) {
            cartaoDePontos = new ArrayList<>();
        }

        cartaoDePontos.add(new cartaoDePonto(data, horas));
    }

}
