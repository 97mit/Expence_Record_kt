package com.stimednp.roommvvm.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide.init
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.adapter.GenericAdapter
import com.stimednp.roommvvm.data.db.entity.Article
import com.stimednp.roommvvm.databinding.AdapterNewsItemBinding
import com.stimednp.roommvvm.databinding.FragmentSlideshowBinding
import com.stimednp.roommvvm.utils.DataHandler
import com.stimednp.roommvvm.utils.UtilFunctions.LogData
import com.stimednp.roommvvm.utils.UtilFunctions.loadImageFromGlide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    private val binding get() = _binding!!
    @Inject
    lateinit var newsAdapter: GenericAdapter<Article>
    val viewModel: SlideshowViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        viewModel.topHeadlines.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    newsAdapter.listOfItems = (dataHandler.data?.articles) as MutableList<Article>
                }
                is DataHandler.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    LogData("onViewCreated: ERROR " + dataHandler.message)
                }
                is DataHandler.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    LogData("onViewCreated: LOADING..")

                }
            }

        }
        viewModel.getTopHeadlines()
    }
    private fun init() {
        newsAdapter = GenericAdapter<Article>()
        newsAdapter.expressionViewHolderBinding = {article,viewBinding->
            var view = viewBinding as AdapterNewsItemBinding
            view.ivArticle.loadImageFromGlide(article.urlToImage)
            view.tvTitle.text = article.title
            view.tvDescription.text = article.description
            view.tvSource.text = article.author
            view.tvPublished.text = article.publishedAt
            view.root.setOnClickListener {
                //Click item value is eachItem
                /*val bundle = Bundle().apply {
                    putParcelable("article_data", it)

                }
                findNavController().navigate(
                    R.id.action_onlineFragment_to_articleDetailsFragment,
                    bundle
                )*/
            }
        }
        newsAdapter.expressionOnCreateViewHolder = {viewGroup->
            AdapterNewsItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }

        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}