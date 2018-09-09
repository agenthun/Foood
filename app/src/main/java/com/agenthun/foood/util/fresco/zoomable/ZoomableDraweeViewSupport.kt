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

import android.content.Context
import android.util.AttributeSet

import com.facebook.drawee.generic.GenericDraweeHierarchy

/**
 * DraweeView that has zoomable capabilities.
 *
 *
 * Once the image loads, pinch-to-zoom and translation gestures are enabled.
 */
class ZoomableDraweeViewSupport : ZoomableDraweeView {

    protected override val logTag: Class<*>
        get() = TAG

    constructor(context: Context, hierarchy: GenericDraweeHierarchy) : super(context, hierarchy) {}

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun createZoomableController(): ZoomableController {
        return AnimatedZoomableController.newInstance()
    }

    companion object {

        private val TAG = ZoomableDraweeViewSupport::class.java
    }
}
