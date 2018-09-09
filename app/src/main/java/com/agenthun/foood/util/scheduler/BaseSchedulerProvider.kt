package com.agenthun.foood.util.scheduler

import io.reactivex.Scheduler

/**
 * @project Beauty
 * @authors agenthun
 * @date 2017/3/17 21:12.
 */

interface BaseSchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}
