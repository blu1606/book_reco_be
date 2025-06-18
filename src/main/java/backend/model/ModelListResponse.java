package backend.model;

import java.util.List;

public record ModelListResponse(String object, List<GeminiModel> data) {
}
