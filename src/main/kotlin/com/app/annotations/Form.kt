package com.app.annotations



@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Form(val name: String) // a form expected to be exposed.


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Label(val name: String) // an english filed trying to map to chinese label