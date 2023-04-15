package com.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity2 : AppCompatActivity() {

    private val TAG = "MyApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val myObservable = Observable.range(1, 18)

        myObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(4)
            .subscribe(object : Observer<List<Int>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(integers: List<Int>) {
                    Log.i(TAG, "onNext")
                    for (i in integers) {
                        Log.i(TAG, "int value is $i")
                    }
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }
}