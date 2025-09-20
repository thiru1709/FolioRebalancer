package portfolioRebalancer.model;

public class Model {
    private String security;
    private double percentage;


    public Model(String security, double percentage) {
        this.security = security;
        this.percentage = percentage;
    }


    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Model{" +
                "security='" + security + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
