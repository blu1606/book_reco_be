package backend.model;

public class GeminiModel {
    private String id;
    private String object;
    private String ownedBy;

    public GeminiModel(String id, String object, String ownedBy) {
        this.id = id;
        this.object = object;
        this.ownedBy = ownedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }
}
