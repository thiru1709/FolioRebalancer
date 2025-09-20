package portfolioRebalancer.writer;

import portfolioRebalancer.model.Portfolio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// Implementation of PortfolioWriter interface to write portfolio target values to a file
public class PortfolioWriterImpl implements PortfolioWriter{

    /**
     * Writes the target values of each portfolio in the list to a file.
     * The output file will be named 'portfolio_targets.txt' in the specified directory.
     *
     * @param portfolioList List of Portfolio objects to write
     * @param outputFilePath Directory path where the output file will be created
     */
    @Override
    public void writeTargetValues(List<Portfolio> portfolioList, String outputFilePath) {
        String fileName = outputFilePath + "/portfolio_targets.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write each portfolio's target values as a comma-separated line
            for(Portfolio portfolio : portfolioList){
                writer.write(
                        portfolio.getSecurity() + "," +
                                portfolio.getTargetQty() + "," +
                                portfolio.getTargetMV() + "," +
                                portfolio.getTargetPercentage()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            // Wrap IOException in a RuntimeException for error handling
            throw new RuntimeException("Error writing file " + fileName, e);
        }
    }
}