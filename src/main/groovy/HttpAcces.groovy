import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class HttpAcces {

    String sendRequest(String url, String method, RequestBody body = null) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build()

        Request request = new Request.Builder()
                .url(url)
                .method(method, body)
                .build()
        Response response = client.newCall(request).execute()
        response.body().string()
    }
}