package com.udacity.project4.view.adapter

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.databinding.ItReminderBinding
import com.udacity.project4.view.reminderslist.ReminderListFragmentDirections

/**Created by
Author: Ankush Bose
Date: 04,April,2021
 **/
class ReminderViewHolder(private val binding: ItReminderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(reminder: Reminder) {
        binding.apply {
            item = reminder
            reminderCardView.setOnClickListener {
                val action =
                    ReminderListFragmentDirections
                        .actionReminderListFragmentToFragmentReminderDetails(reminder)
                it.findNavController().navigate(action)
            }
            executePendingBindings()
        }
    }
}