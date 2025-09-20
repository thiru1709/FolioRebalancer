package portfolioRebalancer;

import portfolioRebalancer.Reader.PortfolioReader;
import portfolioRebalancer.model.Model;
import portfolioRebalancer.model.Portfolio;
import portfolioRebalancer.service.ModelService;
import portfolioRebalancer.service.PortfolioService;
import portfolioRebalancer.writer.PortfolioWriter;

import java.util.List;

/**
 * Main class responsible for rebalancing portfolios based on models.
 */
public class PortfolioRebalancer {

    // Service for portfolio-related operations
    private final PortfolioService portfolioService;
    // Service for model-related operations
    private final ModelService modelService;
    // Reader for portfolio and model data
    private final PortfolioReader portfolioReader;
    // Writer for outputting portfolio data
    private final PortfolioWriter portfolioWriter;

    /**
     * Constructs a PortfolioRebalancer with required services and readers/writers.
     *
     * @param portfolioService Service for portfolio operations
     * @param modelService Service for model operations
     * @param portfolioReader Reader for portfolio/model data
     * @param portfolioWriter Writer for outputting results
     */
    public PortfolioRebalancer(PortfolioService portfolioService, ModelService modelService, PortfolioReader portfolioReader, PortfolioWriter portfolioWriter) {
        this.portfolioService = portfolioService;
        this.modelService = modelService;
        this.portfolioReader = portfolioReader;
        this.portfolioWriter = portfolioWriter;
    }

    /**
     * Rebalances the portfolio based on the provided model and writes the results.
     *
     * @param portfolioPath Path to the portfolio data file
     * @param modelpath Path to the model data file
     * @param outputDir Directory to write output files
     */
    public void rebalance(String portfolioPath, String modelpath, String outputDir) {
        // Read portfolio and model data
        List<Portfolio> portfolioList = portfolioReader.getPortfolioList(portfolioPath);
        List<Model> modelList = portfolioReader.getModelList(modelpath);

        // Calculate total market value of the portfolio
        double totalPortfolioMarketValue = portfolioService.getTotalMarketValue(portfolioList);

        // Calculate security percentages in the portfolio
        portfolioService.calculateSecurityPerc(portfolioList, totalPortfolioMarketValue);

        // Calculate drift from the model
        modelService.calculateDriftFromModel(portfolioList, modelList, totalPortfolioMarketValue);

        // Calculate required trades to rebalance
        portfolioService.calculateTrades(portfolioList, totalPortfolioMarketValue);

        // Write target values to output
        portfolioWriter.writeTargetValues(portfolioList, outputDir);

        // Output portfolio and model details for verification
        portfolioList.forEach(System.out::println);
        modelList.forEach(System.out::println);
        System.out.println("Total market value is " + totalPortfolioMarketValue);
    }
}