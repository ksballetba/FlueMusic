package com.ksballetba.fluemusic.ui.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ksballetba.fluemusic.R
import com.ksballetba.fluemusic.utils.model.HighPlaylistModel

class PlaylistAdapter2(val mItems:ArrayList<HighPlaylistModel>?,internal val didSelectedAtPos:(idx:Int)->Unit):RecyclerView.Adapter<PlaylistAdapter2.ViewHolder>() {
    internal var mContext:Context? = null
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var playlistListenCount = view.findViewById<TextView>(R.id.playlist_listencount)
        var playlistCardView = view.findViewById<CardView>(R.id.playlist_cardview)
        var playlistImage = view.findViewById<ImageView>(R.id.playlist2_image)
        var playlistName = view.findViewById<TextView>(R.id.playlist2_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext==null) {
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.playlist_item2,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun bind(model: HighPlaylistModel){
            val playlistenCount = if(model.playCount>10000) "${(model.playCount/10000)}万" else model.playCount.toString()
            holder.playlistName.text = model.name
            holder.playlistListenCount.text = "$playlistenCount  "
            Glide.with(mContext!!).load(model.coverImgUrl).into(holder.playlistImage)
            with(holder.playlistCardView){
                setOnClickListener(object :View.OnClickListener{
                    override fun onClick(v: View?) {
                        didSelectedAtPos(position)
                    }
                })
            }

        }
        val item = mItems!![position]
        bind(item)
    }

    override fun getItemCount(): Int {
        return mItems!!.size
    }

    fun update(newData: List<HighPlaylistModel>) {
        mItems?.clear()
        mItems?.addAll(newData)
        notifyDataSetChanged()
    }
}