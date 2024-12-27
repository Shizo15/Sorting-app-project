import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Frame extends JFrame {

    Frame() {this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setTitle("Sorting App");
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("jerryLogo.png");
        this.setIconImage(logo.getImage());


        // Panel górny
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0xb8b8b8));
        topPanel.setPreferredSize(new Dimension(800, 40));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel logoLabel = new JLabel("Sorting data sets app");
        logoLabel.setForeground(Color.black);
        logoLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        topPanel.add(logoLabel);

        // Panel lewy
        JPanel leftCenterPanel = new JPanel();
        leftCenterPanel.setPreferredSize(new Dimension(250, 480));
        leftCenterPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        JTextField fileField = new JTextField(20);
        fileField.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
        JButton loadFileButton = new JButton("Wczytaj plik");
        JButton saveToFileButton = new JButton("Zapisz do pliku");

        // Dodanie etykiety "Plik:"
        gbc.gridx = 0; // Kolumna 0
        gbc.gridy = 0; // Wiersz 0
        gbc.gridwidth = 2; // Zajmuje dwie kolumny
        leftCenterPanel.add(new JLabel("Plik:"), gbc);

        // Dodanie pola tekstowego
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        leftCenterPanel.add(fileField, gbc);

        // Dodanie przycisku "Wczytaj plik"
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        leftCenterPanel.add(loadFileButton, gbc);

        // Dodanie przycisku "Zapisz do pliku"
        gbc.gridx = 1;
        gbc.gridy = 2;
        leftCenterPanel.add(saveToFileButton, gbc);

        // Panel prawy
        JPanel rightCenterPanel = new JPanel(new BorderLayout());
        rightCenterPanel.setBackground(Color.LIGHT_GRAY);

        JTextArea dataArea = new JTextArea();
        dataArea.setEditable(false);
        dataArea.setLineWrap(true);
        dataArea.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));

        JScrollPane scrollPane = new JScrollPane(dataArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rightCenterPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel dolny
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0xb8b8b8));
        bottomPanel.setPreferredSize(new Dimension(800, 40));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel sizeLabel = new JLabel("Rozmiar zbioru:");
        String[] sizes = {"100", "1000", "10000"};
        JComboBox<String> sizeComboBox = new JComboBox<>(sizes);
        JLabel dataTypeLabel = new JLabel("Typ danych:");
        String[] dataTypes = {"Liczby całkowite", "Liczby zmiennoprzecinkowe", "Łańcuchy znaków"};
        JComboBox<String> dataTypeComboBox = new JComboBox<>(dataTypes);
        JButton generateDataButton = new JButton("Generuj dane");
        JButton sortButton = new JButton("Sortuj");


        bottomPanel.add(sizeLabel);
        bottomPanel.add(sizeComboBox);
        bottomPanel.add(dataTypeLabel);
        bottomPanel.add(dataTypeComboBox);
        bottomPanel.add(generateDataButton);
        bottomPanel.add(sortButton);


        this.add(topPanel, BorderLayout.NORTH);
        this.add(leftCenterPanel, BorderLayout.WEST);
        this.add(rightCenterPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        List<String> data = new ArrayList<>();

        //Obsługa przycisków

        //Generuj dane
        generateDataButton.addActionListener(e -> {
            data.clear();
            int size = Integer.parseInt((String) sizeComboBox.getSelectedItem());
            String type = (String) dataTypeComboBox.getSelectedItem();

            Random random = new Random();
            for (int i = 0; i < size; i++) {
                switch (type) {
                    case "Liczby całkowite":
                        data.add(String.valueOf(random.nextInt(1000)));
                        break;
                    case "Liczby zmiennoprzecinkowe":
                        data.add(String.format("%.2f", random.nextDouble()*1000));
                        break;
                    case "Łańcuchy znaków":
                        data.add(generateRandomString(random, 5));
                        break;
                }
            }
            dataArea.setText(String.join("\n", data));
            JOptionPane.showMessageDialog(this, "Dane zostały wygenerowane!");

        });

        //Wczytaj plik
        loadFileButton.addActionListener(e -> {
            String filePath = fileField.getText();
            try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
                data.clear();
                String line;
                while ((line = br.readLine())!=null){
                    data.add(line);
                }
                dataArea.setText(String.join("\n", data));
                JOptionPane.showMessageDialog(this, "Dane zostały wczytane pomyślnie!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Błąd podczas wczytywania pliku. Spróbuj ponownie!");
            }
        });
    }
    public String generateRandomString(Random random, int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

}
