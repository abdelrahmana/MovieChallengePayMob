package com.Movie.challenge.ui.fragment.moviedetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.Movie.androidtask.data.model.Constants.MOVIE_ID
import com.Movie.androidtask.util.CommonUtil
import com.banquemisr.challenge05.R
import com.Movie.challenge.data.model.ProductionCompany
import com.Movie.challenge.data.model.Result
import com.Movie.challenge.data.model.SourceModel
import com.banquemisr.challenge05.databinding.MovieDetailsFragmentBinding
import com.Movie.challenge.ui.fragment.moviedetails.adaptor.ProductionCompanyAdaptor
import com.Movie.challenge.ui.fragment.moviefragment.MovieViewModel
import com.Movie.challenge.ui.fragment.moviefragment.Offline
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(){
    @Inject lateinit var utils : CommonUtil
    @Inject lateinit var sharedPrefs : SharedPreferences
    val model : MovieViewModel by viewModels()
    var binding : MovieDetailsFragmentBinding ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = MovieDetailsFragmentBinding.inflate(layoutInflater, container, false)
        binding?.containerHeader?.backArrow?.setOnClickListener{
            findNavController().popBackStack()

        }
        model.getMovieDetails(
             Offline(),arguments?.getInt(MOVIE_ID)?:0
        )
        setViewModelObservers()
        return binding!!.root
    }

    private fun setViewModelObservers() {
        model.movieLiveData.observe(viewLifecycleOwner, Observer{ // for sending to maintenance
            if (it!=null){
                setViews(it,requireContext(),SourceModel.baseUrlImage)

            }
        })

        model.networkLoader.observe(viewLifecycleOwner, Observer{
            it?.let { progress->
                binding?.progress?.visibility = it
                model.setNetworkLoader(null)
            }
        })

        model.errorViewModel.observe(viewLifecycleOwner) {
            it?.let { error ->
                utils.showSnackMessages(requireActivity(), error)

            }
        }
    }
    var adaptorRecycleCompanies : ProductionCompanyAdaptor? = null
    private fun setViews(it: Result,context : Context,baseUrl : String) {
        binding?.movieName?.text = it.title?:""
        binding?.productionYear?.text = it.release_date?:""
        binding?.ratingText?.text = it.vote_average.toString()
        binding?.overViewText?.text = it.overview.toString()
        binding?.containerHeader?.headerTitle?.text = it.title?:""
        setIsFavourite(it)
        binding?.favouriteUnFavourite?.setOnClickListener{result->
            it.isFavourite = !(it.isFavourite?:false)
            model.updateMovieFavouriteUnfavourite(it)
            setIsFavourite(it)
        }

        Glide.with(context).load( baseUrl+it.poster_path)
            .error(R.color.gray_new).placeholder(R.color.gray_new).dontAnimate().into(binding!!.posterImage)
        it.production_companies?.let { casts->
            adaptorRecycleCompanies = ProductionCompanyAdaptor(context,
                casts as ArrayList<ProductionCompany>
            )
            utils.setRecycleView(binding!!.castRecycle,adaptorRecycleCompanies!!,LinearLayoutManager.VERTICAL,context,null,false
            )
        }
    }

    private fun setIsFavourite(it: Result) {
        if (it.isFavourite == true)
            binding?.favouriteUnFavourite?.setImageResource(R.drawable.favourite)
        else
            binding?.favouriteUnFavourite?.setImageResource(R.drawable.unfavourite)
    }

}