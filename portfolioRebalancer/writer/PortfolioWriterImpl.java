package portfolioRebalancer.writer;

import portfolioRebalancer.model.Portfolio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PortfolioWriterImpl implements PortfolioWriter{

    @Override
    public void writeTargetValues(List<Portfolio> portfolioList, String outputFilePath) {
        String fileName = outputFilePath + "/portfolio_targets.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
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
            throw new RuntimeException("Error writing file " + fileName, e);
        }
    }
}
