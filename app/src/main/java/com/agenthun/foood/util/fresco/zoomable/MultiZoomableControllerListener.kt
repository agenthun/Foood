/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.agenthun.foood.util.fresco.zoomable

import android.graphics.Matrix
import java.util.ArrayList

/**
 * An implementation of [ZoomableController.Listener] that allows multiple child listeners to
 * be added and notified about [ZoomableController] events.
 */
class MultiZoomableControllerListener : ZoomableController.Listener {

    private val mListeners = ArrayList<ZoomableController.Listener>()

    @Synchronized
    override fun onTransformBegin(transform: Matrix) {
        for (listener in mListeners) {
            listener.onTransformBegin(transform)
        }
    }

    @Synchronized
    override fun onTransformChanged(transform: Matrix) {
        for (listener in mListeners) {
            listener.onTransformChanged(transform)
        }
    }

    @Synchronized
    override fun onTransformEnd(transform: Matrix) {
        for (listener in mListeners) {
            listener.onTransformEnd(transform)
        }
    }

    @Synchronized
    fun addListener(listener: ZoomableController.Listener) {
        mListeners.add(listener)
    }

    @Synchronized
    fun removeListener(listener: ZoomableController.Listener) {
        mListeners.remove(listener)
    }
}
