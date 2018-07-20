package com.ksballetba.fluemusic.ui.fragment


import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide

import com.ksballetba.fluemusic.R
import com.ksballetba.fluemusic.ui.adapter.PlaylistAdapter
import com.ksballetba.fluemusic.utils.model.PlaylistModel
import com.ksballetba.fluemusic.utils.network.NetworkManager
import kotlinx.android.synthetic.main.fragment_music.*
import kotlinx.android.synthetic.main.fragment_music.view.*
import org.jetbrains.anko.db.DEFAULT
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MusicFragment : Fragment() {


    var mPlaylist = ArrayList<PlaylistModel>()
    var favPlaylist = ArrayList<PlaylistModel>()

    lateinit var myPlayListRec: RecyclerView
    lateinit var favPlayListRec: RecyclerView

    lateinit var musicSwipe: SwipeRefreshLayout

    lateinit var myAdapter: PlaylistAdapter
    lateinit var favAdapter: PlaylistAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View){
        musicSwipe = view.music_swipe
        myPlayListRec = view.my_playlist
        favPlayListRec = view.fav_playlist
        myPlayListRec.isNestedScrollingEnabled = false
        favPlayListRec.isNestedScrollingEnabled = false
        val mylayoutManager = LinearLayoutManager(activity)
        mylayoutManager.orientation = LinearLayout.VERTICAL
        val favlayoutManager = LinearLayoutManager(activity)
        mylayoutManager.orientation = LinearLayout.VERTICAL
        myPlayListRec.layoutManager = mylayoutManager
        favPlayListRec.layoutManager = favlayoutManager
        myAdapter = PlaylistAdapter(mPlaylist){idx ->
            toast(mPlaylist[idx].name)
        }
        favAdapter = PlaylistAdapter(favPlaylist){idx ->
            toast(favPlaylist[idx].name)
        }
        setOnClick()
        myPlayListRec.itemAnimator = DefaultItemAnimator()
        favPlayListRec.itemAnimator = DefaultItemAnimator()
        my_playlist.adapter = myAdapter
        fav_playlist.adapter = favAdapter
        musicSwipe.setOnRefreshListener {
            refreshList()
        }
        if (mPlaylist.size == 0) {
            refreshList()
        }
        musicSwipe.post { musicSwipe.isRefreshing =true }

    }

    private fun setOnClick(){
        myplaylist_switch.setOnClickListener {
            when(myPlayListRec.visibility){
                View.GONE->{
                    myPlayListRec.visibility = View.VISIBLE
                    Glide.with(context!!).load(R.drawable.ic_keyboard_arrow_down_black_18dp).into(myplaylist_onoff)
                }
                View.VISIBLE->{
                    myPlayListRec.visibility = View.GONE
                    Glide.with(context!!).load(R.drawable.ic_keyboard_arrow_right_black_18dp).into(myplaylist_onoff)
                }
            }
        }
        favplaylist_switch.setOnClickListener {
            when(favPlayListRec.visibility){
                View.GONE->{
                    favPlayListRec.visibility = View.VISIBLE
                    Glide.with(context!!).load(R.drawable.ic_keyboard_arrow_down_black_18dp).into(favplaylist_onoff)
                }
                View.VISIBLE->{
                    favPlayListRec.visibility = View.GONE
                    Glide.with(context!!).load(R.drawable.ic_keyboard_arrow_right_black_18dp).into(favplaylist_onoff)
                }
            }
        }
    }


    private fun refreshList(){
        val uid = PreferenceManager.getDefaultSharedPreferences(context).getString("uid","")
        doAsync {
            NetworkManager.getPlaylistAll(uid) { res, error ->
                if (error == null) {
                    uiThread {
                        mPlaylist = NetworkManager.getUserPlaylist(res,uid)
                        favPlaylist = NetworkManager.getFavPlaylist(res,uid)
                        myAdapter.update(mPlaylist)
                        favAdapter.update(favPlaylist)
                        musicSwipe.isRefreshing = false
//                        toast(mPlaylist.size)
                    }
                }
            }
        }
    }
}
