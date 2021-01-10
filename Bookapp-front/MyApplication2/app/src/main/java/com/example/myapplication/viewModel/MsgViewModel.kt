package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Msg

class MsgViewModel:ViewModel() {
    var _msglist=MutableLiveData<MutableList<Msg>>().apply { value=ArrayList()
        val m=Msg("","rose","hellojack","14:00")
        val m2=Msg("","jack","","")
        val m1=Msg("","zhc","","")
        (value as ArrayList<Msg>).add(m)
        (value as ArrayList<Msg>).add(m1)
        (value as ArrayList<Msg>).add(m2)}


    val msglist:LiveData<MutableList<Msg>> = _msglist




}