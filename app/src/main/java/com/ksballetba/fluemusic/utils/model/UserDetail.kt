package com.ksballetba.fluemusic.utils.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class UserDetail(
        val profile: Profile,
        val level: Int, // 8
        val listenSongs: Int, // 7685
        val userPoint: UserPoint,
        val mobileSign: Boolean, // false
        val pcSign: Boolean, // false
        val peopleCanSeeMyPlayRecord: Boolean, // true
        val bindings: List<Binding>,
        val adValid: Boolean, // true
        val code: Int, // 200
        val createTime: Int, // 0
        val createDays: Int // 17729
) {
    data class Binding(
            val refreshTime: Int, // 1513517232
            val expiresIn: Int, // 7200
            val url: String,
            val userId: Int, // 34157213
            val tokenJsonStr: String, // {"access_token":"4_ObsFDnK0IsdbyskI80RCmGWEIK0GL9os5fw9QTwU15CKvcNG32cI44QIOP2n_DbEA5EGxcKtUrko0mp7ox30iC5MujduBGnKN_Hnw6oubuw","refresh_token":"4_0h9vg_1wXAD5sgt_hWX_5dp47sZ6GqQ5x7L8SDtPcC47n5WnHgwCn-poyWkZBPJX_s0rHhe_iTH8riyB7KbanwnBZnhzAImEJuhbuIRRuL0","unionid":"oZoefuAIJc9GZ2Q2aMzjSZiZ32PY","openid":"okvmMjlYgyca3IFo2is89P1qfP5c","scope":"snsapi_userinfo","name":"Fried Chicken and Beer","nickname":"Fried Chicken and Beer","expires_in":7200}
            val expired: Boolean, // true
            val id: Long, // 6506509915
            val type: Int // 10
    )

    data class Profile(
            val avatarImgIdStr: String, // 2885118512288612
            val backgroundImgIdStr: String, // 3412884104660860
            val djStatus: Int, // 0
            val followed: Boolean, // false
            val detailDescription: String,
            val description: String,
            val mutual: Boolean, // false
            val remarkName: Any?, // null
            val expertTags: Any?, // null
            val experts: Any?,
            val avatarImgId: Long, // 2885118512288612
            val backgroundImgId: Long, // 3412884104660860
            val userType: Int, // 0
            val authStatus: Int, // 0
            val backgroundUrl: String, // http://p1.music.126.net/rcrqbp9AIiIj1UqTeNTS5A==/3412884104660860.jpg
            val userId: Int, // 34157213
            val accountStatus: Int, // 0
            val nickname: String, // ???????
            val vipType: Int, // 0
            val gender: Int, // 1
            val defaultAvatar: Boolean, // false
            val avatarUrl: String, // http://p4.music.126.net/5Zp7i0UC9Dwn1vh4Eu70bw==/2885118512288612.jpg
            val birthday: Long, // 900745200000
            val city: Int, // 530600
            val province: Int, // 530000
            val signature: String, // ?????????????
            val authority: Int, // 0
            val artistIdentity: List<Any>,
            val followeds: Int, // 14
            val follows: Int, // 16
            val blacklist: Boolean, // false
            val eventCount: Int, // 3
            val playlistCount: Int, // 10
            val playlistBeSubscribedCount: Int // 0
    )

    data class UserPoint(
            val userId: Int, // 34157213
            val balance: Int, // 330
            val updateTime: Long, // 1526456707248
            val version: Int, // 107
            val status: Int, // 1
            val blockBalance: Int // 0
    )
    class Deserializer : ResponseDeserializable<UserDetail> {
        override fun deserialize(content: String): UserDetail? {
            return Gson().fromJson(content,UserDetail::class.java)
        }
    }
}