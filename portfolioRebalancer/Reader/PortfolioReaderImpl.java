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

    /**
     * Reads lines from a file and parses each line using the provided lineParser function.
     *
     * @param filePath   the path to the file to read
     * @param lineParser a function to parse each line into an object of type T
     * @param <T>        the type of objects to return
     * @return a list of parsed objects
     */
    private <T> List<T> readFromFile(String filePath, Function<String, T> lineParser) {
        List<T> resultList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                T obj = lineParser.apply(line);
                resultList.add(obj);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading the file " + filePath, e);
        }
        return resultList;
    }

    /**
     * Reads a portfolio list from a file.
     * Each line should contain: security, price, quantity (comma-separated).
     *
     * @param filePath the path to the portfolio file
     * @return a list of Portfolio objects
     */
    @Override
    public List<Portfolio> getPortfolioList(String filePath) {
        return readFromFile(filePath, line -> {
            String[] parts = line.split(","); // Split by comma

            if (parts.length == 3) { // Ensure we have security, price, quantity
                try {
                    String security = parts[0].trim(); // Get the security name
                    double price = Double.parseDouble(parts[1].trim()); // Get the price
                    double quantity = Double.parseDouble(parts[2].trim()); // Get the quantity
                    return new Portfolio(security, price, quantity); // Create a portfolio object
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
            return null;
        });
    }

    /**
     * Reads a model list from a file.
     * Each line should contain: security, percentage (comma-separated).
     *
     * @param filePath the path to the model file
     * @return a list of Model objects
     */
    @Override
    public List<Model> getModelList(String filePath) {
        return readFromFile(filePath, line -> {
            String[] parts = line.split(","); // Split by comma

            if (parts.length == 2) { // Ensure we have security, percentage
                try {
                    String security = parts[0].trim(); // Get the security name
                    double percentage = Double.parseDouble(parts[1].trim()); // Get the percentage
                    return new Model(security, percentage);
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
            return null;
        });
    }
}