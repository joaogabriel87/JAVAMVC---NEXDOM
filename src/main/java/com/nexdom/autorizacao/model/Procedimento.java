package com.nexdom.autorizacao.model;


public class Procedimento {
    private Long id;
    private String codigo;
    private Integer idade;
    private String sexo;
    private Boolean permitido;

    public Procedimento() {
    }

    public Procedimento(Long id, String codigo, Integer idade, String sexo, Boolean permitido) {
        this.id = id;
        this.codigo = codigo;
        this.idade = idade;
        this.sexo = sexo;
        this.permitido = permitido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Boolean getPermitido() {
        return permitido;
    }

    public void setPermitido(Boolean permitido) {
        this.permitido = permitido;
    }
}


