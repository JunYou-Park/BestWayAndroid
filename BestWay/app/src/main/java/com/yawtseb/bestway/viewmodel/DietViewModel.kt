package com.yawtseb.bestway.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airetefacruo.myapplication.api.RxExtensions.Companion.ioNewThread
import com.airetefacruo.myapplication.util.network.NetWorkState
import com.airetefacruo.myapplication.util.network.Resource
import com.google.gson.Gson
import com.yawtseb.bestway.R
import com.yawtseb.bestway.base.BaseViewModel
import com.yawtseb.bestway.model.DataResult
import com.yawtseb.bestway.model.MenuVo
import com.yawtseb.bestway.model.TicketVo
import com.yawtseb.bestway.repository.DietRepository
import com.yawtseb.bestway.util.ShowMsg.Companion.showLog
import io.reactivex.observers.DisposableObserver
import org.joda.time.DateTime

class DietViewModel(private val dietRepository: DietRepository, private val netWorkState: NetWorkState) : BaseViewModel(){

    private val _toDayDietLiveData = MutableLiveData<Resource<ArrayList<MenuVo>>>()
    val toDayMenuLiveData: LiveData<Resource<ArrayList<MenuVo>>> get() = _toDayDietLiveData

    val getUserTicket = MutableLiveData<Resource<TicketVo>>()

    fun getUserTicket(accessToken: String){
        getUserTicket.postValue(Resource.loading(null))
        var result = DataResult()

        if(netWorkState.isNetworkConnected()){
            addToDisposable(dietRepository.getUserTicket(accessToken).ioNewThread()
                    .subscribeWith(object : DisposableObserver<DataResult>(){
                        override fun onNext(t: DataResult) {
                            showLog(t)
                            result = t
                        }

                        override fun onError(e: Throwable) {
                            showLog(e.message.toString())
                            getUserTicket.postValue(Resource.error(e.message.toString(), null))
                        }

                        override fun onComplete() {
                            when(result.status){
                                "201" ->{
                                    val data = result.data
                                    var ticket: TicketVo? = null
                                    if(data!=null){
                                        val gson = Gson()
                                        for(obj in data){
                                            ticket = gson.fromJson(obj, TicketVo::class.java)
                                        }
                                    }
                                    getUserTicket.postValue(Resource.success(ticket))
                                }
                                else -> getUserTicket.postValue(Resource.error(result.msg.toString(), null))
                            }
                        }

                    }))
        }
        else{
            getUserTicket.postValue(Resource.errorInt(R.string.network_disconnected_and_retry, null))
        }

    }

    fun getToDayDiet(){
        _toDayDietLiveData.postValue(Resource.loading(null))

        var result = DataResult()

        if(netWorkState.isNetworkConnected()){
            addToDisposable(dietRepository.getToDayDiet(DateTime().toString("yyyyMMddHHmmss")).ioNewThread()
                .subscribeWith(object : DisposableObserver<DataResult>(){
                    override fun onNext(t: DataResult) {
                        showLog(t, "getToDayDiet")
                        result = t
                    }

                    override fun onError(e: Throwable) {
                        _toDayDietLiveData.postValue(Resource.error(e.message.toString(), null))
                    }

                    override fun onComplete() {
                        when(result.status){
                            "201" -> {
                                val data = result.data
                                val diet = arrayListOf<MenuVo>()
                                if(data!=null){
                                    val gson = Gson()
                                    for(obj in data){
                                        val dietVo = gson.fromJson(obj, MenuVo::class.java)
                                        diet.add(dietVo)
                                    }
                                }
                                _toDayDietLiveData.postValue(Resource.success(diet))
                            }
                            else ->{
                                _toDayDietLiveData.postValue(Resource.error(result.msg.toString(), null))
                            }
                        }
                    }

                }))
        }
        else{
            _toDayDietLiveData.postValue(Resource.errorInt(R.string.network_disconnected_and_retry, null))
        }
    }
}