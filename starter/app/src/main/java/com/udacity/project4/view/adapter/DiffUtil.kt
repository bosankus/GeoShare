package com.udacity.project4.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.udacity.project4.data.model.Reminder

/**Created by
Author: Ankush Bose
Date: 23,March,2021
 **/
class DiffUtil : DiffUtil.ItemCallback<Reminder>() {
    override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

}