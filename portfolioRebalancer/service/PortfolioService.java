package portfolioRebalancer.service;

import portfolioRebalancer.model.Portfolio;

import java.util.List;

import static portfolioRebalancer.service.UtilityService.roundToNearestDouble;

public class PortfolioService {


    public static final String USD_SECURITY = "USD";

    public void calculateSecurityPerc(List<Portfolio> portfolioList, double totalPortfolioMarketValue) {
        portfolioList.forEach(portfolio -> portfolio.setPercentage(roundToNearestDouble(portfolio.getMarketValue() / totalPortfolioMarketValue * 100)));
    }


    private void updateProposedValues(double totalMarketValue, Portfolio portfolio) {
        portfolio.setTargetMV(roundToNearestDouble(portfolio.getTargetQty() * portfolio.getPrice()));
        portfolio.setTargetPercentage(roundToNearestDouble(portfolio.getTargetMV() / totalMarketValue * 100));
    }

    public double getTotalMarketValue(List<Portfolio> portfolioList) {
        return portfolioList.stream().mapToDouble(Portfolio::getMarketValue).sum();
    }

    public void calculateTrades(List<Portfolio> portfolioList, double totalPortfolioMarketValue) {
        calculateNonCashTrades(portfolioList);
        double actualTradeCostOfUSD = calculateCashTradeCost(portfolioList);
        updateCashPortfolio(portfolioList, actualTradeCostOfUSD);
        portfolioList.forEach(portfolio -> updateProposedValues(totalPortfolioMarketValue, portfolio));
    }

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

    private void updateCashPortfolio(List<Portfolio> portfolioList, double actualTradeCostOfUSD) {
        portfolioList.stream().filter(portfolio -> USD_SECURITY.equalsIgnoreCase(portfolio.getSecurity()))
                .forEach(portfolio -> {
                    portfolio.setActualTradeCost(actualTradeCostOfUSD);
                    portfolio.setTargetQty(roundToNearestDouble(portfolio.getActualTradeCost() / portfolio.getPrice() + portfolio.getQuantity()));
                });
    }

    private double calculateCashTradeCost(List<Portfolio> portfolioList) {
        return roundToNearestDouble(
                portfolioList.stream()
                        .filter(portfolio -> !USD_SECURITY.equalsIgnoreCase(portfolio.getSecurity()))
                        .mapToDouble(Portfolio::getActualTradeCost)
                        .sum());
    }
}
