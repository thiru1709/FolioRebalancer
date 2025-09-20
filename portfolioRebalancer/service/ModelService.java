package portfolioRebalancer.service;

import portfolioRebalancer.model.Model;
import portfolioRebalancer.model.Portfolio;

import java.util.List;

import static portfolioRebalancer.service.UtilityService.roundToNearestDouble;

public class ModelService {

    public void calculateDriftFromModel(List<Portfolio> portfolioList, List<Model> modelList, double totalPortfolioMarketValue) {
        for (Portfolio portfolio : portfolioList) {
            calculateDriftForPortfolio(modelList, totalPortfolioMarketValue, portfolio);

        }
    }

    private void calculateDriftForPortfolio(List<Model> modelList, double totalPortfolioMarketValue, Portfolio portfolio) {
        boolean securityFound = false;
        for (Model model : modelList) {
            if (model.getSecurity().equals(portfolio.getSecurity())) {
                securityFound = true;
                portfolio.setDriftFromMode(roundToNearestDouble(portfolio.getPercentage() - model.getPercentage()));
                portfolio.setProposedTargetMV(model.getPercentage()/100 * totalPortfolioMarketValue);
            }
        }
        if(!securityFound){
            portfolio.setDriftFromMode(roundToNearestDouble(portfolio.getPercentage() - 0));
            portfolio.setProposedTargetMV(0 * totalPortfolioMarketValue);
        }
    }


}
