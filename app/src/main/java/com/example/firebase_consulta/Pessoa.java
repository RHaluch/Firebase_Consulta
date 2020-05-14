package com.example.firebase_consulta;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

public class Pessoa {
    private String nome;
    private double salario;
    private boolean ativo;
    private Date dataAniversario;
    private List pets;
    private int qtde_filhos;

    public Pessoa(String nome, double salario, boolean ativo, Date dataAniversario, List pets, int qtde_filhos) {
        this.nome = nome;
        this.salario = salario;
        this.ativo = ativo;
        this.dataAniversario = dataAniversario;
        this.pets = pets;
        this.qtde_filhos = qtde_filhos;
    }

    public Pessoa() {
    }

    public int getQtde_filhos() {
        return qtde_filhos;
    }

    public void setQtde_filhos(int qtde_filhos) {
        this.qtde_filhos = qtde_filhos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataAniversario() {
        return dataAniversario;
    }

    public void setDataAniversario(Date dataAniversario) {
        this.dataAniversario = dataAniversario;
    }

    public List getPets() {
        return pets;
    }

    public void setPets(List pets) {
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "Nome = " + nome + '\n' +
                "Salario = " + salario + '\n' +
                "Ativo = " + ativo + '\n' +
                "Data de aniversario = " + dataAniversario + '\n' +
                "Filhos: " + qtde_filhos + "\n" +
                "Pets = " + pets + "\n\n";
    }
}
