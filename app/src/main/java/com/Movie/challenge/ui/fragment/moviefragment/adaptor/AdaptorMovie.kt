package com.Movie.challenge.ui.fragment.moviefragment.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.banquemisr.challenge05.R
import com.Movie.challenge.data.model.Result
import com.Movie.challenge.data.model.SourceModel.Companion.baseUrlImage
import com.banquemisr.challenge05.databinding.OneItemMovieBinding


class AdaptorMovie( // one selection
    var context: Context, var arrayList: ArrayList<Result>,
    val callBackClick: (Result) -> Unit,
    val clickFavouriteUnFavourite: (Result) -> Unit

)
   // var selectedArrayList: ArrayList<ModelTrip>
 :
    RecyclerView.Adapter<AdaptorMovie.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val binding = OneItemMovieBinding.inflate(LayoutInflater.from(context),parent,false)

        return ViewHolder(
            binding
        )

    }

    override fun getItemCount(): Int {

        return arrayList.size

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(arrayList[position])

    }
   inner class ViewHolder(val itemViews: OneItemMovieBinding) : RecyclerView.ViewHolder(itemViews.root) {
       fun bindItems(
           currentItem: Result?
       ) {
           itemViews.name.text = currentItem?.title?:""
           itemViews.yearOfProduction.text = currentItem?.release_date?:""
           if (currentItem?.isFavourite == true)
               itemViews.favouriteUnFavourite.setImageResource(R.drawable.favourite)
           else
               itemViews.favouriteUnFavourite.setImageResource(R.drawable.unfavourite)

           Glide.with(itemViews.root.context).load(baseUrlImage+currentItem?.poster_path)
               .error(R.color.gray_new).placeholder(R.color.gray_new).dontAnimate().into(itemViews.movieImage)
           itemViews.cardOneItem.setOnClickListener{
               callBackClick.invoke(currentItem!!)
           }
           itemViews.favouriteUnFavourite.setOnClickListener{
               clickFavouriteUnFavourite(currentItem!!)
               notifyDataSetChanged()
           }
       }


   }
    fun updateList(results: List<Result>) {
     //   if (arrayList.isEmpty()) {
            arrayList.addAll(results)
            notifyDataSetChanged()
      /*  }
        else {
            arrayList.addAll(results)
            notifyItemRangeChanged(0,arrayList.size)
        }*/
    }

}