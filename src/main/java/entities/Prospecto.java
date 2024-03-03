package entities;

import java.io.Serializable;

public class Prospecto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nome_razaosocial;
	private String email;
	private String telefone;
	private String cpf_cnpj;
	private String cep;
	private String tipo_pessoa;
	private String bairro;
	private String endereco;
	private String numero;

	//Composição com serviço
	private Servico servico;
	

	public Prospecto() {
	}

	public Prospecto(String nome_razaosocial, String email, String telefone, String cpf_cnpj, String cep,
			String tipo_pessoa, String bairro, String endereco, String numero, Servico servico) {
		this.nome_razaosocial = nome_razaosocial;
		this.email = email;
		this.telefone = telefone;
		this.cpf_cnpj = cpf_cnpj;
		this.cep = cep;
		this.tipo_pessoa = tipo_pessoa;
		this.bairro = bairro;
		this.endereco = endereco;
		this.numero = numero;
		this.servico = servico;
	}

	public String getNome_razaosocial() {
		return nome_razaosocial;
	}

	public void setNome_razaosocial(String nome_razaosocial) {
		this.nome_razaosocial = nome_razaosocial;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf_cnpj() {
		return cpf_cnpj;
	}

	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTipo_pessoa() {
		return tipo_pessoa;
	}

	public void setTipo_pessoa(String tipo_pessoa) {
		this.tipo_pessoa = tipo_pessoa;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	@Override
	public String toString() {
		return "Prospecto [nome_razaosocial=" + nome_razaosocial + ", email=" + email + ", telefone=" + telefone
				+ ", cpf_cnpj=" + cpf_cnpj + ", cep=" + cep + ", tipo_pessoa=" + tipo_pessoa + ", bairro=" + bairro
				+ ", endereco=" + endereco + ", numero=" + numero + ", servico=" + servico + "]";
	}

	
}
