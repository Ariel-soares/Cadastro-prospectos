package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;

import entities.Prospecto;
import entities.Servico;

public class Form extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldCaminho;
	private JTextField caminhoToken;
	private JTextField textFieldEndereco;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form frame = new Form();
					frame.setTitle("Cadastro de prospectos");
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Form() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1034, 586);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(50, 27, 140));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblXClose = new JLabel("");
		lblXClose.setIcon(new ImageIcon(Form.class.getResource("/imagens/icons8-x-50.png")));
		lblXClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		lblXClose.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblXClose.setBackground(new Color(255, 255, 255));
		lblXClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblXClose.setForeground(new Color(76, 41, 211));
		lblXClose.setBounds(984, 0, 50, 42);
		contentPane.add(lblXClose);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(76, 41, 211));
		panel.setBounds(0, 0, 326, 586);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Formulário de requisição");
		lblNewLabel.setForeground(new Color(204,204,204));
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 19));
		lblNewLabel.setBounds(45, 30, 243, 31);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Caminho do arquivo:");
		lblNewLabel_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(28, 92, 188, 14);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Token de acesso:");
		lblNewLabel_1_1.setForeground(new Color(204, 204, 204));
		lblNewLabel_1_1.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		lblNewLabel_1_1.setBounds(28, 177, 188, 14);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_2 = new JLabel("Link do servidor");
		lblNewLabel_2.setForeground(new Color(204, 204, 204));
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		lblNewLabel_2.setBounds(28, 272, 188, 14);
		panel.add(lblNewLabel_2);

		textFieldCaminho = new JTextField();
		textFieldCaminho.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		textFieldCaminho.setBounds(28, 130, 188, 20);
		panel.add(textFieldCaminho);
		textFieldCaminho.setColumns(10);

		caminhoToken = new JTextField();
		caminhoToken.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		caminhoToken.setBounds(28, 211, 258, 20);
		panel.add(caminhoToken);
		caminhoToken.setColumns(10);

		textFieldEndereco = new JTextField();
		textFieldEndereco.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		textFieldEndereco.setBounds(28, 307, 258, 20);
		panel.add(textFieldEndereco);
		textFieldEndereco.setColumns(10);

		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(230, 230, 230));
		textArea.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		textArea.setBounds(326, 44, 708, 542);
		contentPane.add(textArea);
		
		

		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldCaminho.getText() != null && !textFieldCaminho.getText().isEmpty()
						&& caminhoToken.getText() != null && !caminhoToken.getText().isEmpty()
						&& textFieldEndereco.getText() != null && !textFieldEndereco.getText().isEmpty()) {

					btnNewButton.setEnabled(false);
					textArea.setEditable(false);
					textArea.append("Carregando arquivos");

					String path = textFieldCaminho.getText();
					String token = caminhoToken.getText();
					String endereco = textFieldEndereco.getText();

					Gson gson = new Gson();

					List<Prospecto> list = new ArrayList<>();

					// Adicionar threads para imprimir informações e outra para fazer as requisições;
					
					try (BufferedReader br = new BufferedReader(new FileReader(path))) {

						String line = br.readLine();

						while (line != null) {
							String[] campos = line.split(";");
							list.add(new Prospecto(campos[0], campos[1], campos[2], campos[3], campos[4], campos[5],
									campos[6], campos[7], campos[8], new Servico(campos[9], campos[10])));
							line = br.readLine();
						}

					} catch (IOException r) {
						System.out.println("O caminho especificado apresenta problemas");
					}

					List<String> prospectos = new ArrayList<>();

					for (Prospecto p : list) {
						prospectos.add(gson.toJson(p));
					}

					for (String s : prospectos) {
						textArea.append(s + "\n");
					}

					System.out.println(
							"__________________________________________________________________________________");
					for (String s : prospectos) {
						System.out.println(s);

						HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(s))
								.uri(URI.create(endereco)).header("Content-Type", "application/json")
								.header("Authorization", "Bearer " + token).build();

						HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3))
								.followRedirects(Redirect.NORMAL).build();

						HttpResponse<String> response;
						try {
							response = httpClient.send(request, BodyHandlers.ofString());

							textArea.append("     " + response.body() + "\n");
							System.out.println(response.body());
							textArea.append("     " + "Código de status: " + String.valueOf(response.statusCode()) + "\n" + "\n");
							System.out.println(response.statusCode());
							System.out.println(response.headers());
							System.out.println(response.version());
							textArea.append(
									"________________________________________________ CADASTRO FINALIZADO________________________________________" + "\n");
							
							System.out.println(
									"____________________________________________________________________________________________________________");

						} catch (IOException | InterruptedException e1) {
							e1.printStackTrace();
						}
						
					}

					textArea.append(
							"---------------------------------------------------------PROCESSO FINALIZADO----------------------------------------------------------");

				} else {
					JOptionPane.showMessageDialog(btnNewButton, "Os campos não podem estar em branco", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 17));
		btnNewButton.setBounds(127, 387, 89, 23);
		panel.add(btnNewButton);

		JButton procurar = new JButton("Procurar");

		procurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.showOpenDialog(procurar);
				File f = fc.getSelectedFile();

				textFieldCaminho.setText(f.getPath());
			}
		});

		procurar.setFont(new Font("Century Gothic", Font.BOLD, 13));
		procurar.setBounds(227, 130, 89, 23);
		panel.add(procurar);
		
		JLabel lblMinimize = new JLabel("New label");
		lblMinimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);
				}
		});
		lblMinimize.setIcon(new ImageIcon(Form.class.getResource("/imagens/icons8-minimize-50.png")));
		lblMinimize.setBounds(917, 0, 57, 53);
		contentPane.add(lblMinimize);

	}
}
