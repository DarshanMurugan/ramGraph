package Exporter;

import Graph.Node;
import Graph.Graph;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class exportToJsonLD {
    private final Graph graph;

    public exportToJsonLD(Graph graph) {
        this.graph = graph;
    }

    // This acts as your JSON-LD @context
private static final Map<String, Object> STATIC_HEADER = Map.of(
       "ram","http://ramGraph.org/",
       "source", Map.of("@id","ram:source", "@type","@id"),
       "target", Map.of("@id","ram:target", "@type","@id"),
       "realtion","ram:relation",
       "@vocab", "ram:properties"
    
    );

    public void export() {
        ObjectMapper mapper = new ObjectMapper();
        
        // Optional: Enable pretty-printing so the output is formatted nicely
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            // 1. Build a single container map for the entire JSON-LD payload
            Map<String, Object> jsonLdDocument = new LinkedHashMap<>();
            jsonLdDocument.put("@context", STATIC_HEADER);
            // jsonLdDocument.put("@graph", new ArrayList<>(graph.getGraphNodes()));
            List<Node> nodes = graph.getGraphNodes();
            
            // 2. Serialize the entire structure ONCE
            String json = mapper.writeValueAsString(jsonLdDocument);
            
            // 3. Print the final valid JSON-LD
            System.out.println(json);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}


