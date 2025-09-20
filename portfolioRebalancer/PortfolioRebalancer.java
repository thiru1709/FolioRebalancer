package portfolioRebalancer;

import portfolioRebalancer.Reader.PortfolioReader;
import portfolioRebalancer.model.Model;
import portfolioRebalancer.model.Portfolio;
import portfolioRebalancer.service.ModelService;
import portfolioRebalancer.service.PortfolioService;
import portfolioRebalancer.writer.PortfolioWriter;

import java.util.List;


public class PortfolioRebalancer {

    private final PortfolioService portfolioService;
    private final ModelService modelService;
    private final PortfolioReader portfolioReader;
    private final PortfolioWriter portfolioWriter;

    public PortfolioRebalancer(PortfolioService portfolioService, ModelService modelService, PortfolioReader portfolioReader, PortfolioWriter portfolioWriter) {
        this.portfolioService = portfolioService;
        this.modelService = modelService;
        this.portfolioReader = portfolioReader;
        this.portfolioWriter = portfolioWriter;
    }


    public void rebalance(String portfolioPath, String modelpath, String outputDir) {
        List<Portfolio> portfolioList = portfolioReader.getPortfolioList(portfolioPath);
        List<Model> modelList = portfolioReader.getModelList(modelpath);

        double totalPortfolioMarketValue = portfolioService.getTotalMarketValue(portfolioList);
        portfolioService.calculateSecurityPerc(portfolioList, totalPortfolioMarketValue);
        modelService.calculateDriftFromModel(portfolioList, modelList,totalPortfolioMarketValue);
        portfolioService.calculateTrades(portfolioList, totalPortfolioMarketValue);

        portfolioWriter.writeTargetValues(portfolioList,outputDir);


        // Output the list of people
        portfolioList.forEach(System.out::println);
        modelList.forEach(System.out::println);
        System.out.println("Total market value is " + totalPortfolioMarketValue);
    }


}
