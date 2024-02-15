package entities;

public class Servico {

	private String id_servico;
	private String valor;

	public Servico(String id_servico, String valor) {
		this.id_servico = id_servico;
		this.valor = valor;
	}

	public String getId_servico() {
		return id_servico;
	}

	public void setId_servico(String id_servico) {
		this.id_servico = id_servico;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
