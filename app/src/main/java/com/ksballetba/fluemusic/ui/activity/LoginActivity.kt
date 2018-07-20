package com.ksballetba.fluemusic.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.ksballetba.fluemusic.R
import com.ksballetba.fluemusic.utils.network.NetworkManager
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        if(preferences.getInt("isLogin",0)==1){
            startActivity(intentFor<MainActivity>().clearTask().newTask())
        }
        init()
    }

    private fun init(){
        setSupportActionBar(login_toolbar)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        phone_et.setText(preferences.getString("phone",""))
        password_et.setText(preferences.getString("password",""))
        login_button.setOnClickListener {
            login(phone_et.text.toString(),password_et.text.toString())
        }
    }

    private fun login(phone: String,password: String){
        NetworkManager.login(
                phone,
                password
        ){data,err->
            if(err==null){
                when(data?.code){
                    200->{
                        startActivity(intentFor<MainActivity>().clearTask().newTask())
                    }
                    else->{
                        toast("登录名或密码错误")
                    }
                }
            } else{
                toast("失败"+data.toString())
            }
            saveInfo(phone_et.text.toString(),password_et.text.toString(),data?.account?.id.toString())
        }
    }

    private fun saveInfo(phone:String,password:String,uid:String){
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("phone",phone)
        editor.putString("password",password)
        editor.putString("uid",uid)
        editor.putInt("isLogin",1)
        editor.commit()
    }

}
