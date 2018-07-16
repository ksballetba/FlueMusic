package com.ksballetba.fluemusic.utils.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class loginModel(
    val loginType: Int, // 1
    val code: Int, // 200
    val account: Account,
    val profile: Profile,
    val bindings: List<Binding>
) {
    data class Binding(
        val url: String,
        val refreshTime: Int, // 1513517232
        val expired: Boolean, // true
        val tokenJsonStr: String, // {"access_token":"4_ObsFDnK0IsdbyskI80RCmGWEIK0GL9os5fw9QTwU15CKvcNG32cI44QIOP2n_DbEA5EGxcKtUrko0mp7ox30iC5MujduBGnKN_Hnw6oubuw","refresh_token":"4_0h9vg_1wXAD5sgt_hWX_5dp47sZ6GqQ5x7L8SDtPcC47n5WnHgwCn-poyWkZBPJX_s0rHhe_iTH8riyB7KbanwnBZnhzAImEJuhbuIRRuL0","unionid":"oZoefuAIJc9GZ2Q2aMzjSZiZ32PY","openid":"okvmMjlYgyca3IFo2is89P1qfP5c","scope":"snsapi_userinfo","name":"Fried Chicken and Beer","nickname":"Fried Chicken and Beer","expires_in":7200}
        val expiresIn: Int, // 7200
        val userId: Int, // 34157213
        val id: Long, // 6506509915
        val type: Int // 10
    )

    data class Account(
        val id: Int, // 34157213
        val userName: String, // 1_13150685281
        val type: Int, // 1
        val status: Int, // 0
        val whitelistAuthority: Int, // 0
        val createTime: Int, // 0
        val salt: String, // [B@7d7c3e1c
        val tokenVersion: Int, // 0
        val ban: Int, // 0
        val baoyueVersion: Int, // 1
        val donateVersion: Int, // 0
        val vipType: Int, // 0
        val viptypeVersion: Long, // 1492358639773
        val anonimousUser: Boolean // false
    )

    data class Profile(
        val backgroundUrl: String, // http://p1.music.126.net/rcrqbp9AIiIj1UqTeNTS5A==/3412884104660860.jpg
        val avatarImgIdStr: String, // 2885118512288612
        val backgroundImgIdStr: String, // 3412884104660860
        val backgroundImgId: Long, // 3412884104660860
        val userType: Int, // 0
        val experts: Any?,
        val expertTags: Any?, // null
        val authStatus: Int, // 0
        val defaultAvatar: Boolean, // false
        val avatarUrl: String, // http://p4.music.126.net/5Zp7i0UC9Dwn1vh4Eu70bw==/2885118512288612.jpg
        val city: Int, // 530600
        val province: Int, // 530000
        val birthday: Long, // 900745200000
        val gender: Int, // 1
        val accountStatus: Int, // 0
        val vipType: Int, // 0
        val avatarImgId: Long, // 2885118512288612
        val userId: Int, // 34157213
        val nickname: String, // 鹿特丹的葡萄架
        val remarkName: Any?, // null
        val mutual: Boolean, // false
        val detailDescription: String,
        val djStatus: Int, // 0
        val followed: Boolean, // false
        val description: String,
        val signature: String, // 我撑着大太阳，只想为你撑伞
        val authority: Int // 0
    )
    class Deserializer : ResponseDeserializable<loginModel>{
        override fun deserialize(content: String): loginModel? {
            return Gson().fromJson(content,loginModel::class.java)
        }
    }
}