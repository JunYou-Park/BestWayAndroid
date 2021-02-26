package com.yawtseb.bestway.util

object ConstData {

    // QRFrag로 이동할 때, 단품 주문 키 값 true면 단품
    const val ORDER_KEY = "single"

    // Parcelable로 data class를 주고 받을 때 사용하는 키
    const val MODEL_KEY = "model.key"

    const val SIGNUP_ANIM_TIME = 300L
    const val DRAWABLE_LEFT = 0
    const val DRAWABLE_TOP = 1
    const val DRAWABLE_RIGHT = 2
    const val DRAWABLE_BOTTOM = 3
    const val TODAY_MENU_CURRENT_POSITION_KEY = "com.airetefacruo.myapplication.key.current_position"

    const val TODAY_MENU_KEY = "com.airetefacruo.myapplication.key.today_menu"

    // aes key는 32바이트여야 한다.
    const val CRYPTO_KEY = "comairetefacruomyapplicationbest";
    val CRYPTO_BYTES = byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)

    const val NOTICE_KEY = "notification"
    const val NOTICE_ALL = "all"
    const val NOTICE_DEPOSIT = "deposit"
    const val NOTICE_NOTICE = "notice"
    const val NOTICE_EVENT = "event"

    const val NOTICE_CHECK = "check"

    const val USER_API = "/api/user"
    const val FCM_API = "/api/fcm"
    const val EVENT_API = "/api/event"
    const val MENU_API = "/api/menu"

    const val IMAGE_URL = "https://elasticbeanstalk-ap-northeast-2-042755660743.s3.ap-northeast-2.amazonaws.com"
    const val AUTH_URL = "functionshop.xyz/api/user/authentication.do?user="

    const val GMAIL_ID = "junyeong271@gmail.com"
    const val GMAIL_PW = "!qkrwnsdud12"

    const val nicknamePattern = "[가-힣a-zA-Z]{2}[가-힣a-zA-Z0-9]{0,14}+$"
    const val foreignNicknamePattern = "[a-zA-Z]{2}[a-zA-Z0-9]{0,14}+$"

    const val namePattern = "^[가-힣a-zA-Z]{2,11}+\$"
    const val foreignNamePattern = "^[a-zA-Z0-9\\s]{2,20}+\$"

    const val phonePattern = "[0-9]{10,11}"
    const val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\$@\$!\$^`~%*#?&])[A-Za-z\\d\$@\$!\$^`~%*#?&]{8,}\$"
    const val companyNamePattern = "^[가-힣a-zA-Z0-9()]{1,23}+\$"
    const val numberPattern="[0-9]" // 숫자가 아닌 경우 replaceAll 시켜주기 위해 ^로 선언해 놓음 (수정 X)
    const val containKorean="/*[ㄱ-ㅎㅏ-ㅣ|가-힣]+.*"

    const val emailPattern = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+\$"
}
