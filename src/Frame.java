import javax.swing.*;
import java.awt.*;
import java.io.*;
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
                dataArea.setText(String.join("\n", data).replace(',','.'));
                JOptionPane.showMessageDialog(this, "Dane zostały wczytane pomyślnie!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Błąd podczas wczytywania pliku. Spróbuj ponownie!");
            }
        });

        //Sortuj

        //pobieramy pierwszą linię
        //robimy try catch sprawdzjąc kolejno czy int, jak nie to czy double, jak nie to na pewno string

        sortButton.addActionListener(e -> {
            if(dataArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Brak danych do posortowania!");
            }

            try{
                String text = dataArea.getText();
                String firstValue = text.split("\n")[0];

                if(tryParseInt(firstValue)){
                    dataTypeComboBox.setSelectedItem(dataTypes[0]);
                }
                else if (tryParseDouble(firstValue)) {
                    dataTypeComboBox.setSelectedItem(dataTypes[1]);
                }
                else {
                    dataTypeComboBox.setSelectedItem(dataTypes[2]);
                }
            }
            catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this,"Coś dziwnego się stało z formatem danych");
            }

            String type = (String) dataTypeComboBox.getSelectedItem();
            if(type.equals("Liczby całkowite")){
                //stworzyć liste
                //przepisać zawartość textArea do listy
                //wywołanie metody
                //czyścimy pole textArea żeby wpisać do niego posortowanie wartości
                //przepisujemy posrotwane wartości z listy do pola textArea

                try{
                    List<Integer>intData = new ArrayList<>();
                    for (String intElement : data) {
                        intData.add(Integer.parseInt(intElement));
                    }

                    bubbleSortGeneric(intData);

                    data.clear();
                    for (Integer intDatum : intData) {
                        data.add(String.valueOf(intDatum));
                    }
                    dataArea.setText(String.join("\n", data));
                    JOptionPane.showMessageDialog(this,"Pomyślnie posortowano dane!");
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(this, "Wybrano nieprawidłowy typ danych!");
                }
            }
            else if (type.equals("Liczby zmiennoprzecinkowe")) {

                try{
                    List<Double>doubleData = new ArrayList<>();
                    for (String doubleElement : data) {
                        doubleData.add(Double.parseDouble(doubleElement.replace(',','.')));
                    }
                    insertionSort(doubleData);
                    data.clear();
                    for (Double doubleDatum : doubleData) {
                        data.add(String.valueOf(doubleDatum));
                    }
                    dataArea.setText(String.join("\n", data));
                    JOptionPane.showMessageDialog(this,"Pomyślnie posortowano dane!");
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(this, "Wybrano nieprawidłowy typ danych!");
                }

            }
            else {
                try{
                    bubbleSortGeneric(data);
                    dataArea.setText(String.join("\n", data));
                    JOptionPane.showMessageDialog(this,"Pomyślnie posortowano dane!");
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(this, "Wybrano nieprawidłowy typ danych!");
                }
            }
        });

        //Zapisz do pliku - o podanej nazwie

        saveToFileButton.addActionListener(e -> {
            if(fileField.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Brak danych do zapisania!");
            }
            try(BufferedWriter bw = new BufferedWriter(new FileWriter("sorted_"+fileField.getText()))){
                for (String s : data) {
                    bw.write(s);
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(this, "Dane zostały zapisane do pliku sorted_"+fileField.getText());
            }catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Błąd podczas zapisu do pliku: " + ex.getMessage());
            }
        });
    }

    //Metoda generująca losowe ciągi znaków
    public String generateRandomString(Random random, int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    //robimy metode generyczną albo przeciążone werjsie tej samej metody z innym typem wejścia
    public <T extends Comparable<T>>void bubbleSortGeneric(List<T> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    public <T extends Comparable<T>>void insertionSort(List<T> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            T temp = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).compareTo(temp) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, temp);
        }
    }

    //Własne wersje metod tryParse z C# bo w Javie nie widze czegoś takiego
    public static boolean tryParseInt(String input){
     try {
         int value = Integer.parseInt(input);
         return true;
     }catch (NumberFormatException ex){
         return false;
     }
    }

    public static boolean tryParseDouble(String input){
        try {
            double value = Double.parseDouble(input);
            return true;
        }catch (NumberFormatException ex){
            return false;
        }
    }

}
