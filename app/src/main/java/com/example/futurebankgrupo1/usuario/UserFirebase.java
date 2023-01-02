package com.example.futurebankgrupo1.usuario;

public class UserFirebase {
    private String nome, idade, email, senha, cpf, telefone, cep, numero, logradouro, bairro, cidade, estado, pais;
    private float saldo = 100.0f;
    private float limiteCartao = 900.0f;
    private float saldoPoupanca = 200.0f;
    private float valorFatura; // = 55.0f;



    public UserFirebase(){

    }

    public UserFirebase(String nome, String idade, String email, String senha, String cpf, String telefone, String cep,
                String numero, String logradouro, String bairro, String cidade, String estado, String pais) {
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
        this.cep = cep;
        this.numero = numero;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public String getIdade() {
        return idade;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }

    public String getNumero() {
        return numero;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getPais() {
        return pais;
    }

    public float getSaldo() {
        return saldo;
    }

    public float getLimiteCartao() {
        return limiteCartao;
    }

    public void setLimiteCartao(float limiteCartao) {
        this.limiteCartao = limiteCartao;
    }

    public float getSaldoPoupanca() {
        return saldoPoupanca;
    }

    public void setSaldoPoupanca(float saldoPoupanca) {
        this.saldoPoupanca = saldoPoupanca;
    }

    public float getValorFatura() {
        return valorFatura;
    }

    public void setValorFatura(float valorFatura) {
        this.valorFatura = valorFatura;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
