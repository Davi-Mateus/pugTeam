package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.Font;
import model.Aluno;
import util.Relatorio;
import dao.AlunoDao;
import com.toedter.calendar.JDateChooser;

public class JanelaRelatorioFrequenciaAluno {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblAluno;
	private JLabel lblAte;
	private JTextField inputAluno;
	private JButton btnGerar;
	private JButton btnCancelar;
	private JButton btnLimpar;
	private JDateChooser dataInicio;
	private JDateChooser dataFinal;
	
	private Aluno alunoSelecionado = new Aluno();

	private SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-MM-dd");


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaRelatorioFrequenciaAluno window = new JanelaRelatorioFrequenciaAluno(null);
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
	public JanelaRelatorioFrequenciaAluno(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Relatório de frequência por aluno");
		frame.setBounds(0, 0, 761, 180);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblAluno = new JLabel("Aluno:");
		lblAluno.setFont(new Font("Dialog", Font.BOLD, 14));
		lblAluno.setBounds(30, 20, 130, 20);
		panel.add(lblAluno);
		
		inputAluno = new JTextField();
		inputAluno.setBounds(90, 20, 280, 25);
		inputAluno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
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
		panel.add(inputAluno);
		inputAluno.setColumns(10);
		
		btnGerar = new JButton("Gerar");
		btnGerar.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {

				String dtInicio;
				try {
					dtInicio = sdfData.format(dataInicio.getDate());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERRO, a data de início não foi selecionada");
					return;
				}
				String dtFinal;
				try {
					dtFinal = sdfData.format(dataFinal.getDate());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERRO, a data de final não foi selecionada");
					return;
				}
				
				if (inputAluno.getText() == "") {
					JOptionPane.showMessageDialog(null, "ERRO, o aluno não foi selecionado");
					return;
				}
				if (alunoSelecionado.getIdAluno() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o aluno não foi selecionado corretamente");
					return;
				}
				
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("dt_inicio", dtInicio);
				parametros.put("dt_final", dtFinal);
				parametros.put("id_aluno", alunoSelecionado.getIdAluno());
				
				try {
					new Relatorio().gerar( "/home/rafael/GitHub/pugTeam/src/relatorios/FrequenciaPorAluno.jasper", parametros, "Relatório de frequência por aluno" );
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		btnGerar.setBounds(632, 20, 105, 25);
		panel.add(btnGerar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				frame. dispose();
			}
		});
		btnCancelar.setBounds(451, 95, 117, 25);
		panel.add(btnCancelar);
		
		dataInicio = new JDateChooser();
		dataInicio.setDateFormatString("dd/MM/yyyy");
		dataInicio.setBounds(385, 20, 100, 25);
		panel.add(dataInicio);
		
		dataFinal = new JDateChooser();
		dataFinal.setDateFormatString("dd/MM/yyyy");
		dataFinal.setBounds(520, 20, 100, 25);
		panel.add(dataFinal);
		
		lblAte = new JLabel("até");
		lblAte.setBounds(490, 20, 70, 20);
		panel.add(lblAte);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(257, 95, 117, 25);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
			}
		});
		panel.add(btnLimpar);
		
		
	}
	
	private void limpaCampos() {
		inputAluno.setText("");
		dataInicio.setDate(null);
		dataFinal.setDate(null);
	}
}
