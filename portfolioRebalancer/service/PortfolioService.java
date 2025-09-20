package portfolioRebalancer.service;

import portfolioRebalancer.model.Portfolio;

import java.util.List;

import static portfolioRebalancer.service.UtilityService.roundToNearestDouble;

public class PortfolioService {

    // Constant representing the USD security
    public static final String USD_SECURITY = "USD";

    /**
     * Calculates and sets the percentage of each security in the portfolio.
     *
     * @param portfolioList List of Portfolio objects
     * @param totalPortfolioMarketValue Total market value of the portfolio
     */
    public void calculateSecurityPerc(List<Portfolio> portfolioList, double totalPortfolioMarketValue) {
        portfolioList.forEach(portfolio -> portfolio.setPercentage(roundToNearestDouble(portfolio.getMarketValue() / totalPortfolioMarketValue * 100)));
    }

    /**
     * Updates the proposed target market value and target percentage for a portfolio.
     *
     * @param totalMarketValue Total market value of the portfolio
     * @param portfolio Portfolio object to update
     */
    private void updateProposedValues(double totalMarketValue, Portfolio portfolio) {
        portfolio.setTargetMV(roundToNearestDouble(portfolio.getTargetQty() * portfolio.getPrice()));
        portfolio.setTargetPercentage(roundToNearestDouble(portfolio.getTargetMV() / totalMarketValue * 100));
    }

    /**
     * Calculates the total market value of all portfolios in the list.
     *
     * @param portfolioList List of Portfolio objects
     * @return Total market value
     */
    public double getTotalMarketValue(List<Portfolio> portfolioList) {
        return portfolioList.stream().mapToDouble(Portfolio::getMarketValue).sum();
    }

    /**
     * Calculates the trades required to rebalance the portfolio.
     *
     * @param portfolioList List of Portfolio objects
     * @param totalPortfolioMarketValue Total market value of the portfolio
     */
    public void calculateTrades(List<Portfolio> portfolioList, double totalPortfolioMarketValue) {
        calculateNonCashTrades(portfolioList);
        double actualTradeCostOfUSD = calculateCashTradeCost(portfolioList);
        updateCashPortfolio(portfolioList, actualTradeCostOfUSD);
        portfolioList.forEach(portfolio -> updateProposedValues(totalPortfolioMarketValue, portfolio));
    }

    /**
     * Calculates trade amounts for non-cash securities in the portfolio.
     *
     * @param portfolioList List of Portfolio objects
     */
    private void calculateNonCashTrades(List<Portfolio> portfolioList) {
        portfolioList.stream()
                .filter(portfolio -> !USD_SECURITY.equalsIgnoreCase(portfolio.getSecurity()))
                .forEach(portfolio -> {
                    portfolio.setProposedTradeAmount(portfolio.getProposedTargetMV() - portfolio.getMarketValue());
                    portfolio.setRoundedTradeQty(Math.round(portfolio.getProposedTradeAmount() / portfolio.getPrice()));
                    portfolio.setActualTradeCost(-portfolio.getRoundedTradeQty() * portfolio.getPrice());
                    portfolio.setTargetQty(portfolio.getQuantity() + portfolio.getRoundedTradeQty());
                });
    }

    /**
     * Updates the cash portfolio with the actual trade cost.
     *
     * @param portfolioList List of Portfolio objects
     * @param actualTradeCostOfUSD Actual trade cost for USD security
     */
    private void updateCashPortfolio(List<Portfolio> portfolioList, double actualTradeCostOfUSD) {
        portfolioList.stream().filter(portfolio -> USD_SECURITY.equalsIgnoreCase(portfolio.getSecurity()))
                .forEach(portfolio -> {
                    portfolio.setActualTradeCost(actualTradeCostOfUSD);
                    portfolio.setTargetQty(roundToNearestDouble(portfolio.getActualTradeCost() / portfolio.getPrice() + portfolio.getQuantity()));
                });
    }

    /**
     * Calculates the total actual trade cost for all non-cash securities.
     *
     * @param portfolioList List of Portfolio objects
     * @return Total actual trade cost for non-cash securities
     */
    private double calculateCashTradeCost(List<Portfolio> portfolioList) {
        return roundToNearestDouble(
                portfolioList.stream()
                        .filter(portfolio -> !USD_SECURITY.equalsIgnoreCase(portfolio.getSecurity()))
                        .mapToDouble(Portfolio::getActualTradeCost)
                        .sum());
    }
}