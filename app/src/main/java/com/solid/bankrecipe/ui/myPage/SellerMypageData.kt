package com.solid.bankrecipe.ui.myPage

data class SellerMypageData(
    var businessName : String = "미기입",//default 미기입
    var OpenTime : String = "영업시간이 없습니다.", //영업시간
    var Holiday : String ="휴무일이 없습니다.",
    var tel : String = " ",
    var homepage : String = " ",
    var Location : String = " "
)
