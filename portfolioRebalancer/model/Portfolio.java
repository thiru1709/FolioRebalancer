package portfolioRebalancer.model;

public class Portfolio {

    private String security;
    private double price;
    private double quantity;
    private double marketValue;

    private double percentage;
    private double driftFromMode;

    private double proposedTargetMV;
    private double proposedTradeAmount;
    private long roundedTradeQty;
    private double actualTradeCost;

    private double targetQty;
    private double targetMV;
    private double targetPercentage;

    public Portfolio() {
    }

    public Portfolio(String security, double price, double quantity) {
        this.security = security;
        this.price = price;
        this.quantity = quantity;
        this.marketValue = price * quantity;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        security = security;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }


    public double getDriftFromMode() {
        return driftFromMode;
    }

    public void setDriftFromMode(double driftFromMode) {
        this.driftFromMode = driftFromMode;
    }

    public double getProposedTargetMV() {
        return proposedTargetMV;
    }

    public void setProposedTargetMV(double proposedTargetMV) {
        this.proposedTargetMV = proposedTargetMV;
    }

    public double getProposedTradeAmount() {
        return proposedTradeAmount;
    }

    public void setProposedTradeAmount(double proposedTradeAmount) {
        this.proposedTradeAmount = proposedTradeAmount;
    }

    public long getRoundedTradeQty() {
        return roundedTradeQty;
    }

    public void setRoundedTradeQty(long roundedTradeQty) {
        this.roundedTradeQty = roundedTradeQty;
    }

    public double getActualTradeCost() {
        return actualTradeCost;
    }

    public void setActualTradeCost(double actualTradeCost) {
        this.actualTradeCost = actualTradeCost;
    }

    public double getTargetQty() {
        return targetQty;
    }

    public void setTargetQty(double targetQty) {
        this.targetQty = targetQty;
    }

    public double getTargetMV() {
        return targetMV;
    }

    public void setTargetMV(double targetMV) {
        this.targetMV = targetMV;
    }

    public double getTargetPercentage() {
        return targetPercentage;
    }

    public void setTargetPercentage(double targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "Security='" + security + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", marketValue=" + marketValue +
                ", percentage=" + percentage +
                ", driftFromMode=" + driftFromMode +
                ", proposedTargetMV=" + proposedTargetMV +
                ", proposedTradeAmount=" + proposedTradeAmount +
                ", roundedTradeQty=" + roundedTradeQty +
                ", actualTradeCost=" + actualTradeCost +
                ", targetQty=" + targetQty +
                ", targetMV=" + targetMV +
                ", targetPercentage=" + targetPercentage +
                '}';
    }
}
