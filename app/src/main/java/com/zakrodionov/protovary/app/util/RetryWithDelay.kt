package com.zakrodionov.protovary.app.util

import io.reactivex.Flowable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

class RetryWithDelay(
    private val maxRetries: Int,
    private val retryDelayMillis: Int,
    private val retryIf: Function<Throwable, Boolean>,
    private val unit: TimeUnit = TimeUnit.MILLISECONDS
) : Function<Flowable<out Throwable>, Flowable<*>> {
    private var retryCount: Int = 0

    override fun apply(attempts: Flowable<out Throwable>): Flowable<*> {
        return attempts
            .doOnNext { println("RetryWithDelay $it") }
            .flatMap { throwable ->
                if (retryIf.apply(throwable) && (maxRetries < 0 || ++retryCount < maxRetries)) {
                    // When this Flowable calls onNext, the original Flowable will be retried (i.e. re-subscribed).
                    Flowable.timer(retryDelayMillis.toLong(), unit)
                } else Flowable.error<Any>(throwable) // Max retries hit. Just pass the error along.
            }
    }
}