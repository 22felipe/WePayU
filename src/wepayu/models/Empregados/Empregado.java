package wepayu.models.Empregados;

import wepayu.Exception.EmpregadoNaoExisteException;

import java.util.UUID;



public class Empregado {

    private String emp;
    private String nome;
    private String endereco;
    private String tipo;
    private double salario;
    private boolean sindicalizado = false; //o empregado sempre começa nao associado

    public Empregado() throws EmpregadoNaoExisteException {

    }

    public Empregado(String nome, String endereco, String tipo, double salario) throws EmpregadoNaoExisteException {
        this.emp = UUID.randomUUID().toString();
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
    }

    // Retorna atributo pelo nome
    public String getAtributo(String atributo) {
        switch (atributo.toLowerCase()) {
            case "emp":
                return emp;
            case "nome":
                return nome;
            case "endereco":
                return endereco;
            case "tipo":
                return tipo;
            case "salario":
                return String.format("%.2f", salario);
            case "sindicalizado":
                return String.valueOf(sindicalizado);
            default:
                throw new IllegalArgumentException("Atributo nao existe.");
        }
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public String getTipo() { return tipo; }
    public double getSalario() { return salario; }
    public String getEmp() { return emp; }
    public boolean isSindicalizado() { return sindicalizado; }
    public void setSindicalizado(boolean sindicalizado) { this.sindicalizado = sindicalizado; }
}
