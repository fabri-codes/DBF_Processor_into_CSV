package fabri.codes;

import java.io.*;
import java.util.*;
import com.linuxense.javadbf.*;

public class App {
    public static void main(String[] args) {
        String dbfFilePath = "caminho\do\arquivo\.dbf";
        String csvFilePath = "caminho\do\arquivo\.csv";
        int groupByColumnIndex = 10; // Escolha o indexador da coluna que deseja agrupar

        try {
            // Leia o arquivo DBF
            InputStream inputStream = new FileInputStream(dbfFilePath);
            DBFReader reader = new DBFReader(inputStream);

            // Obter os nomes das colunas
            //String[] columnNames = reader.getColumnNames();

            // Cria um mapa para armazenar os valores agrupados
            Map<String, List<Double>> groupedValues = new HashMap<>();

            // Itera sobre cada linha do arquivo DBF
            Object[] row;
            while ((row = ((DBFReader) reader).nextRecord()) != null) {
                String groupKey = row[groupByColumnIndex].toString();
                double value = Double.parseDouble(row[15].toString()); // Indique o indexador da coluna que deseja somar

                // Adiciona o valor ao grupo correspondente
                if (groupedValues.containsKey(groupKey)) {
                    groupedValues.get(groupKey).add(value);
                } else {
                    List<Double> values = new ArrayList<>();
                    values.add(value);
                    groupedValues.put(groupKey, values);
                }
            }

            // Exporta os valores agrupados para um arquivo CSV
            FileWriter writer = new FileWriter(csvFilePath);
            //writer.append(String.join(",", columnNames)).append("\n");
            for (Map.Entry<String, List<Double>> entry : groupedValues.entrySet()) {
                String groupKey = entry.getKey();
                List<Double> values = entry.getValue();
                double sum = 0.0;
                for (Double value : values) {
                    sum += value;
                }
                writer.append(groupKey).append(",").append(String.valueOf(sum)).append("\n");
            }
            writer.flush();
            writer.close();

            System.out.println("CSV file exported successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
