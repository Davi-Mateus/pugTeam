package model;

import java.util.Date;

public class Matricula {
	//Variaveis
	private int IdMatricula;
	private Date dataMatricula;
	private Date dataCancelamentoMatricula;
	private Aluno aluno;
	private Turma turma;
	
	//Getters and Setters
	public Date getDataMatricula() {
		return dataMatricula;
	}
	public void setDataMatricula(Date dataMatricula) {
		this.dataMatricula = dataMatricula;
	}
	public Date getDataCancelamentoMatricula() {
		return dataCancelamentoMatricula;
	}
	public void setDataCancelamentoMatricula(Date dataCancelamentoMatricula) {
		this.dataCancelamentoMatricula = dataCancelamentoMatricula;
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
	public int getIdMatricula() {
		return IdMatricula;
	}
	public void setIdMatricula(int idMatricula) {
		IdMatricula = idMatricula;
	}	
}
