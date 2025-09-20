package portfolioRebalancer.writer;

import portfolioRebalancer.model.Portfolio;

import java.util.List;

public interface PortfolioWriter {

    void writeTargetValues(List<Portfolio> portfolioList, String outputFilePath);
}
