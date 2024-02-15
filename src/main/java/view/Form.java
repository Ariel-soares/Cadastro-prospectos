package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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

	/**
	 * Launch the application.
	 */

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

	/**
	 * Create the frame.
	 */
	public Form() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 864, 611);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(49, 62, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(34, 11, 775, 228);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Formulário de requisição");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
		lblNewLabel.setBounds(242, 11, 258, 31);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Caminho do arquivo:");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblNewLabel_1.setBounds(42, 53, 188, 14);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Token de acesso:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblNewLabel_1_1.setBounds(42, 93, 188, 14);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_2 = new JLabel("Link do servidor");
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblNewLabel_2.setBounds(42, 137, 188, 14);
		panel.add(lblNewLabel_2);

		textFieldCaminho = new JTextField();
		textFieldCaminho.setBounds(240, 53, 404, 20);
		panel.add(textFieldCaminho);
		textFieldCaminho.setColumns(10);

		caminhoToken = new JTextField();
		caminhoToken.setBounds(240, 90, 404, 20);
		panel.add(caminhoToken);
		caminhoToken.setColumns(10);

		textFieldEndereco = new JTextField();
		textFieldEndereco.setBounds(240, 134, 404, 20);
		panel.add(textFieldEndereco);
		textFieldEndereco.setColumns(10);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(34, 250, 775, 311);
		contentPane.add(textArea);

		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldCaminho.getText() != null && !textFieldCaminho.getText().isEmpty()
						&& caminhoToken.getText() != null && !caminhoToken.getText().isEmpty()
						&& textFieldEndereco.getText() != null && !textFieldEndereco.getText().isEmpty()) {

					btnNewButton.setEnabled(false);
					textArea.setEditable(false);

					String path = textFieldCaminho.getText();
					String token = caminhoToken.getText();
					String endereco = textFieldEndereco.getText();

					Gson gson = new Gson();

					List<Prospecto> list = new ArrayList<>();

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
						System.out.println(s);
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
									"_____________________________________________________________________________________________________________"
											+ "\n");
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
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
		btnNewButton.setBounds(321, 194, 89, 23);
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

		procurar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		procurar.setBounds(676, 51, 89, 23);
		panel.add(procurar);

	}
}
