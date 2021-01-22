import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class HttpAcces {

    static String processaRequisicao(String url, String method, RequestBody body = null, String cookie = null) {

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build()

        if (cookie) {
            Request request = new Request.Builder()
                    .url(url)
                    .method(method, body)
                    .addHeader("Cookie", cookie)
                    .build()
            Response response = client.newCall(request).execute()
            return response.body().string()
        } else {
            Request request = new Request.Builder()
                    .url(url)
                    .method(method, body)
                    .build()
            Response response = client.newCall(request).execute()
            return response.body().string()
        }
    }
}
