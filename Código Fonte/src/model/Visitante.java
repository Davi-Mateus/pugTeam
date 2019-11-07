package model;

public class Visitante {
	//Variaveis
	private int idVisitante;
	private String nomeVisitante;
	private String motivoVisita;
	private Permissao permissao;
	
	//Getters and Setters
	public int getIdVisitante() {
		return idVisitante;
	}
	public void setIdVisitante(int idVisitante) {
		this.idVisitante = idVisitante;
	}
	public String getNomeVisitante() {
		return nomeVisitante;
	}
	public void setNomeVisitante(String nomeVisitante) {
		this.nomeVisitante = nomeVisitante;
	}
	public String getMotivoVisita() {
		return motivoVisita;
	}
	public void setMotivoVisita(String motivoVisita) {
		this.motivoVisita = motivoVisita;
	}
	public Permissao getPermissao() {
		return permissao;
	}
	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}
	public String toString() {
		return nomeVisitante;
	}
}