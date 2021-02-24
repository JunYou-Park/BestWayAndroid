package com.yawtseb.bestway.listener

import com.yawtseb.bestway.model.TokenVo

interface SignInListener {
    fun onSignUp()
    fun onLogin(tokenVo: TokenVo)
    fun onDisConnection(tokenVo: TokenVo)
}