package com.example.testgitapp.presentation.github_user_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.example.testgitapp.BuildConfig
import com.example.testgitapp.R
import com.example.testgitapp.data.remote.api.GithubApi
import com.example.testgitapp.data.remote.models.DataMapper
import com.example.testgitapp.data.remote.repository.GithubRepositoryImpl
import com.example.testgitapp.databinding.GithubUserDetailFragmentBinding
import com.example.testgitapp.presentation.models.DetailUiModel
import com.example.testgitapp.presentation.models.GithubUserDetailsResult
import com.example.testgitapp.presentation.models.GithubUsersResult
import com.example.testgitapp.presentation.models.UiMapper
import com.example.testgitapp.presentation.viewmodels.ViewModelFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GithubUserDetailFragment : Fragment() {

    private var _binding: GithubUserDetailFragmentBinding? = null

    private val binding get() = _binding!!

    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .setLenient()
        .create()


    private val httpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
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

    private val githubRepository by lazy {
        GithubRepositoryImpl(api, DataMapper.BaseDataMapper())
    }

    private val gitHubUserDetailViewModel: GitHubUserDetailViewModel by  viewModels {
        ViewModelFactory.provideDetailViewModel(githubRepository, UiMapper.BaseUiMapper())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = GithubUserDetailFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderResult()
        gitHubUserDetailViewModel.loadDetail(
            GithubUserDetailFragmentArgs.fromBundle(requireArguments()).userName
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderResult(){
        gitHubUserDetailViewModel.usersResult.observe(viewLifecycleOwner) { resultData ->
            when(resultData) {

                is GithubUserDetailsResult.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.userDetailLayout.visibility = View.GONE
                }

                is GithubUserDetailsResult.Error -> {
                    binding.progressbar.visibility = View.GONE
                    binding.userDetailLayout.visibility = View.GONE
                    resultData.throwable.message?.let{
                        Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
                    }
                }

                is GithubUserDetailsResult.Success -> {
                    binding.userDetailLayout.visibility = View.VISIBLE
                    binding.progressbar.visibility = View.GONE
                    configureView(resultData.detail)
                }
            }
        }
    }

    private fun configureView(detailUiModel: DetailUiModel){
        with(binding){

            email.text = detailUiModel.login.getOrDefault("Name not Found")
            bio.text = detailUiModel.blog.getOrDefault("Bio not Found")
            location.text = detailUiModel.location.getOrDefault("Location not Found")
            createdTime.text = detailUiModel.createdAt.getOrDefault("Created time not Found")
            updatedTime.text = detailUiModel.updatedAt.getOrDefault("Updated time not Found")
            subscribers.text = "Subscribers ${detailUiModel.following?: "0"}"
            flowers.text = "Followers ${detailUiModel.followers ?: "0"}"

            Glide.with(this@GithubUserDetailFragment)
                .load(detailUiModel.avatarUrl)
                .placeholder(R.drawable.baseline_person_pin_24)
                .error(R.drawable.baseline_person_pin_24)
                .override(400, 400)
                .circleCrop()
                .into(avatar)

        }
    }

    infix fun String?.getOrDefault(defult: String):String{
        return if (this.isNullOrEmpty()) defult else this
    }

}