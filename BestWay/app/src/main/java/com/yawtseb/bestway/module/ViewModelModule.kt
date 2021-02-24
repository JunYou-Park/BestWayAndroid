package com.airetefacruo.myapplication.module

import com.yawtseb.bestway.viewmodel.DietViewModel
import com.yawtseb.bestway.viewmodel.AccountViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        DietViewModel(get(), get())
    }

    viewModel {
        AccountViewModel(get(), get())
    }

}