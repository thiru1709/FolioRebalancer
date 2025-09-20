package portfolioRebalancer;

import portfolioRebalancer.Reader.PortfolioReader;
import portfolioRebalancer.Reader.PortfolioReaderImpl;
import portfolioRebalancer.service.ModelService;
import portfolioRebalancer.service.PortfolioService;
import portfolioRebalancer.writer.PortfolioWriter;
import portfolioRebalancer.writer.PortfolioWriterImpl;

public class Application {

    public static void main(String[] args) {

        PortfolioReader portfolioReader = new PortfolioReaderImpl();
        ModelService modelService = new ModelService();
        PortfolioService portfolioService = new PortfolioService();
        PortfolioWriter portfolioWriter = new PortfolioWriterImpl();
        PortfolioRebalancer portfolioRebalancer = new PortfolioRebalancer(portfolioService,modelService, portfolioReader,portfolioWriter);

        String basePath = "D:\\HLD & LLD\\FolioRebalancer\\src\\portfolioRebalancer\\resources";
        String portfolioPath = basePath + "\\portfolio.txt";
        String modelpath = basePath + "\\model.txt";
        portfolioRebalancer.rebalance(portfolioPath, modelpath, basePath);

    }
}
