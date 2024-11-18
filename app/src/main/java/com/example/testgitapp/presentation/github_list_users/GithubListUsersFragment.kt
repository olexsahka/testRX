package com.example.testgitapp.presentation.github_list_users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testgitapp.BuildConfig
import com.example.testgitapp.data.remote.api.GithubApi
import com.example.testgitapp.data.remote.models.DataMapper
import com.example.testgitapp.data.remote.repository.GithubRepositoryImpl
import com.example.testgitapp.data.remote.source.GettingUserPagingDataSourceImpl
import com.example.testgitapp.databinding.GithubListUsersFragmentBinding
import com.example.testgitapp.presentation.adapters.GithubUserAdapter
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

class GithubListUsersFragment: Fragment() {

    private var _binding: GithubListUsersFragmentBinding? = null

    private val binding get() = _binding!!

    private var observer : LiveData<GithubUsersResult>? = null

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

    private val githubAdapter by lazy {
        GithubUserAdapter { name ->
            val action = GithubListUsersFragmentDirections.actionGithubListUsersFragmentToGithubUserDetailFragment(name)
            findNavController().navigate(action)

        }
    }

    private val githubRepository by lazy {
        GithubRepositoryImpl(api, DataMapper.BaseDataMapper())
    }

    private val githubViewModel: GithubUserListViewModel by  viewModels {
        ViewModelFactory.provideListViewModel(GettingUserPagingDataSourceImpl(githubRepository), UiMapper.BaseUiMapper())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GithubListUsersFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        renderResult()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        observer = null
    }

    private fun setupRecyclerView(){
        with(binding.usersRecyclerView){
            this.adapter = githubAdapter
            this.layoutManager = LinearLayoutManager(requireContext())
            this.setHasFixedSize(true);
        }

   }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { githubViewModel.updateQuery(it) }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })
    }

    private fun renderResult(){
        observer = githubViewModel.usersResult
        observer?.observe(viewLifecycleOwner) { resultData ->
            when(resultData) {

                is GithubUsersResult.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is GithubUsersResult.Error -> {
                    binding.progressbar.visibility = View.GONE
                    resultData.throwable.message?.let{
                        Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
                    }
                }

                is GithubUsersResult.Success -> {
                    binding.progressbar.visibility = View.GONE
                    githubAdapter.submitData(requireActivity().lifecycle, resultData.users)
                }
            }
        }
    }
}