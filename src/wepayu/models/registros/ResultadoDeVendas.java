package wepayu.models.registros;

import java.time.LocalDate;

public class ResultadoDeVendas {

    private LocalDate data;
    private double valor;

    public ResultadoDeVendas(LocalDate data, double valor) {
        this.data = data;
        this.valor = valor;
    }

    public LocalDate getData() { return data; }
    public double getValor() { return valor; }
}
