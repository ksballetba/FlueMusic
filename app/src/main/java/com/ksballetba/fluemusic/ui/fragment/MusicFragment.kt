package com.ksballetba.fluemusic.ui.fragment


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

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

    lateinit var myPlayListRec: RecyclerView

    lateinit var musicSwipe: SwipeRefreshLayout

    lateinit var adapter: PlaylistAdapter


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
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayout.VERTICAL
        myPlayListRec.layoutManager = layoutManager
        adapter = PlaylistAdapter(mPlaylist){idx ->
            toast(mPlaylist[idx].name)
        }
        myPlayListRec.itemAnimator = DefaultItemAnimator()
        my_playlist.adapter = adapter
        musicSwipe.setOnRefreshListener {
            refreshList()
        }
        musicSwipe.post { musicSwipe.isRefreshing =true }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && mPlaylist.size == 0) {
            refreshList()
        }

    }


    private fun refreshList(){
        doAsync {
            NetworkManager.getUserPlaylistAll("34157213") { res, error ->
                if (error == null) {
                    uiThread {
                        mPlaylist = NetworkManager.getUserPlaylist(res)
                        adapter.update(mPlaylist)
                        musicSwipe.isRefreshing = false
                    }
                }
            }
        }
    }
}
