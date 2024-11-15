package com.example.testgitapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testgitapp.BuildConfig
import com.example.testgitapp.R
import com.example.testgitapp.data.remote.GithubApi
import com.example.testgitapp.data.remote.models.DataMapper
import com.example.testgitapp.data.remote.repository.GithubRepositoryImpl
import com.example.testgitapp.presentation.adapters.GithubUserAdapter
import com.example.testgitapp.presentation.models.GithubUsersResult
import com.example.testgitapp.presentation.models.UiMapper
import com.example.testgitapp.presentation.viewmodels.GithubViewModel
import com.example.testgitapp.presentation.viewmodels.ViewModelFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Logger


class MainActivity : AppCompatActivity() {

    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .setLenient()
        .create()

    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor(object : Logger("Logging",null),
        HttpLoggingInterceptor.Logger {

        override fun log(message: String) {
            Log.d("HttpLoggingInterceptor",message)
        }
    })

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.addHeader(
                    "Authorization",
                    BuildConfig.API_KEY
                )
                chain.proceed(requestBuilder.build())
            }.build()

    }

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(httpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(GithubApi::class.java)
    }

    private val adapter by lazy {
        GithubUserAdapter()
    }

    private val githubViewModel: GithubViewModel by  viewModels {
        ViewModelFactory.provideViewModel(GithubRepositoryImpl(api,DataMapper.BaseDataMapper()), UiMapper.BaseUiMapper())
    }

    private var recycler: RecyclerView? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        githubViewModel.loadUsers()
        renderResult()
    }

    override fun onDestroy() {
        super.onDestroy()
        recycler = null
    }

    private fun setupRecyclerView(){
       recycler = findViewById(R.id.users_recycler_view)
       recycler?.adapter = adapter
       recycler?.layoutManager = LinearLayoutManager(this)
       recycler?.setHasFixedSize(true);
   }

    private fun renderResult(){
        githubViewModel.usersResult.observe(this) { resultData ->
            when(resultData){
                is GithubUsersResult.Loading ->{
                    findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
                }
                is GithubUsersResult.Error -> {
                    findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                    resultData.throwable.message?.let{
                        Toast.makeText(this,it,Toast.LENGTH_LONG).show()
                    }
                }
                is GithubUsersResult.Success -> {
                    findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                    adapter.submitData(lifecycle, resultData.users)
                }
            }
        }
    }
}