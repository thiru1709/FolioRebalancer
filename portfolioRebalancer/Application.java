package portfolioRebalancer;

import portfolioRebalancer.Reader.PortfolioReader;
import portfolioRebalancer.Reader.PortfolioReaderImpl;
import portfolioRebalancer.service.ModelService;
import portfolioRebalancer.service.PortfolioService;
import portfolioRebalancer.writer.PortfolioWriter;
import portfolioRebalancer.writer.PortfolioWriterImpl;

public class Application {

    // Entry point of the application
    public static void main(String[] args) {

        // Create reader and writer implementations for portfolio data
        PortfolioReader portfolioReader = new PortfolioReaderImpl();
        PortfolioWriter portfolioWriter = new PortfolioWriterImpl();

        // Create service instances for model and portfolio operations
        ModelService modelService = new ModelService();
        PortfolioService portfolioService = new PortfolioService();

        // Inject dependencies into PortfolioRebalancer
        PortfolioRebalancer portfolioRebalancer = new PortfolioRebalancer(
                portfolioService, modelService, portfolioReader, portfolioWriter
        );

        // Define base path for resource files
        String basePath = "D:\\HLD & LLD\\FolioRebalancer\\src\\portfolioRebalancer\\resources";
        String portfolioPath = basePath + "\\portfolio.txt";
        String modelpath = basePath + "\\model.txt";

        // Perform portfolio rebalancing and generate output file
        portfolioRebalancer.rebalance(portfolioPath, modelpath, basePath);

    }
}
