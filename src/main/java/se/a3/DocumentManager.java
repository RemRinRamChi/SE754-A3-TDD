package se.a3;

import java.util.List;

public class DocumentManager {
    List<Document> _searchResults;
    List<Cluster> _clusters;

    IClusterer _clusterer;
    IKeywordExtractor _extractor;
    ITextSummariser _summariser;

    public DocumentManager(List<Document> searchResults, IClusterer clusterer, IKeywordExtractor extractor, ITextSummariser summariser){
        _searchResults = searchResults;
        _clusterer = clusterer;
        _extractor = extractor;
        _summariser = summariser;
    }

    public void createClusters(){
        _clusters = _clusterer.createClusters();
    }

    public List<Cluster> getClusters(){
        return _clusters;
    }

    public void assignPopularityToClusters(){
        int totalNoOfDocuments = 0;
        for(Cluster c : _clusters){
            totalNoOfDocuments += c.getNoOfDocuments();
        }
        for(Cluster c : _clusters){
            c.setPopularity(c.getNoOfDocuments()/totalNoOfDocuments);
        }
    }

    public void setClusterRelevance(int position, double relevance){
        if(relevance < 0 || relevance > 1){
            throw new InvalidOperationException();
        }

        try {
            _clusters.get(position).setRelevance(relevance);
        } catch (IndexOutOfBoundsException e){
            throw new InvalidOperationException();
        }
    }

    public double computeIdeaMaturity(){
        double maturity = 0;

        for(Cluster c : _clusters){
            maturity += c.getPopularity() * c.getRelevance();
        }

        return maturity;
    }
}
