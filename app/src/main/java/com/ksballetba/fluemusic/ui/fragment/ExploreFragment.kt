package com.ksballetba.fluemusic.ui.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide

import com.ksballetba.fluemusic.R
import com.ksballetba.fluemusic.ui.adapter.PlaylistAdapter
import com.ksballetba.fluemusic.ui.adapter.PlaylistAdapter2
import com.ksballetba.fluemusic.utils.model.BannerModel
import com.ksballetba.fluemusic.utils.model.HighPlaylistModel
import com.ksballetba.fluemusic.utils.model.PlaylistModel
import com.ksballetba.fluemusic.utils.network.NetworkManager
import com.youth.banner.loader.ImageLoader
import com.zhouwei.mzbanner.holder.MZHolderCreator
import com.zhouwei.mzbanner.holder.MZViewHolder
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.fragment_music.*
import kotlinx.android.synthetic.main.fragment_music.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.support.v4.uiThread
import org.jetbrains.anko.uiThread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ExploreFragment : Fragment() {

    var highPlaylist = ArrayList<HighPlaylistModel>()
    var hotPlaylist = ArrayList<HighPlaylistModel>()
    var newPlaylist = ArrayList<HighPlaylistModel>()


    lateinit var highAdapter: PlaylistAdapter2
    lateinit var hotAdapter: PlaylistAdapter2
    lateinit var newAdapter: PlaylistAdapter2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        val highGridLayoutManager = GridLayoutManager(context,2)
        highGridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        val hotGridLayoutManager = GridLayoutManager(context,2)
        hotGridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        val newGridLayoutManager = GridLayoutManager(context,2)
        newGridLayoutManager.orientation = GridLayoutManager.HORIZONTAL

        highAdapter = PlaylistAdapter2(highPlaylist){idx ->
            toast(highPlaylist[idx].name)
        }
        hotAdapter = PlaylistAdapter2(hotPlaylist){idx ->
            toast(hotPlaylist[idx].name)
        }
        newAdapter = PlaylistAdapter2(newPlaylist){idx ->
            toast(newPlaylist[idx].name)
        }

        high_playlist_rec.layoutManager = highGridLayoutManager
        hot_playlist_rec.layoutManager = hotGridLayoutManager
        new_playlist_rec.layoutManager = newGridLayoutManager

        high_playlist_rec.adapter = highAdapter
        hot_playlist_rec.adapter = hotAdapter
        new_playlist_rec.adapter = newAdapter

        high_playlist_rec.itemAnimator = DefaultItemAnimator()
        high_playlist_rec.itemAnimator = DefaultItemAnimator()
        new_playlist_rec.itemAnimator = DefaultItemAnimator()

        showBanner()

        showPlaylist()
    }


    private fun showBanner(){
        NetworkManager.getBannerAll { res, error ->
            if(error ==null){
                val list:ArrayList<String>? = ArrayList()
                val mBanners = NetworkManager.getBanner(res)
                for(i in 0 until mBanners.size){
                    list?.add(mBanners[i].picUrl)
                }
                toast(mBanners[0].toString())
                banner.setImages(list).setImageLoader(GlideImageLoader()).start()
            }
        }
    }

    private fun showPlaylist(){
        doAsync {
            NetworkManager.getHighPlaylistAll(NetworkManager.highPlaylistUrl){res, error ->
                if(error==null){
                    uiThread {
                    highPlaylist = NetworkManager.getRecPlaylist(res)
                    highAdapter.update(highPlaylist)
                    }
                }
            }

            NetworkManager.getHighPlaylistAll(NetworkManager.hotPlaylistUrl){res, error ->
                if(error==null){
                    uiThread {
                    hotPlaylist = NetworkManager.getRecPlaylist(res)
                    hotAdapter.update(hotPlaylist)
                    }
                }
            }

            NetworkManager.getHighPlaylistAll(NetworkManager.newPlaylistUrl){res, error ->
                if(error==null){
                    uiThread {
                     newPlaylist = NetworkManager.getRecPlaylist(res)
                     newAdapter.update(newPlaylist)
                    }
                }
            }
        }

    }
}

class GlideImageLoader:ImageLoader(){
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        Glide.with(context!!).load(path).into(imageView!!)
    }
}


