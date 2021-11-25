package ru.ulybka

import android.app.Application
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ulybka.data.remote.IRetrofitService
import okhttp3.OkHttpClient

/**
 * Singleton for Retrofit
 */

class App: Application() {

    lateinit var service: IRetrofitService

    override fun onCreate() {
        super.onCreate()

        configureRetrofit()
    }

    private fun configureRetrofit() {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .authenticator(object : okhttp3.Authenticator {
                override fun authenticate(route: Route?, response: Response): Request? {
                    val request: Request = response.request
                    if (request.header("Authorization") != null) // Логин и пароль неверны
                        return null

                    return request.newBuilder()
                        .header("Authorization", Credentials.basic("l12345678", "p12345678"))
                        .build()
                }

            })
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api-test.tdera.ru")
            .build()

        service = retrofit.create(IRetrofitService::class.java)
    }
}