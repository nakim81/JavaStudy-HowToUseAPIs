package book.search;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KakaoBookApi {
    private static final String API_KEY = "5fed397668a7901a3256971cf76831f5"; //Rest Key
    private static final String API_BASE_URL = "https://dapi.kakao.com/v3/search/book";
    private static final OkHttpClient client = new OkHttpClient(); //use to connect to the server and make a request
    private static final Gson gson = new Gson(); //use to parse Json

    public static List<Book> searchBooks(String title) throws IOException {
        // building url for connection
        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("query", title);

        // generate request object
        Request request = new Request.Builder().url(urlBuilder.build()).addHeader("Authorization", "KakaoAK " + API_KEY).build();

        //request using OkHttpClint object and get response
        try (Response response = client.newCall(request).execute()) {
            //if request failed
            if (!response.isSuccessful()) throw new IOException("Request failed: " +response);

            //if successfull, convert Json to JsonObject
            JsonObject jsonResponse = gson.fromJson(response.body().charStream(), JsonObject.class);
            //each book is a Json document. Get them as a Json array
            JsonArray documents = jsonResponse.getAsJsonArray("documents");

            //ArrayList for Book class
            List<Book> books = new ArrayList<>();
            for (JsonElement document : documents) {
                //convert each document to Json object
                JsonObject bookJson = document.getAsJsonObject();
                //generate Book object from Json element
                Book book = new Book(bookJson.get("title").getAsString(), bookJson.get("authors").getAsJsonArray().toString(), bookJson.get("publisher").getAsString(), bookJson.get("thumbnail").getAsString());
                //Put the object to the list
                books.add(book);
            }
            return books;
        }
    }
}
