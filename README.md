# TransactionBoard
Here you have an android app written on kotlin

# How To use it?
You can use Android Studio or Intellij to work with this repository.
Use an emulator or android device with android version   23 <= API <= 29

Here  you can download transferences from remote API, and persist it on Room Data Base.
To manage offline performance, the local data persisted is returned if it exist, and the app .
shows a message to indicate if you have any internet connection failure.
Note: Wifi connection require couple seconds more because of the connection.

When the user touch any transaction, the user will see the transaction detail, and the reviewed transaction is marked with a yellow mark (left side of)  
Note the first 20 transactions that are new are marked with a yellow view, that goes to gray when the user open the transaction detail.

# Demo
![alt text](https://github.com/aliceresponde/TransactionBoard/blob/master/info/demo.gif)

# Architecture
![alt text](https://github.com/aliceresponde/TransactionBoard/blob/master/info/android_clean_repository_arch.png)

There the main layers:
data: in this module has all details Service and Database
repository : has sync server
domain: each use case performed by the user
presentation: This module has all android framework using MVVM

# Used Libraries
* [Android X Preference](https://developer.android.com/jetpack/androidx/releases/preference)
* [Data Binding](https://codelabs.developers.google.com/codelabs/android-databinding/index.html?index=..%2F..index#5)
* [Navigation Component](https://codelabs.developers.google.com/codelabs/android-navigation/index.html?index=..%2F..index#0)
* [Room](https://developer.android.com/jetpack/androidx/releases/room)
* [Retrofit2](https://square.github.io/retrofit/)
* [Coroutines](https://developer.android.com/kotlin/coroutines)

# Best Practices And References
[Dependency Inversion Principle (DIP)](https://martinfowler.com/articles/dipInTheWild.html)(without frameworks)
[Clean Code)](https://www.amazon.com/-/es/Robert-C-Martin/dp/0132350882)
