package com.ksballetba.fluemusic.ui.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.ksballetba.fluemusic.R
import com.ksballetba.fluemusic.utils.network.NetworkManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main.*
import kotlinx.android.synthetic.main.nav_head.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        if(preferences.getInt("isLogin",0)!=1){
            startActivity(intentFor<LoginActivity>().newTask())
        }
        init()
    }

    private fun init(){
        setSupportActionBar(main_toolbar)
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        getUserDetial(preferences.getString("uid",""))
        setItemListener()
    }


    private fun getUserDetial(uid:String){
        NetworkManager.getUserDetail(uid){data,err->
            if(err==null){
               Glide.with(this).load(data?.profile?.backgroundUrl).into(nav_headbg)
               Glide.with(this).load(data?.profile?.avatarUrl).into(nav_headavatar)
               nav_headnickname.text = data?.profile?.nickname
            } else{
                toast("失败"+data.toString())
            }
        }
    }

    private fun setItemListener() {
        nav_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_logout -> {
                    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                    val editor = preferences.edit()
                    editor.putInt("isLogin",0)
                    editor.commit()
                    startActivity(intentFor<LoginActivity>().clearTask().newTask())

                }

            }
            draw_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->{
                draw_layout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
