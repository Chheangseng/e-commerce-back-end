package com.tcs.e_commerce_back_end.utils.url;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UrlUtils {
    private static String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
    private static Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return params;
        }

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }
    public static String removeParameter(String urlString, String paramToRemove) {
        try {
            // Split URL into parts
            URL url = new URL(urlString);
            URI uri = url.toURI();
            String query = uri.getQuery();

            if (query == null || query.isEmpty()) {
                return urlString; // No query to remove
            }

            // Parse existing parameters
            Map<String, String> params = parseQuery(query);

            // Remove the specified parameter
            params.remove(paramToRemove);

            // Rebuild the query string
            StringBuilder newQuery = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    newQuery.append("&");
                }
                newQuery.append(entry.getKey()).append("=").append(encodeValue(entry.getValue()));
            }

            // Reconstruct the URL
            String newUrl = urlString.split("\\?")[0];
            if (!newQuery.toString().isEmpty()) {
                newUrl += "?" + newQuery.toString();
            }

            return newUrl;

        } catch (Exception e) {
            throw new RuntimeException("Failed to remove parameter from URL", e);
        }
    }
    public static String addParameter(String baseUrl, String paramName, String paramValue) {
        // Check if the base URL already has parameters (contains "?")
        if (baseUrl.contains("?")) {
            return baseUrl + "&" + paramName + "=" + encodeValue(paramValue);
        } else {
            return baseUrl + "?" + paramName + "=" + encodeValue(paramValue);
        }
    }
}
