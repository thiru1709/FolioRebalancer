package portfolioRebalancer.Reader;

import portfolioRebalancer.model.Model;
import portfolioRebalancer.model.Portfolio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class PortfolioReaderImpl implements PortfolioReader {

    private <T> List<T> readFromFile(String filePath, Function<String, T> lineParser) {
        List<T> resultList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                T obj = lineParser.apply(line);
                resultList.add(obj);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading the file "+ filePath , e);
        }
        return resultList;
    }

    @Override
    public List<Portfolio> getPortfolioList(String filePath) {
        return readFromFile(filePath, line -> {
            String[] parts = line.split(","); // Split by comma

            if (parts.length == 3) { // Ensure we have security,name,age
                try {
                    String security = parts[0].trim(); // Get the security name
                    double price = Double.parseDouble(parts[1].trim()); // Get the age
                    double quantity = Double.parseDouble(parts[2].trim()); // Get the age
                    return new Portfolio(security, price, quantity); // Create a portfolio object and add to the list
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
            return null;
        });
    }

    @Override
    public List<Model> getModelList(String filePath) {
        return readFromFile(filePath, line -> {
            String[] parts = line.split(","); // Split by comma

            if (parts.length == 2) { // Ensure we have security,name,age
                try {
                    String security = parts[0].trim(); // Get the security name
                    double percentage = Double.parseDouble(parts[1].trim()); // Get the age
                    return new Model(security, percentage);
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
            return null;
        });
    }
}
