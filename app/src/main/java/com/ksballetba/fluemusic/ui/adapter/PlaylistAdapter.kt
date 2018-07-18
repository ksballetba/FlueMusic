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
import com.ksballetba.fluemusic.utils.model.PlaylistModel
import kotlinx.android.synthetic.main.playlist_item.view.*

class PlaylistAdapter(val mItems:ArrayList<PlaylistModel>?,internal val didSelectedAtPos:(idx:Int)->Unit):RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {
    internal var mContext:Context? = null
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var cardView = view as CardView
        var playlistImage = view.findViewById<ImageView>(R.id.playlist_image)
        var playlistName = view.findViewById<TextView>(R.id.playlist_name)
        var playlistCount = view.findViewById<TextView>(R.id.playlist_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext==null) {
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.playlist_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun bind(model: PlaylistModel){
            holder.playlistName.text = model.name
            holder.playlistCount.text = "${model.trackCount}首"
            Glide.with(mContext!!).load(model.coverImgUrl).into(holder.playlistImage)
            with(holder.cardView){
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

    fun update(newData: List<PlaylistModel>) {
        mItems?.clear()
        mItems?.addAll(newData)
        notifyDataSetChanged()
    }


}