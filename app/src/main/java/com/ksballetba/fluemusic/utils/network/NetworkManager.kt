package com.ksballetba.fluemusic.utils.network

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import javax.xml.transform.Result
import com.google.gson.Gson
import com.ksballetba.fluemusic.utils.model.*
import org.json.JSONObject
import org.json.JSONArray
import java.util.prefs.PreferencesFactory


class NetworkManager {
    companion object {
        private fun host(): String {
            return "http://192.168.1.106:3000"
        }

        val loginUrl = host() + "/login/cellphone"
        val userDetailUrl = host() + "/user/detail"
        val playlistUrl = host() + "/user/playlist"
        val bannerUrl = host()+"/banner"
        val highPlaylistUrl = host()+"/top/playlist/highquality?limit=6"
        val hotPlaylistUrl = host()+"/top/playlist?limit=6&order=hot"
        val newPlaylistUrl = host()+"/top/playlist?limit=6&order=new"

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

        fun getPlaylistAll(uid: String, complete: (res: String?, error: FuelError?) -> Unit) {
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

        fun getUserPlaylist(response: String?,uid: String?): ArrayList<PlaylistModel> {
            var resultPlaylist = ArrayList<PlaylistModel>()
            var all = JSONObject(response)
            var playlistContent = all.getJSONArray("playlist")
            val playlist: List<PlaylistModel> = Gson().fromJson(playlistContent.toString(),Array<PlaylistModel>::class.java).toList()
            for(i in 0 until playlist.size-1){
                if(playlist[i].creator.userId==uid?.toInt()){
                    resultPlaylist.add(playlist[i])
                }
            }
            return resultPlaylist
        }

        fun getFavPlaylist(response: String?,uid: String?): ArrayList<PlaylistModel> {
            var resultPlaylist = ArrayList<PlaylistModel>()
            var all = JSONObject(response)
            var playlistContent = all.getJSONArray("playlist")
            val playlist: List<PlaylistModel> = Gson().fromJson(playlistContent.toString(),Array<PlaylistModel>::class.java).toList()
            for(i in 0 until playlist.size-1){
                if(playlist[i].creator.userId!=uid?.toInt()){
                    resultPlaylist.add(playlist[i])
                }

            }
            return resultPlaylist
        }

        fun getBannerAll(complete: (res: String?, error: FuelError?) -> Unit) {
            FuelManager.instance.request(Method.GET, bannerUrl)
                    .responseString { request, response, result ->
                        when (result) {
                            is com.github.kittinunf.result.Result.Failure -> {
                                complete(null, result.error)
                            }
                            is com.github.kittinunf.result.Result.Success -> {
                                val (res, err) = result
                                complete(res!!, null)
                            }
                        }
                    }
        }

        fun getBanner(response: String?): ArrayList<BannerModel> {
            var resultBanners = ArrayList<BannerModel>()
            var all = JSONObject(response)
            var bannersContent = all.getJSONArray("banners")
            val banners: List<BannerModel> = Gson().fromJson(bannersContent.toString(),Array<BannerModel>::class.java).toList()
            for(i in 0 until banners.size){
                resultBanners.add(banners[i])
            }
            return resultBanners
        }

        fun getHighPlaylistAll(url:String,complete: (res: String?, error: FuelError?) -> Unit) {
            FuelManager.instance.request(Method.GET, url)
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

        fun getRecPlaylist(response: String?): ArrayList<HighPlaylistModel> {
            var resultPlaylist = ArrayList<HighPlaylistModel>()
            var all = JSONObject(response)
            var playlistContent = all.getJSONArray("playlists")
            val playlist: List<HighPlaylistModel> = Gson().fromJson(playlistContent.toString(),Array<HighPlaylistModel>::class.java).toList()
            for(i in 0 until playlist.size){
                    resultPlaylist.add(playlist[i])

            }
            return resultPlaylist
        }


    }

}