package com.ksballetba.fluemusic.utils.network

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.ksballetba.fluemusic.utils.model.PlaylistModel
import com.ksballetba.fluemusic.utils.model.loginModel
import com.ksballetba.fluemusic.utils.model.UserDetail
import javax.xml.transform.Result
import com.google.gson.Gson
import org.json.JSONObject
import org.json.JSONArray


class NetworkManager {
    companion object {
        private fun host(): String {
            return "http://192.168.1.106:3000"
        }

        val loginUrl = host() + "/login/cellphone"
        val userDetailUrl = host() + "/user/detail"
        val playlistUrl = host() + "/user/playlist"

        fun login(phone: String, password: String, complete: (loginModel: loginModel?, error: FuelError?) -> Unit) {

            FuelManager.instance.request(Method.GET, loginUrl, listOf(Pair("phone", phone), Pair("password", password)))
                    .responseObject(loginModel.Deserializer()) { request, response, result ->
                        when (result) {
                            is com.github.kittinunf.result.Result.Failure -> {
                                complete(null, result.error)
                            }
                            is com.github.kittinunf.result.Result.Success -> {
                                val (data, err) = result
                                complete(data!!, null)
                            }
                        }
                    }
//
        }


        fun getUserDetail(uid: String, complete: (userDetail: UserDetail?, error: FuelError?) -> Unit) {
            FuelManager.instance.request(Method.GET, userDetailUrl, listOf(Pair("uid", uid)))
                    .responseObject(UserDetail.Deserializer()) { request, response, result ->
                        when (result) {
                            is com.github.kittinunf.result.Result.Failure -> {
                                complete(null, result.error)
                            }
                            is com.github.kittinunf.result.Result.Success -> {
                                val (detail, err) = result
                                complete(detail!!, null)
                            }
                        }
                    }
        }

        fun getUserPlaylistAll(uid: String, complete: (res: String?, error: FuelError?) -> Unit) {
            FuelManager.instance.request(Method.GET, playlistUrl, listOf(Pair("uid", uid)))
                    .responseString { request, response, result ->
                        when (result) {
                            is com.github.kittinunf.result.Result.Failure -> {
                                complete(null, result.error)
                            }
                            is com.github.kittinunf.result.Result.Success -> {
                                val (responseText, err) = result
                                complete(responseText, null)
                            }
                        }
                    }
        }

        fun getUserPlaylist(response: String?): ArrayList<PlaylistModel> {
            var resultPlaylist = ArrayList<PlaylistModel>()
            var all = JSONObject(response)
            var playlistContent = all.getJSONArray("playlist")
            val playlist: List<PlaylistModel> = Gson().fromJson(playlistContent.toString(),Array<PlaylistModel>::class.java).toList()
            for(i in 0..10){
                resultPlaylist.add(playlist[i])
            }
            return resultPlaylist
        }
    }


}