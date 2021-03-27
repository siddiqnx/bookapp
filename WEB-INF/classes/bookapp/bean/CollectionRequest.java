package bookapp.bean;

import java.util.List;

public class CollectionRequest {
  public int userId;
  public int collectionId;
  public String userName;
  public String collectionName;

  public CollectionRequest(int userId, int collectionId, String userName, String collectionName) {
    this.userId = userId;
    this.collectionId = collectionId;
    this.userName = userName;
    this.collectionName = collectionName;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(int collectionId) {
    this.collectionId = collectionId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }
}