package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import dao.AlunoDao;
import dao.HorarioDao;
import dao.PermissaoDao;
import dao.RegistroDao;
import dao.TurmaDao;
import dao.VisitanteDao;
import model.Aluno;
import model.Horario;
import model.Permissao;
import model.Registro;
import model.Turma;
import model.Visitante;
import util.RunnableAtualizacaoAtrasados;
import util.RunnableCriacaoSchedulesDia;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JButton;

public class JanelaPrincipal {
	private int alunosDoDia;
	private JTextField lblAlunosDia;
	private JButton resetAlunosDia;
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu mnCadastrar;
	private JMenuItem mntmCadastrarCurso;
	private JMenuItem mntmCadastrarHorriosDeAula;
	private JMenuItem mntmCadastrarAluno;
	private JMenuItem mntmCadastrarTurma;
	private JMenu mnAlterar;
	private JMenuItem mntmAlterarCurso;
	private JMenuItem mntmAlterarHorriosDeAula;
	private JMenuItem mntmAlterarAluno;
	private JMenuItem mntmAlterarTurma;
	private JMenu mnExcluir;
	private JMenuItem mntmExcluirCurso;
	private JMenuItem mntmExcluirHorriosDeAula;
	private JMenuItem mntmExcluirTurma;
	private JMenu mnMatricula;
	private JMenuItem mntmMatricularAluno;
	private JMenuItem mntmCancelarMatriculaDe;
	private JMenu mnPermitir;
	private JMenuItem mntmCadastrarPermissaoAluno;
	private JMenuItem mntmCadastrarPermissaoTurma;
	private JMenuItem mntmCadastrarPermissaoVisitante;
	private JMenuItem mntmExcluirPermissaoAluno;
	private JMenuItem mntmExcluirPermissaoTurma;
	private JMenuItem mntmExcluirPermissaoVisitante;
	private JMenuItem mntmExcluirDiaDeAula;
	private Dimension tamanhoTela;
	private java.awt.List jListAlunosComPermissao;
	private java.awt.List jListTurmasComPermissao;
	private java.awt.List jListVisitantes;
	private java.awt.List jListAlunosAtrasados;
	private JLabel lblVisitantes;
	private JLabel lblAlunosPermissao;
	private JLabel lblAlunosAtrasados;
	private JLabel lblTurmasComPermissao;
	private JLabel lblResultadoRegistro;
	private JTextField inputAluno;
	private JButton btnRegistrar;
	
	private JanelaPrincipal instanciaJanelaPrincipal = this;
	private Aluno alunoSelecionado = null;
	private Timer timer = null;

	private SimpleDateFormat sdfHora = new SimpleDateFormat("HH");
	private SimpleDateFormat sdfMinuto = new SimpleDateFormat("mm");
	
	private static JanelaCadastroCurso frameJanelaCadastroCurso = null;
	private static JanelaCadastroHorario frameJanelaCadastroHorario = null;
	private static JanelaCadastroAluno frameJanelaCadastroAluno = null;
	private static JanelaCadastroTurma frameJanelaCadastroTurma = null;
	private static JanelaAlteracaoCurso frameJanelaAlteracaoCurso = null;
	private static JanelaAlteracaoHorario frameJanelaAlteracaoHorario = null;
	private static JanelaAlteracaoAluno frameJanelaAlteracaoAluno = null;
	private static JanelaAlteracaoTurma frameJanelaAlteracaoTurma = null;
	private static JanelaExclusaoCurso frameJanelaExclusaoCurso = null;
	private static JanelaExclusaoHorario frameJanelaExclusaoHorario = null;
	private static JanelaExclusaoTurma frameJanelaExclusaoTurma = null;
	private static JanelaExclusaoDiaAula frameJanelaExclusaoDiaAula = null;
	private static JanelaMatriculaAluno frameJanelaMatriculaAluno = null;
	private static JanelaCancelamentoMatriculaAluno frameJanelaCancelamentoMatriculaAluno = null;
	private static JanelaCadastroPermissaoAluno frameJanelaCadastroPermissaoAluno = null;
	private static JanelaCadastroPermissaoTurma frameJanelaCadastroPermissaoTurma = null;
	private static JanelaCadastroPermissaoVisitante frameJanelaCadastroPermissaoVisitante = null;
	private static JanelaExclusaoPermissaoAluno frameJanelaExclusaoPermissaoAluno = null;
	private static JanelaExclusaoPermissaoTurma frameJanelaExclusaoPermissaoTurma = null;
	private static JanelaExclusaoPermissaoVisitante frameJanelaExclusaoPermissaoVisitante = null;
	private static JanelaImportacao frameJanelaImportacao = null;
	
	private JMenuItem mnImportacaoCSV;
	private JMenu mnNewMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaPrincipal window = new JanelaPrincipal();
					window.frame.pack();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public JanelaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		
		tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
		alunosDoDia = 0;
		frame = new JFrame();
		frame.setSize(tamanhoTela);
		frame.setLocation(0,0);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());	
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(tamanhoTela);
		panel.setLayout(null);
		
		jListAlunosAtrasados = new java.awt.List();
		jListAlunosAtrasados.setBackground(Color.WHITE);
		jListAlunosAtrasados.setFont(new Font("Dialog", Font.BOLD, 16));
		jListAlunosAtrasados.setBounds(25, 200, 300, 450);
		jListAlunosAtrasados.setFocusable(false);
		panel.add(jListAlunosAtrasados);
		criaSchedulerPorAula();
		preencheAlunosAtrasados();
		
		jListAlunosComPermissao = new java.awt.List();
		jListAlunosComPermissao.setBackground(Color.WHITE);
		jListAlunosComPermissao.setFont(new Font("Dialog", Font.BOLD, 16));
		jListAlunosComPermissao.setBounds(365, 200, 300, 450);
		jListAlunosComPermissao.setFocusable(false);
		panel.add(jListAlunosComPermissao);
		preencheAlunosComPermissao();
		
		jListTurmasComPermissao = new java.awt.List();
		jListTurmasComPermissao.setBackground(Color.WHITE);
		jListTurmasComPermissao.setFont(new Font("Dialog", Font.BOLD, 16));
		jListTurmasComPermissao.setBounds(705, 200, 300, 450);
		jListTurmasComPermissao.setFocusable(false);
		panel.add(jListTurmasComPermissao);
		preencheTurmasComPermissao();
		
		jListVisitantes = new java.awt.List();
		jListVisitantes.setBackground(Color.WHITE);
		jListVisitantes.setFont(new Font("Dialog", Font.BOLD, 16));
		jListVisitantes.setBounds(1045, 200, 300, 450);
		jListVisitantes.setFocusable(false);
		panel.add(jListVisitantes);
		preencheVisitantesDia();
		
		lblVisitantes = new JLabel("Visitantes");
		lblVisitantes.setFont(new Font("Dialog", Font.BOLD, 19));
		lblVisitantes.setBounds(1145, 170, 201, 25);
		panel.add(lblVisitantes);
		
		lblAlunosAtrasados = new JLabel("Alunos atrasados");
		lblAlunosAtrasados.setFont(new Font("Dialog", Font.BOLD, 19));
		lblAlunosAtrasados.setBounds(95, 170, 295, 25);
		panel.add(lblAlunosAtrasados);
		
		lblAlunosPermissao = new JLabel("Alunos com permiss�o");
		lblAlunosPermissao.setFont(new Font("Dialog", Font.BOLD, 19));
		lblAlunosPermissao.setBounds(404, 170, 348, 25);
		panel.add(lblAlunosPermissao);
		
		lblAlunosDia = new JTextField("Alunos Presentes: "+Integer.toString(alunosDoDia), 30);
		lblAlunosDia.setFont(new Font("Dialog", Font.BOLD, 19));
		lblAlunosDia.setBounds(300, 100, 200, 25);
		lblAlunosDia.setEditable(false);
		panel.add(lblAlunosDia);
		
		lblTurmasComPermissao = new JLabel("Turmas com permiss�o");
		lblTurmasComPermissao.setFont(new Font("Dialog", Font.BOLD, 19));
		lblTurmasComPermissao.setBounds(740, 170, 348, 25);
		panel.add(lblTurmasComPermissao);
		
		inputAluno = new JTextField();
		inputAluno.setFont(new Font("Dialog", Font.PLAIN, 22));
		inputAluno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == 10) {
					AlunoDao alunoDao = new  AlunoDao();
					try {
						alunoSelecionado = alunoDao.pesquisarPorNumeroMatricula(Long.parseLong(inputAluno.getText()));
					} catch (NumberFormatException e) {
					}
					registra();
					return;
				}
				JPopupMenu popupMenu = new JPopupMenu();
				List<Aluno> alunos = new ArrayList<>();
				AlunoDao alunoDao = new AlunoDao();
				alunos = alunoDao.pesquisarPorNome(inputAluno.getText());
				for (Aluno aluno: alunos) {
					JMenuItem item = new JMenuItem(aluno.toString());
					popupMenu.add(item);
					item.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			                inputAluno.setText(aluno.toString());
			                alunoSelecionado = aluno;
			            }
			        });
				}
				popupMenu.show(inputAluno, 0, 25);
				inputAluno.requestFocus();
			}
		});
		inputAluno.setEnabled(true);
		inputAluno.setBounds(60, 50, 600, 30);
		panel.add(inputAluno);
		inputAluno.setColumns(10);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setEnabled(true);
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AlunoDao alunoDao = new  AlunoDao();
				try {
					alunoSelecionado = alunoDao.pesquisarPorNumeroMatricula(Long.parseLong(inputAluno.getText()));
				} catch (NumberFormatException e) {
				}
				registra();
			}	
		});
		btnRegistrar.setBounds(542, 100, 117, 25);
		panel.add(btnRegistrar);
		
		resetAlunosDia = new JButton("Resetar contagem");
		resetAlunosDia.setEnabled(true);
		resetAlunosDia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alunosDoDia = 0;
				lblAlunosDia.setText("Alunos Presentes: "+Integer.toString(alunosDoDia));
			}
		});
		resetAlunosDia.setBounds(60	, 100, 200, 25);
		panel.add(resetAlunosDia);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnCadastrar = new JMenu("Cadastrar");
		mnCadastrar.setFont(new Font("Dialog", Font.BOLD, 22));
		menuBar.add(mnCadastrar);
		
		mntmCadastrarCurso = new JMenuItem("Cadastrar curso");
		mntmCadastrarCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (frameJanelaCadastroCurso == null) {
					frameJanelaCadastroCurso = new JanelaCadastroCurso(instanciaJanelaPrincipal);
				}
				frameJanelaCadastroCurso.frame.setVisible(true);
			}
		});
		mntmCadastrarCurso.setFont(new Font("Dialog", Font.BOLD, 22));
		mnCadastrar.add(mntmCadastrarCurso);
		
		mntmCadastrarHorriosDeAula = new JMenuItem("Cadastrar hor�rios de aula");
		mntmCadastrarHorriosDeAula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaCadastroHorario == null) {
					frameJanelaCadastroHorario = new JanelaCadastroHorario(instanciaJanelaPrincipal);
				}
				frameJanelaCadastroHorario.initialize(instanciaJanelaPrincipal);
				frameJanelaCadastroHorario.frame.setVisible(true);
			}
		});
		mntmCadastrarHorriosDeAula.setFont(new Font("Dialog", Font.BOLD, 22));
		mnCadastrar.add(mntmCadastrarHorriosDeAula);
		
		mntmCadastrarAluno = new JMenuItem("Cadastrar aluno");
		mntmCadastrarAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaCadastroAluno == null) {
					frameJanelaCadastroAluno = new JanelaCadastroAluno(instanciaJanelaPrincipal);
				}
				frameJanelaCadastroAluno.initialize(instanciaJanelaPrincipal);
				frameJanelaCadastroAluno.frame.setVisible(true);
			}
		});
		mntmCadastrarAluno.setFont(new Font("Dialog", Font.BOLD, 22));
		mnCadastrar.add(mntmCadastrarAluno);
		
		mntmCadastrarTurma = new JMenuItem("Cadastrar turma");
		mntmCadastrarTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaCadastroTurma == null) {
					frameJanelaCadastroTurma = new JanelaCadastroTurma(instanciaJanelaPrincipal);
				}
				frameJanelaCadastroTurma.initialize(instanciaJanelaPrincipal);
				frameJanelaCadastroTurma.frame.setVisible(true);
			}
		});
		mntmCadastrarTurma.setFont(new Font("Dialog", Font.BOLD, 22));
		mnCadastrar.add(mntmCadastrarTurma);
		
		mnAlterar = new JMenu("Alterar");
		mnAlterar.setFont(new Font("Dialog", Font.BOLD, 22));
		menuBar.add(mnAlterar);
		
		mntmAlterarCurso = new JMenuItem("Alterar curso");
		mntmAlterarCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaAlteracaoCurso == null) {
					frameJanelaAlteracaoCurso = new JanelaAlteracaoCurso(instanciaJanelaPrincipal);
				}
				frameJanelaAlteracaoCurso.initialize(instanciaJanelaPrincipal);
				frameJanelaAlteracaoCurso.frame.setVisible(true);
			}
		});
		mntmAlterarCurso.setFont(new Font("Dialog", Font.BOLD, 22));
		mnAlterar.add(mntmAlterarCurso);
		
		mntmAlterarHorriosDeAula = new JMenuItem("Alterar hor�rios de aula");
		mntmAlterarHorriosDeAula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaAlteracaoHorario == null) {
					frameJanelaAlteracaoHorario = new JanelaAlteracaoHorario(instanciaJanelaPrincipal);
				}
				frameJanelaAlteracaoHorario.initialize(instanciaJanelaPrincipal);
				frameJanelaAlteracaoHorario.frame.setVisible(true);
			}
		});
		mntmAlterarHorriosDeAula.setFont(new Font("Dialog", Font.BOLD, 22));
		mnAlterar.add(mntmAlterarHorriosDeAula);
		
		mntmAlterarAluno = new JMenuItem("Alterar aluno");
		mntmAlterarAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaAlteracaoAluno == null) {
					frameJanelaAlteracaoAluno = new JanelaAlteracaoAluno(instanciaJanelaPrincipal);
				}
				frameJanelaAlteracaoAluno.initialize(instanciaJanelaPrincipal);
				frameJanelaAlteracaoAluno.frame.setVisible(true);
			}
		});
		mntmAlterarAluno.setFont(new Font("Dialog", Font.BOLD, 22));
		mnAlterar.add(mntmAlterarAluno);
		
		mntmAlterarTurma = new JMenuItem("Alterar turma");
		mntmAlterarTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaAlteracaoTurma == null) {
					frameJanelaAlteracaoTurma = new JanelaAlteracaoTurma(instanciaJanelaPrincipal);
				}
				frameJanelaAlteracaoTurma.initialize(instanciaJanelaPrincipal);
				frameJanelaAlteracaoTurma.frame.setVisible(true);
			}
		});
		mntmAlterarTurma.setFont(new Font("Dialog", Font.BOLD, 22));
		mnAlterar.add(mntmAlterarTurma);
		
		mnExcluir = new JMenu("Excluir");
		mnExcluir.setFont(new Font("Dialog", Font.BOLD, 22));
		menuBar.add(mnExcluir);
		
		mntmExcluirCurso = new JMenuItem("Excluir curso");
		mntmExcluirCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaExclusaoCurso == null) {
					frameJanelaExclusaoCurso = new JanelaExclusaoCurso(instanciaJanelaPrincipal);
				}
				frameJanelaExclusaoCurso.initialize(instanciaJanelaPrincipal);
				frameJanelaExclusaoCurso.frame.setVisible(true);
			}
		});
		mntmExcluirCurso.setFont(new Font("Dialog", Font.BOLD, 22));
		mnExcluir.add(mntmExcluirCurso);
		
		mntmExcluirHorriosDeAula = new JMenuItem("Excluir hor�rios de aula");
		mntmExcluirHorriosDeAula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaExclusaoHorario == null) {
					frameJanelaExclusaoHorario = new JanelaExclusaoHorario(instanciaJanelaPrincipal);
				}
				frameJanelaExclusaoHorario.initialize(instanciaJanelaPrincipal);
				frameJanelaExclusaoHorario.frame.setVisible(true);
			}
		});
		mntmExcluirHorriosDeAula.setFont(new Font("Dialog", Font.BOLD, 22));
		mnExcluir.add(mntmExcluirHorriosDeAula);
		
		mntmExcluirTurma = new JMenuItem("Excluir turma");
		mntmExcluirTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaExclusaoTurma == null) {
					frameJanelaExclusaoTurma = new JanelaExclusaoTurma(instanciaJanelaPrincipal);
				}
				frameJanelaExclusaoTurma.initialize(instanciaJanelaPrincipal);
				frameJanelaExclusaoTurma.frame.setVisible(true);
			}
		});
		mntmExcluirTurma.setFont(new Font("Dialog", Font.BOLD, 22));
		mnExcluir.add(mntmExcluirTurma);
		
		mntmExcluirDiaDeAula = new JMenuItem("Excluir dia de aula");
		mntmExcluirDiaDeAula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaExclusaoDiaAula == null) {
					frameJanelaExclusaoDiaAula = new JanelaExclusaoDiaAula(instanciaJanelaPrincipal);
				}
				frameJanelaExclusaoDiaAula.initialize(instanciaJanelaPrincipal);
				frameJanelaExclusaoDiaAula.frame.setVisible(true);
			}
		});
		mntmExcluirDiaDeAula.setFont(new Font("Dialog", Font.BOLD, 22));
		mnExcluir.add(mntmExcluirDiaDeAula);
		
		mnMatricula = new JMenu("Matr�cula");
		mnMatricula.setFont(new Font("Dialog", Font.BOLD, 22));
		menuBar.add(mnMatricula);
		
		mntmMatricularAluno = new JMenuItem("Matricular aluno");
		mntmMatricularAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaMatriculaAluno == null) {
					frameJanelaMatriculaAluno = new JanelaMatriculaAluno(instanciaJanelaPrincipal);
				}
				frameJanelaMatriculaAluno.initialize(instanciaJanelaPrincipal);
				frameJanelaMatriculaAluno.frame.setVisible(true);
			}
		});
		mntmMatricularAluno.setFont(new Font("Dialog", Font.BOLD, 22));
		mnMatricula.add(mntmMatricularAluno);
		
		mntmCancelarMatriculaDe = new JMenuItem("Cancelar matr�cula de aluno");
		mntmCancelarMatriculaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaCancelamentoMatriculaAluno == null) {
					frameJanelaCancelamentoMatriculaAluno = new JanelaCancelamentoMatriculaAluno(instanciaJanelaPrincipal);
				}
				frameJanelaCancelamentoMatriculaAluno.initialize(instanciaJanelaPrincipal);
				frameJanelaCancelamentoMatriculaAluno.frame.setVisible(true);
			}
		});
		mntmCancelarMatriculaDe.setFont(new Font("Dialog", Font.BOLD, 22));
		mnMatricula.add(mntmCancelarMatriculaDe);
		
		mnPermitir = new JMenu("Permiss�o");
		mnPermitir.setFont(new Font("Dialog", Font.BOLD, 22));
		menuBar.add(mnPermitir);
		
		mntmCadastrarPermissaoAluno = new JMenuItem("Cadastrar permiss�o aluno");
		mntmCadastrarPermissaoAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaCadastroPermissaoAluno == null) {
					frameJanelaCadastroPermissaoAluno = new JanelaCadastroPermissaoAluno(instanciaJanelaPrincipal);
				}
				frameJanelaCadastroPermissaoAluno.initialize(instanciaJanelaPrincipal);
				frameJanelaCadastroPermissaoAluno.frame.setVisible(true);
			}
		});
		mntmCadastrarPermissaoAluno.setFont(new Font("Dialog", Font.BOLD, 22));
		mnPermitir.add(mntmCadastrarPermissaoAluno);
		
		mntmCadastrarPermissaoTurma = new JMenuItem("Cadastrar permiss�o turma");
		mntmCadastrarPermissaoTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaCadastroPermissaoTurma == null) {
					frameJanelaCadastroPermissaoTurma = new JanelaCadastroPermissaoTurma(instanciaJanelaPrincipal);
				}
				frameJanelaCadastroPermissaoTurma.initialize(instanciaJanelaPrincipal);
				frameJanelaCadastroPermissaoTurma.frame.setVisible(true);
			}
		});
		mntmCadastrarPermissaoTurma.setFont(new Font("Dialog", Font.BOLD, 22));
		mnPermitir.add(mntmCadastrarPermissaoTurma);
		
		mntmCadastrarPermissaoVisitante = new JMenuItem("Cadastrar permiss�o visitante");
		mntmCadastrarPermissaoVisitante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaCadastroPermissaoVisitante == null) {
					frameJanelaCadastroPermissaoVisitante = new JanelaCadastroPermissaoVisitante(instanciaJanelaPrincipal);
				}
				frameJanelaCadastroPermissaoVisitante.initialize(instanciaJanelaPrincipal);
				frameJanelaCadastroPermissaoVisitante.frame.setVisible(true);
			}
		});
		mntmCadastrarPermissaoVisitante.setFont(new Font("Dialog", Font.BOLD, 22));
		mnPermitir.add(mntmCadastrarPermissaoVisitante);
		
		mntmExcluirPermissaoAluno = new JMenuItem("Excluir permiss�o aluno");
		mntmExcluirPermissaoAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaExclusaoPermissaoAluno == null) {
					frameJanelaExclusaoPermissaoAluno = new JanelaExclusaoPermissaoAluno(instanciaJanelaPrincipal);
				}
				frameJanelaExclusaoPermissaoAluno.initialize(instanciaJanelaPrincipal);
				frameJanelaExclusaoPermissaoAluno.frame.setVisible(true);
			}
		});
		mntmExcluirPermissaoAluno.setFont(new Font("Dialog", Font.BOLD, 22));
		mnPermitir.add(mntmExcluirPermissaoAluno);
		
		mntmExcluirPermissaoTurma = new JMenuItem("Excluir permiss�o turma");
		mntmExcluirPermissaoTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaExclusaoPermissaoTurma == null) {
					frameJanelaExclusaoPermissaoTurma = new JanelaExclusaoPermissaoTurma(instanciaJanelaPrincipal);
				}
				frameJanelaExclusaoPermissaoTurma.initialize(instanciaJanelaPrincipal);
				frameJanelaExclusaoPermissaoTurma.frame.setVisible(true);
			}
		});
		mntmExcluirPermissaoTurma.setFont(new Font("Dialog", Font.BOLD, 22));
		mnPermitir.add(mntmExcluirPermissaoTurma);
		
		mntmExcluirPermissaoVisitante = new JMenuItem("Excluir permiss�o visitante");
		mntmExcluirPermissaoVisitante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaExclusaoPermissaoVisitante == null) {
					frameJanelaExclusaoPermissaoVisitante = new JanelaExclusaoPermissaoVisitante(instanciaJanelaPrincipal);
				}
				frameJanelaExclusaoPermissaoVisitante.initialize(instanciaJanelaPrincipal);
				frameJanelaExclusaoPermissaoVisitante.frame.setVisible(true);
			}
		});
		mntmExcluirPermissaoVisitante.setFont(new Font("Dialog", Font.BOLD, 22));
		mnPermitir.add(mntmExcluirPermissaoVisitante);

		
		
		mnNewMenu = new JMenu("Importa��o");
		mnNewMenu.setFont(new Font("Dialog", Font.BOLD, 22));
		menuBar.add(mnNewMenu);
		
		mnImportacaoCSV = new JMenuItem("Importa��o CSV");
		mnImportacaoCSV.setFont(new Font("Dialog", Font.BOLD, 22));
		mnNewMenu.add(mnImportacaoCSV);
		mnImportacaoCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frameJanelaImportacao == null) {
					frameJanelaImportacao = new JanelaImportacao(instanciaJanelaPrincipal);
				}
				frameJanelaImportacao.initialize(instanciaJanelaPrincipal);
				frameJanelaImportacao.frame.setVisible(true);
			}
		});
		
		LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNext ;
        zonedNext = zonedNow.withHour(0).withMinute(0).withSecond(1);
        if(zonedNow.compareTo(zonedNext) > 0)
            zonedNext = zonedNext.plusDays(1);

        Duration duration = Duration.between(zonedNow, zonedNext);
        long initalDelay = duration.getSeconds();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler = Executors.newScheduledThreadPool(1);            
        scheduler.scheduleAtFixedRate(new RunnableCriacaoSchedulesDia(instanciaJanelaPrincipal), initalDelay, 24*60*60, TimeUnit.SECONDS);
        
        
        frame.getContentPane().add(panel);
        
        lblResultadoRegistro = new JLabel("");
        panel.add(lblResultadoRegistro, BorderLayout.CENTER);
        lblResultadoRegistro.setFont(new Font("Dialog", Font.BOLD, 24));
        lblResultadoRegistro.setBounds(800, 50, 750, 30);
        
        frame.getContentPane().add(panel);
        inputAluno.grabFocus();
	}
	
	private void mostraMensagem(String mensagem, Color color) {
		inputAluno.setText("");
		alunoSelecionado = null;
		lblResultadoRegistro.setText(mensagem);
		lblResultadoRegistro.setForeground(color);
		if (timer == null) {
			timer = new Timer(5000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblResultadoRegistro.setText("");
				}
			});
			timer.start(); 
		} else {
			if (timer.isRunning()) {
				timer.restart();
			} else {
				timer = new Timer(5000, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						lblResultadoRegistro.setText("");
					}
				});
				timer.start();
			}
		}
	}
	
	private void registra() {
		if (alunoSelecionado == null || alunoSelecionado.getIdAluno() == 0) {
			mostraMensagem("Aluno inval�do ou n�o selecionado", new Color(204, 51, 0));
			return;
		}
		//verifica horarios de aula do aluno
		HorarioDao horarioDao= new HorarioDao();
		List<Horario> horariosAluno = horarioDao.pesquisarHorariosPorAlunoDia(alunoSelecionado);
		LocalTime horaAtual = LocalTime.now();			
		
		//verifica se aluno j� entrou no colegio
		RegistroDao registroDao = new RegistroDao();
		Registro registroAlunoPresente = registroDao.pesquisarPorAlunoPresenteNoColegio(alunoSelecionado);
		PermissaoDao permissaoDao = new PermissaoDao();
		List<Permissao> permissoesAluno = permissaoDao.pesquisarPermissoesAlunoDia(alunoSelecionado);
		TurmaDao turmaDao = new TurmaDao();
		List<Turma> turmasAluno = turmaDao.pesquisarPorAluno(alunoSelecionado);
		if (!(registroAlunoPresente.getIdRegistro() == 0)) {
				//verifica se horario atual � posterior ao final da aula
			if (horaAtual.isAfter(registroAlunoPresente.getHorario().getHorarioFinalAulaLocaTime())) {
				registraSaida(registroDao, registroAlunoPresente);
				return;
			}
			for (Permissao permissao : permissoesAluno) {
				//verifica se possui permissao de saida
				if (permissao.getTipoPermissao().equals("sa�da") || permissao.getTipoPermissao().equals("entrada e sa�da")) {
					registraSaida(registroDao, registroAlunoPresente);
					return;
				}
			}
			//verifica se turmas do aluno possuem permissao saida
			for (Turma turma : turmasAluno) {
				List<Permissao> permissoesTurma = permissaoDao.pesquisarPermissoesTurmaDia(turma);
				for (Permissao permissao : permissoesTurma) {
					//verifica se possui permissao de saida
					if (permissao.getTipoPermissao().equals("sa�da") || permissao.getTipoPermissao().equals("entrada e sa�da")) {
						registraSaida(registroDao, registroAlunoPresente);
						return;
					}
				}
			}
			mostraMensagem("Sa�da n�o autorizada", new Color(204, 51, 0));
			return;
		}
		
		//verifica se hora atual est� entre o horario de inicio da aula e 1h antes para cada aula do dia do aluno
		for (Horario horario : horariosAluno) {	
			if (horaAtual.isAfter(horario.getHorarioInicioAulaLocaTime().minusHours(1)) && horaAtual.isBefore(horario.getHorarioInicioAulaLocaTime())) {
				registraEntrada(registroDao, horaAtual, horario);
				preencheAlunosAtrasados();
				return;
			}
		}
		
		for (Permissao permissao : permissoesAluno) {
			//verifica se possui permissao de entrada
			if (permissao.getTipoPermissao().equals("entrada") || permissao.getTipoPermissao().equals("entrada e sa�da")) {
				for (Horario horario : horariosAluno) {
					if (horaAtual.isAfter(horario.getHorarioInicioAulaLocaTime()) &&  horaAtual.isBefore(horario.getHorarioFinalAulaLocaTime())) {
						registraEntrada(registroDao, horaAtual, horario);
						preencheAlunosAtrasados();
						return;
					}
				}
			}
		}
		//verifica se turmas do aluno possuem permissao saida
		for (Turma turma : turmasAluno) {
			List<Permissao> permissoesTurma = permissaoDao.pesquisarPermissoesTurmaDia(turma);
			for (Permissao permissao : permissoesTurma) {
				//verifica se possui permissao de entrada
				if (permissao.getTipoPermissao().equals("entrada") || permissao.getTipoPermissao().equals("entrada e sa�da")) {
					for (Horario horario : horariosAluno) {
						if (horaAtual.isAfter(horario.getHorarioInicioAulaLocaTime()) && horaAtual.isBefore(horario.getHorarioFinalAulaLocaTime())) {
							registraEntrada(registroDao, horaAtual, horario);
							preencheAlunosAtrasados();
							return;
						}
					}
				}
			}
		}
		mostraMensagem("Entrada n�o autorizada", new Color(204, 51, 0));
		return;
	}
	
	private void registraEntrada(RegistroDao registroDao, LocalTime horaAtual, Horario horario) {
		Registro registro = new Registro();
		registro.setIdAluno(alunoSelecionado.getIdAluno());
		registro.setHoraEntrada(new Date());
		registro.setIdTurma(((Turma)horario.getTurmas().get(0)).getIdTurma());
		registro.setHorario(horario);
		registroDao.entrar(registro);
		alunosDoDia += 1;
		lblAlunosDia.setText("Alunos do dia: "+Integer.toString(alunosDoDia));
		mostraMensagem("Entrada autorizada", new Color(0, 153, 0));
		return;
	}
	
	private void registraSaida(RegistroDao registroDao, Registro registroAlunoPresente) {
		registroAlunoPresente.setHoraSaida(new Date());
		registroDao.sair(registroAlunoPresente);
		if(alunosDoDia!=0) {
		alunosDoDia -= 1; 
		}
		lblAlunosDia.setText("Alunos do dia: "+Integer.toString(alunosDoDia));
		lblAlunosDia.revalidate();
		lblAlunosDia.repaint();
		mostraMensagem("Sa�da autorizada", new Color(0, 153, 0));
		return;
	}
	
	public void preencheTurmasComPermissao() {
		jListTurmasComPermissao.removeAll();
		PermissaoDao permissaoDao = new PermissaoDao();
		List<Permissao> permissoes = permissaoDao.pesquisarTurmasComPermissaoDia();
		for (Permissao permissao : permissoes) {
			jListTurmasComPermissao.add(permissao.getTurma().toString() + " - " + permissao.getTipoPermissao());
		}
		jListTurmasComPermissao.revalidate();
		jListTurmasComPermissao.repaint();
	}
	
	public void preencheAlunosComPermissao() {
		jListAlunosComPermissao.removeAll();
		PermissaoDao permissaoDao = new PermissaoDao();
		List<Permissao> permissoes = permissaoDao.pesquisarAlunosComPermissaoDia();
		for (Permissao permissao : permissoes) {
			jListAlunosComPermissao.add(permissao.getAluno().toString() + " - " + permissao.getTipoPermissao());
		}
		jListAlunosComPermissao.revalidate();
		jListAlunosComPermissao.repaint();
	}
	
	public void preencheAlunosAtrasados() {
		jListAlunosAtrasados.removeAll();
		AlunoDao alunoDao = new AlunoDao();
		List<Aluno> alunos = alunoDao.pesquisarAlunoNaoPresente(new Date());
		for (Aluno aluno : alunos) {
			jListAlunosAtrasados.add(aluno.toString() + " - " + aluno.getTurmas().get(0).getNomeTurma().toString());
		}
		jListAlunosAtrasados.revalidate();
		jListAlunosAtrasados.repaint();
	}
	
	public void preencheVisitantesDia() {
		jListVisitantes.removeAll();
		VisitanteDao visitanteDao = new VisitanteDao();
		List<Visitante> visitanteDia = visitanteDao.pesquisarVisitantesDia();
		for (Visitante visitante : visitanteDia) {
			jListVisitantes.add(visitante.toString());
		}
		jListVisitantes.revalidate();
		jListVisitantes.repaint();
	}
	
	public void criaSchedulerPorAula() {
		List<Horario> aulasDia = new ArrayList<>();
		HorarioDao horarioDao = new HorarioDao();
		aulasDia = horarioDao.pesquisarPorHorariosDoDia();
		for (Horario horario : aulasDia) {
			criaSchedler(Integer.parseInt(sdfHora.format(horario.getHorarioInicioAula())), Integer.parseInt(sdfMinuto.format(horario.getHorarioInicioAula())));
			criaSchedler(Integer.parseInt(sdfHora.format(horario.getHorarioFinalAula())), Integer.parseInt(sdfMinuto.format(horario.getHorarioFinalAula())));
		}
	}
	
	private void criaSchedler(int hora, int minuto) {
		LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNext ;
        zonedNext = zonedNow.withHour(hora).withMinute(minuto).withSecond(1);
        if(zonedNow.compareTo(zonedNext) > 0)
            zonedNext = zonedNext.plusDays(1);

        Duration duration = Duration.between(zonedNow, zonedNext);
        long initalDelay = duration.getSeconds();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler = Executors.newScheduledThreadPool(1);            
        scheduler.scheduleAtFixedRate(new RunnableAtualizacaoAtrasados(instanciaJanelaPrincipal), initalDelay,
                                      24*60*60, TimeUnit.SECONDS);
	}
}
