package portfolioRebalancer.service;

import portfolioRebalancer.model.Model;
import portfolioRebalancer.model.Portfolio;

import java.util.List;

import static portfolioRebalancer.service.UtilityService.roundToNearestDouble;
public class ModelService {

    /**
     * Calculates the drift from the model for each portfolio in the list.
     * Updates each portfolio with its drift and proposed target market value.
     *
     * @param portfolioList List of portfolios to update
     * @param modelList List of model securities and their target percentages
     * @param totalPortfolioMarketValue Total market value of all portfolios
     */
    public void calculateDriftFromModel(List<Portfolio> portfolioList, List<Model> modelList, double totalPortfolioMarketValue) {
        for (Portfolio portfolio : portfolioList) {
            calculateDriftForPortfolio(modelList, totalPortfolioMarketValue, portfolio);
        }
    }

    /**
     * Calculates drift and proposed target market value for a single portfolio.
     * If the portfolio's security is not found in the model, assumes target percentage is 0.
     *
     * @param modelList List of model securities and their target percentages
     * @param totalPortfolioMarketValue Total market value of all portfolios
     * @param portfolio Portfolio to update
     */
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
