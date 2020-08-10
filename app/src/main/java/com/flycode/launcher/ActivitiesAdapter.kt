package com.flycode.launcher

import android.R
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ActivitiesAdapter(var context:Context) : RecyclerView.Adapter<ActivitiesAdapter.ActivitiesViewHolder>() {

    private var mActivities: List<ResolveInfo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater
            .inflate(R.layout.simple_list_item_1, parent, false)
        return ActivitiesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mActivities!!.size
    }

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        val resolveInfo = mActivities!![position]
        holder.bindActivity(resolveInfo)
    }

    fun setItems(list:List<ResolveInfo>){
        mActivities = list
        notifyDataSetChanged()
    }

    inner class ActivitiesViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private var mResolveInfo: ResolveInfo? = null
        private var mNameTextView: TextView? = null

        init {
            mNameTextView = view as TextView
        }
        fun bindActivity(resolveInfo: ResolveInfo) {
            mResolveInfo = resolveInfo
            val pm: PackageManager = context.getPackageManager()
            val appName = mResolveInfo!!.loadLabel(pm).toString()
            mNameTextView!!.text = appName
            mNameTextView!!.setOnClickListener {
                var activityInfo = mResolveInfo!!.activityInfo
                var i = Intent(Intent.ACTION_MAIN).setClassName(activityInfo.applicationInfo.packageName,activityInfo.name)
                context.startActivity(i)
            }
        }
    }
}