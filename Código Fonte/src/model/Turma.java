package model;

import java.util.ArrayList;
import java.util.Date;

public class Turma {
	//Variaveis
	private int idTurma;
	private String nomeTurma;
	private int ano;
	private Date dataInicio;
	private Date dataFinal;
	private ArrayList<Aluno> aluno;
	private Curso curso;
	private ArrayList<Horario> horarios;
	private ArrayList<Permissao> permissoes;
	private ArrayList<Matricula> matriculas;
	
	//Getters and Setters
	public int getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}
	public String getNomeTurma() {
		return nomeTurma;
	}
	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public ArrayList<Aluno> getAluno() {
		return aluno;
	}
	public void setAluno(ArrayList<Aluno> aluno) {
		this.aluno = aluno;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public ArrayList<Horario> getQuadrosHorarios() {
		return horarios;
	}
	public void setQuadrosHorarios(ArrayList<Horario> quadrosHorarios) {
		this.horarios = quadrosHorarios;
	}
	public ArrayList<Permissao> getPermissoes() {
		return permissoes;
	}
	public void setPermissoes(ArrayList<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
	public ArrayList<Matricula> getMatriculas() {
		return matriculas;
	}
	public void setMatriculas(ArrayList<Matricula> matriculas) {
		this.matriculas = matriculas;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String toString() {
		return nomeTurma + " - " + ano;
	}
}