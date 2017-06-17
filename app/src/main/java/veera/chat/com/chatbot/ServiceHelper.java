package veera.chat.com.chatbot;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceHelper {

    private static Retrofit instance;

    public ServiceHelper() {
    }

    /**
     * Creates new unique retrofit instance if not created already
     */
    public static synchronized Retrofit getRetrofitInstance() {
        if (instance == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            if (BuildConfig.DEBUG) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                logging.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.addInterceptor(logging);

            instance = new Retrofit
                    .Builder()
                    .baseUrl("http://www.personalityforge.com/")
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
        }

        return instance;

    }

    /**
     * Creates service to make network requests to endpoints
     *
     * @param apiService
     * @param retrofit
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> apiService, Retrofit retrofit) {
        return retrofit.create(apiService);
    }
}
