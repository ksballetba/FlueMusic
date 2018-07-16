package com.ksballetba.fluemusic.utils.network

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.ksballetba.fluemusic.utils.model.loginModel
import com.ksballetba.fluemusic.utils.model.UserDetail
import javax.xml.transform.Result

class NetworkManager{
    companion object {
        private fun host():String{
            return "http://g213y58142.imwork.net:21457"
        }
        val loginUrl = host()+"/login/cellphone"
        val userDetailUrl = host()+"/user/detail"

        fun login(phone:String,password:String,complete:(loginModel: loginModel?,error:FuelError?)->Unit){

            FuelManager.instance.request(Method.GET, loginUrl, listOf(Pair("phone",phone),Pair("password",password)))
                    .responseObject(loginModel.Deserializer()){request, response, result ->
                        when(result){
                            is com.github.kittinunf.result.Result.Failure->{
                                complete(null,result.error)
                            }
                            is com.github.kittinunf.result.Result.Success->{
                                val(data,err) = result
                                complete(data!!,null)
                            }
                        }
                    }
//
        }


        fun getUserDetail(uid:String,complete: (userDetail: UserDetail?, error: FuelError?) -> Unit){
            FuelManager.instance.request(Method.GET, userDetailUrl, listOf(Pair("uid",uid)))
                    .responseObject(UserDetail.Deserializer()){request, response, result ->
                        when(result){
                            is com.github.kittinunf.result.Result.Failure->{
                                complete(null,result.error)
                            }
                            is com.github.kittinunf.result.Result.Success->{
                                val(detail,err) = result
                                complete(detail!!,null)
                            }
                        }
                    }
        }

    }

}