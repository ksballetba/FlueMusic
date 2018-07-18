package com.ksballetba.fluemusic.utils.model

import com.google.gson.annotations.SerializedName

data class PlaylistModel(
        val subscribers: List<Any>,
        val subscribed: Boolean, // false
        val creator: Creator,
        val artists: Any?, // null
        val tracks: Any?, // null
        val ordered: Boolean, // false
        val subscribedCount: Int, // 0
        val cloudTrackCount: Int, // 0
        val tags: List<Any>,
        val coverImgId: Long, // 19172184254127699
        val createTime: Long, // 1408981379190
        val updateTime: Long, // 1530336995498
        val description: Any?, // null
        val status: Int, // 0
        val commentThreadId: String, // A_PL_0_25978058
        val highQuality: Boolean, // false
        val trackCount: Int, // 622
        val userId: Int, // 34157213
        val totalDuration: Int, // 0
        val anonimous: Boolean, // false
        val privacy: Int, // 0
        val newImported: Boolean, // false
        val specialType: Int, // 5
        val trackUpdateTime: Long, // 1531883195826
        val playCount: Int, // 4849
        val adType: Int, // 0
        val trackNumberUpdateTime: Long, // 1530336995498
        val coverImgUrl: String, // http://p
        // 1.music.126.net/Q2c2oa1hp3vA43Q0tNxqTw==/19172184254127699.jpg
        val name: String, // 楣跨壒涓圭殑钁¤悇鏋跺枩娆㈢殑闊充箰
        val id: Long, // 25978058
        @SerializedName("coverImgId_str")
        val coverImgIdStr: String // 19172184254127699
) {
    data class Creator(
            val defaultAvatar: Boolean, // false
            val province: Int, // 530000
            val authStatus: Int, // 0
            val followed: Boolean, // false
            val avatarUrl: String, // http://p1.music.126.net/5Zp7i0UC9Dwn1vh4Eu70bw==/2885118512288612.jpg
            val accountStatus: Int, // 0
            val gender: Int, // 1
            val city: Int, // 530600
            val birthday: Long, // 900745200000
            val userId: Int, // 34157213
            val userType: Int, // 0
            val nickname: String, // 楣跨壒涓圭殑钁¤悇鏋�
            val signature: String, // 鎴戞拺鐫�澶уお闃筹紝鍙兂涓轰綘鎾戜紴
            val description: String,
            val detailDescription: String,
            val avatarImgId: Long, // 2885118512288612
            val backgroundImgId: Long, // 3412884104660860
            val backgroundUrl: String, // http://p1.music.126.net/rcrqbp9AIiIj1UqTeNTS5A==/3412884104660860.jpg
            val authority: Int, // 0
            val mutual: Boolean, // false
            val expertTags: Any?, // null
            val experts: Any?, // null
            val djStatus: Int, // 0
            val vipType: Int, // 0
            val remarkName: Any?, // null
            val backgroundImgIdStr: String, // 3412884104660860
            val avatarImgIdStr: String // 2885118512288612
    )
}