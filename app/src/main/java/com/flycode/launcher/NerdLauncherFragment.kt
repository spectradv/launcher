package com.flycode.launcher

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_nerd_launcher.*
import java.util.*
import kotlin.Comparator


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NerdLauncherFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var adapter:ActivitiesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nerd_launcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

    fun setupAdapter(){
        val startupIntent = Intent(Intent.ACTION_MAIN)
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pm = activity!!.packageManager
        val activities =
            pm.queryIntentActivities(startupIntent, 0)
        Collections.sort(activities, object : Comparator<ResolveInfo?> {
            override fun compare(o1: ResolveInfo?, o2: ResolveInfo?): Int {
                val pm = activity!!.packageManager
                return java.lang.String.CASE_INSENSITIVE_ORDER.compare(
                    o1!!.loadLabel(pm).toString(),
                    o2!!.loadLabel(pm).toString()
                )
            }

        })
        adapter = ActivitiesAdapter(activity!!)
        app_recycler_view.adapter = adapter
       adapter.setItems(activities)

        Log.i("ACT", "Found " + activities.size + " activities.")
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NerdLauncherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}