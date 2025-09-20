package portfolioRebalancer.writer;

import portfolioRebalancer.model.Portfolio;

import java.util.List;

/**
 * Interface for writing portfolio target values to an output file.
 */
public interface PortfolioWriter {

    /**
     * Writes the target values of the given list of portfolios to the specified output file.
     *
     * @param portfolioList   the list of portfolios to write
     * @param outputFilePath  the path of the output file
     */
    void writeTargetValues(List<Portfolio> portfolioList, String outputFilePath);
}