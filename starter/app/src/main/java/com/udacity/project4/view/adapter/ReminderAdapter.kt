package com.udacity.project4.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.databinding.ItReminderBinding

/**Created by
Author: Ankush Bose
Date: 04,April,2021
 **/
class ReminderAdapter : ListAdapter<Reminder, ReminderViewHolder>(DiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItReminderBinding.inflate(layoutInflater, parent, false)
        return ReminderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminderItem = getItem(position)
        holder.bind(reminderItem)
    }
}