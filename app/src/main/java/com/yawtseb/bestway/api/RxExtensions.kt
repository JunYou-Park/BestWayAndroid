package com.airetefacruo.myapplication.api

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxExtensions {
    companion object{
        fun <T> Single<T>.with(): Single<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        fun <T> Observable<T>.with(): Observable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        fun <T> Observable<T>.computationNewThread(): Observable<T> = subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())

        fun <T> Observable<T>.ioNewThread(): Observable<T> = subscribeOn(Schedulers.io()).observeOn(Schedulers.io())

        fun <T> Flowable<T>.with(): Flowable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}