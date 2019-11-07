package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import dao.ImportacaoDao;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class JanelaImportacao {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblAluno;
	private JButton btnGerar;
	private JButton btnCancelar;
	private JButton btnLimpar;
	private JTextField caminhoArquivo;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaImportacao window = new JanelaImportacao(null);
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
	public JanelaImportacao(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Importação");
		frame.setBounds(0, 0, 761, 180);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblAluno = new JLabel("Selecione o csv:");
		lblAluno.setFont(new Font("Dialog", Font.BOLD, 14));
		lblAluno.setBounds(30, 20, 140, 20);
		panel.add(lblAluno);
		
		btnGerar = new JButton("Importar");
		btnGerar.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				ImportacaoDao importacaoDao = new ImportacaoDao();
				boolean importacao = importacaoDao.importar(caminhoArquivo.getText());
				if (importacao) {
					JOptionPane.showMessageDialog(null, "Importação efetuada com sucesso");
				}
				instanciaJanelaPrincipal.preencheAlunosAtrasados();
				limpaCampos();
			}
		});
		btnGerar.setBounds(600, 20, 105, 25);
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
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(257, 95, 117, 25);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
			}
		});
		panel.add(btnLimpar);
		
		caminhoArquivo = new JTextField();
		caminhoArquivo.setColumns(10);
		caminhoArquivo.setBounds(180, 20, 340, 25);
		panel.add(caminhoArquivo);
		
		JButton btnBuscar = new JButton("...");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					caminhoArquivo.setText(selectedFile.getPath());
				}
			}
		});
		btnBuscar.setBounds(520, 20, 20, 24);
		panel.add(btnBuscar);
				
	}
	
	private void limpaCampos() {
	caminhoArquivo.setText("");
	}
}
