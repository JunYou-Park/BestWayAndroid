package com.airetefacruo.myapplication.module

import com.yawtseb.bestway.repository.DietRepository
import com.yawtseb.bestway.repository.AccountRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        DietRepository(get())
    }

    single {
        AccountRepository(get())
    }

}