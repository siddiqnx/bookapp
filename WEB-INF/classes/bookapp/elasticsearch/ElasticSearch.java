package bookapp.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.*;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.*;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.*;
import org.elasticsearch.index.reindex.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

import bookapp.Config;

public class ElasticSearch {
  
  private RestHighLevelClient client;
  private String ES_BOOKS_INDEX = "books";
  
  public ElasticSearch() {
    setClient();
  }
  
  private void setClient() {
    RestClientBuilder builder = 
      RestClient.builder(new HttpHost(Config.ES_HOST, Config.ES_PORT, "http"));
    client = new RestHighLevelClient(builder);    
  }

  private void closeClient() {
    try {
      client.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void indexBook(Integer bookId, String summary) {
    IndexRequest request = new IndexRequest(ES_BOOKS_INDEX);

    Map<String, Object> jsonMap = new HashMap<>();
    jsonMap.put("id", bookId);
    jsonMap.put("summary", summary);
    request.source(jsonMap);
    
    try {
      client.index(request, RequestOptions.DEFAULT);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      closeClient();
    }
  }

  public List<Integer> searchKeywordInSummary(String keyword) {
    SearchRequest request = new SearchRequest(ES_BOOKS_INDEX);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchQuery("summary", keyword));

    searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));

    request.source(searchSourceBuilder);

    try {
      SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);

      SearchHit[] searchHits = searchResponse.getHits().getHits();

      List<Integer> result = Arrays.stream(searchHits)
                              .map(hit -> hit.getSourceAsMap())
                              .map(source -> (Integer)source.get("id"))
                              .collect(Collectors.toList());

      return result;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      closeClient();
    }

    return null;
  }
  
  public void deleteBook(Integer bookId) {
    DeleteByQueryRequest request =
      new DeleteByQueryRequest(ES_BOOKS_INDEX);
      
    request.setConflicts("proceed");
    
    request.setQuery(new TermQueryBuilder("id", bookId));
    
    request.setMaxDocs(1);

    try {
      client.deleteByQuery(request, RequestOptions.DEFAULT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
