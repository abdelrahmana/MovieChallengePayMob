package com.Movie.swensonhe.di

import android.content.Context
import android.content.SharedPreferences
import com.Movie.androidtask.data.model.Constants.SHAREDPREFS
import com.Movie.androidtask.util.CommonUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class,
    FragmentComponent::class,ActivityComponent::class,ServiceComponent::class)
class CommonDi {
    @Provides
    fun getUtil(@ApplicationContext context: Context?): CommonUtil {
        return  CommonUtil(context!!)
    }
    @Provides
    fun getSharedPrefs(@ApplicationContext context: Context?): SharedPreferences {
        return context!!.getSharedPreferences(
            SHAREDPREFS,
            Context.MODE_PRIVATE
        )
    }
}