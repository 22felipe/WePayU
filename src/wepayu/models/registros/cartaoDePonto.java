package wepayu.models.registros;

import java.time.LocalDate;

public class cartaoDePonto {
    private LocalDate data;
    private double horas;

    public cartaoDePonto(LocalDate data, double horas) {
        this.data = data;
        this.horas = horas;
    }

    public LocalDate getData() { return data; }
    public double getHoras() { return horas; }
}