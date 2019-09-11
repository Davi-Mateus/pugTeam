package model;

import java.util.Date;

public class Permissao {
	//Variaveis
	private int idPermissao;
	private String tipoPermissao;
	private String responsavel;
	private Date dataPermissao;
	private Aluno aluno;
	private Turma turma;
	private Visitante visitante;
	
	//Getters and Setters
	public int getIdPermissao() {
		return idPermissao;
	}
	public void setIdPermissao(int idPermissao) {
		this.idPermissao = idPermissao;
	}
	public String getTipoPermissao() {
		return tipoPermissao;
	}
	public void setTipoPermissao(String tipoPermissao) {
		this.tipoPermissao = tipoPermissao;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public Date getDataPermissao() {
		return dataPermissao;
	}
	public void setDataPermissao(Date dataPermissao) {
		this.dataPermissao = dataPermissao;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public Turma getTurma() {
		return turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	public Visitante getVisitante() {
		return visitante;
	}
	public void setVisitante(Visitante visitante) {
		this.visitante = visitante;
	}
}