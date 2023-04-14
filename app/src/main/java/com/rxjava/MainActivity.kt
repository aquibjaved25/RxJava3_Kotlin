package com.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var myObservable: Observable<Student>
    private lateinit var myObserver: DisposableObserver<Student>

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myObservable = Observable.create { e ->
            for (student in getStudents()) {
                e.onNext(student)
            }

            e.onComplete()
        }

        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.map { student ->
                    student.name = (student.name.uppercase(Locale.ROOT))
                    student
            }*/
                .flatMap { student: Student ->

                    val student1 = Student(student.name, "", 1, "")
                    val student2 = Student(student.name, "", 2, "")

                    student.name = (student.name.uppercase(Locale.ROOT))
                    Observable.just<Student>(student,student1,student2)
                }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<Student> {

        myObserver = object : DisposableObserver<Student>() {
            override fun onNext(t: Student) {
                Log.d("RxJava", "onNext Invoked ${t.name}")
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

    private fun getStudents(): ArrayList<Student> {
        val studentArrayList: ArrayList<Student> = arrayListOf()
        studentArrayList.add(Student("student 1", "student1@gmail.com", 27, ""))
        studentArrayList.add(Student("student 2", "student2@gmail.com", 20, ""))
        studentArrayList.add(Student("student 3", "student3@gmail.com", 20, ""))
        studentArrayList.add(Student("student 4", "student4@gmail.com", 20, ""))
        studentArrayList.add(Student("student 5", "student5@gmail.com", 20, ""))

        return studentArrayList
    }
}