package com.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private lateinit var myObserver: DisposableObserver<Array<String>>
    private lateinit var myObservable: Observable<Array<String>>
    private var compositeDisposable:CompositeDisposable  = CompositeDisposable()
    private var greeting: Array<String> = arrayOf ( "Hello A","Hello B","Hello C" )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myObservable = Observable.just(greeting)

        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver():DisposableObserver <Array<String>> {

        myObserver = object : DisposableObserver<Array<String>> () {
            override fun onNext(t: Array<String>) {
                Log.d("RxJava", "onNext Invoked $t")
            }

            override fun onError(e: Throwable) {
                Log.d("RxJava", "onError Invoked")
            }

            override fun onComplete() {
                Log.d("RxJava", "onComplete Invoked")
            }


        }

         return myObserver
    }
}