package com.udacity.project4.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.view.adapter.ReminderAdapter

/** This is an Util class to create custom BindingAdapters */

object BindingAdapters {

    /**
     * Use binding adapter to set the recycler view data using livedata object
     */
    @BindingAdapter("listData")
    @JvmStatic
    fun RecyclerView.bindRecyclerView(list: LiveData<List<Reminder?>>) {
        list.value.let {
            val reminderAdapter = ReminderAdapter()
            this.adapter = reminderAdapter
            reminderAdapter.submitList(it)
        }
    }

    /**
     * Use this binding adapter to show and hide the views using boolean variables
     */
    @BindingAdapter("android:fadeVisible")
    @JvmStatic
    fun setFadeVisible(view: View, visible: Boolean? = true) {
        if (view.tag == null) {
            view.tag = true
            view.visibility = if (visible == true) View.VISIBLE else View.GONE
        } else {
            view.animate().cancel()
            if (visible == true) {
                if (view.visibility == View.GONE)
                    view.fadeIn()
            } else {
                if (view.visibility == View.VISIBLE)
                    view.fadeOut()
            }
        }
    }

    /**
     * Use this binding adapter to show and hide the views using a list size
     */
    @BindingAdapter("isLoading")
    @JvmStatic
    fun View.bindLoadingState(size: Int) {
        this.visibility = when (size) {
            0 -> View.VISIBLE
            else -> View.GONE
        }
    }
}