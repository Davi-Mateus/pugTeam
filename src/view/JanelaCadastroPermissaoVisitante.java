package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;

import dao.PermissaoDao;
import dao.VisitanteDao;
import model.Permissao;
import model.Visitante;
import com.toedter.calendar.JDateChooser;

public class JanelaCadastroPermissaoVisitante {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblNomeAluno;
	private JLabel lblNumeroMatricula;
	private JLabel lblResponsvel;
	private JLabel lblDataDaPermissao;
	private JTextField inputNomeVisitante;
	private JTextField inputMotivo;
	private JTextField inputResponsavel;
	private JDateChooser inputDataPermissao;
	private JButton btnSalvar;
	private JButton btnCancelar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCadastroPermissaoVisitante window = new JanelaCadastroPermissaoVisitante(null);
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
	public JanelaCadastroPermissaoVisitante(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Cadastro de permissão para visitante");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblNomeAluno = new JLabel("Nome do visitante:");
		lblNomeAluno.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomeAluno.setBounds(58, 32, 153, 20);
		panel.add(lblNomeAluno);
		
		inputNomeVisitante = new JTextField();
		inputNomeVisitante.setBounds(244, 32, 362, 25);
		panel.add(inputNomeVisitante);
		inputNomeVisitante.setColumns(10);
		
		lblNumeroMatricula = new JLabel("Motivo da visita:");
		lblNumeroMatricula.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNumeroMatricula.setBounds(58, 88, 181, 15);
		panel.add(lblNumeroMatricula);
		
		inputMotivo = new JTextField();
		inputMotivo.setBounds(244, 88, 362, 25);
		panel.add(inputMotivo);
		inputMotivo.setColumns(10);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String nome = inputNomeVisitante.getText();
				String motivo = inputMotivo.getText();
				String responsavel = inputResponsavel.getText();
				Date dataPermissao = inputDataPermissao.getDate();
				
				if (nome.length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o nome do visitante não foi preenchido");
					return;
				}
				if (motivo.length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o motivo da visita não foi preenchido");
					return;
				}
				if (responsavel.length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o responsável pela visita não foi preenchido");
					return;
				}
				if (dataPermissao == null) {
					JOptionPane.showMessageDialog(null, "ERRO, a data da visita não foi preenchida");
					return;
				}
				
				PermissaoDao permissaoDao = new PermissaoDao();
				
				Permissao permissao = new Permissao();
				
				permissao.setDataPermissao(dataPermissao);
				permissao.setResponsavel(responsavel);
				permissao.setTipoPermissao("ENTRADA E SAIDA");
				
				boolean insercaoPermissao =permissaoDao.inserirPermissao(permissao);
				
				if (!insercaoPermissao) {
					JOptionPane.showMessageDialog(null, "ERRO ao inserir permissão ao visitante no banco de dados");
					limpaCampos();
					return;
				}
				
				permissao.setIdPermissao(permissaoDao.buscarUltimoId());
				
				Visitante visitante = new Visitante();
				visitante.setNomeVisitante(nome);
				visitante.setMotivoVisita(motivo);
				visitante.setPermissao(permissao);

				VisitanteDao visitanteDao = new VisitanteDao();
				boolean insercaoVisitante = visitanteDao.inserir(visitante);
				
				if (!insercaoVisitante) {
					JOptionPane.showMessageDialog(null, "ERRO ao inserir permissão ao visitante no banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				instanciaJanelaPrincipal.preencheVisitantesDia();
				JOptionPane.showMessageDialog(null, "Permissão ao visitante inserido com sucesso");
			}
		});
		btnSalvar.setBounds(257, 333, 117, 25);
		panel.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				frame. dispose();
			}
		});
		btnCancelar.setBounds(451, 333, 117, 25);
		panel.add(btnCancelar);
		
		lblResponsvel = new JLabel("Responsável:");
		lblResponsvel.setFont(new Font("Dialog", Font.BOLD, 14));
		lblResponsvel.setBounds(58, 144, 181, 15);
		panel.add(lblResponsvel);
		
		inputResponsavel = new JTextField();
		inputResponsavel.setColumns(10);
		inputResponsavel.setBounds(244, 144, 362, 25);
		panel.add(inputResponsavel);
		
		lblDataDaPermissao = new JLabel("Data da permissao:");
		lblDataDaPermissao.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDataDaPermissao.setBounds(58, 200, 181, 15);
		panel.add(lblDataDaPermissao);
		
		inputDataPermissao = new JDateChooser();
		inputDataPermissao.setDateFormatString("dd/MM/yyyy");
		inputDataPermissao.setBounds(244, 200, 362, 25);
		panel.add(inputDataPermissao);
	}
	
	private void limpaCampos() {
		inputNomeVisitante.setText("");
		inputMotivo.setText("");
		inputResponsavel.setText("");
		inputDataPermissao.setDate(null);
	}
}
