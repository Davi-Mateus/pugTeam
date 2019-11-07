package model;

import java.util.ArrayList;

public class Aluno{
	//Variaveis
	private int idAluno;
	private String nomeAluno;
	private Long numeroMatricula;
	private ArrayList<Turma> turmas;
	private ArrayList<Permissao> permissoes;
	private ArrayList<Registro> registros;
	
	//Getters and Setters
	public int getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}
	public String getNomeAluno() {
		return nomeAluno;
	}
	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}
	public Long getNumeroMatricula() {
		return numeroMatricula;
	}
	public void setNumeroMatricula(Long numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}
	public ArrayList<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(ArrayList<Turma> turmas) {
		this.turmas = turmas;
	}
	public ArrayList<Permissao> getPermissoes() {
		return permissoes;
	}
	public void setPermissoes(ArrayList<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
	public ArrayList<Registro> getRegistros() {
		return registros;
	}
	public void setRegistros(ArrayList<Registro> registros) {
		this.registros = registros;
	}
	public String toString() {
		return nomeAluno + " - " + numeroMatricula;
	}
}
