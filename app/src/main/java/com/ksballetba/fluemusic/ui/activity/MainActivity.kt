package com.ksballetba.fluemusic.ui.activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.ksballetba.fluemusic.R
import com.ksballetba.fluemusic.ui.fragment.MusicFragment
import com.ksballetba.fluemusic.utils.network.NetworkManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main.*
import kotlinx.android.synthetic.main.nav_head.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.toast
import android.support.v4.app.FragmentStatePagerAdapter
import com.ksballetba.fluemusic.ui.fragment.ExploreFragment


class MainActivity : AppCompatActivity() {
    val fragmentList = ArrayList<Fragment>()
    lateinit var musicFragment:MusicFragment
    lateinit var exploreFragment:ExploreFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        setSupportActionBar(main_toolbar)
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        musicFragment = MusicFragment()
        exploreFragment = ExploreFragment()
        fragmentList.add(musicFragment)
        fragmentList.add(exploreFragment)
        main_viewpager.adapter = KotlinPagerAdapter(fragmentList,supportFragmentManager)
        main_viewpager.offscreenPageLimit = 3
        main_tablayout.setupWithViewPager(main_viewpager)
        main_tablayout.getTabAt(0)?.text = "我的"
        main_tablayout.getTabAt(1)?.text = "推荐"
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
                    editor.clear()
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

class KotlinPagerAdapter(var mList : List<Fragment>, fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): android.support.v4.app.Fragment {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }

}
