package com.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity2 : AppCompatActivity() {

    private val TAG = "MyApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val myObservable = Observable.range(1, 20)

        myObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            //.filter { integer -> integer % 3 == 0 }
            .filter(object : Predicate<Int> {
                override fun test(p0: Int): Boolean {
                    return p0 % 3 == 0
                }
            } )
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(integer: Int) {
                    Log.i(TAG, "onNext Invoked $integer")
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }
}