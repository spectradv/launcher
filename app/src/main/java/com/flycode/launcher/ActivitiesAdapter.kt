package com.flycode.launcher

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class ActivitiesAdapter(var context:Context) : RecyclerView.Adapter<ActivitiesAdapter.ActivitiesViewHolder>() {

    private var mActivities: List<ResolveInfo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(
            R.layout.app_icon_item,
            parent,
            false
        )
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
        private var mName: TextView? = null
        private var mIcon: ImageView? = null
        private var mLayout: ConstraintLayout? = null

        init {
            mName = view.findViewById(R.id.name_app)
            mIcon = view.findViewById(R.id.icon_app)
            mLayout = view.findViewById(R.id.app_layout)
        }
        fun bindActivity(resolveInfo: ResolveInfo) {
            mResolveInfo = resolveInfo
            val pm: PackageManager = context.getPackageManager()
            val appName = mResolveInfo!!.loadLabel(pm).toString()
            mName!!.text = appName
            mIcon!!.setImageDrawable(mResolveInfo!!.loadIcon(pm))
            mLayout!!.setOnClickListener {
                var activityInfo = mResolveInfo!!.activityInfo
                var i = Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName,activityInfo.name)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i)
            }
        }
    }
}