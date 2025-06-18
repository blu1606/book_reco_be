package backend.model;

import java.util.List;

public class ModelListResponse {
    private String object;
    private List<GeminiModel> data;

    public ModelListResponse(String object, List<GeminiModel> data) {
        this.object = object;
        this.data = data;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<GeminiModel> getData() {
        return data;
    }

    public void setData(List<GeminiModel> data) {
        this.data = data;
    }
} 