package portfolioRebalancer.Reader;

import portfolioRebalancer.model.Model;
import portfolioRebalancer.model.Portfolio;

import java.util.List;

public interface PortfolioReader {

    public List<Portfolio> getPortfolioList(String filePath);
    public List<Model> getModelList(String filePath);
}
