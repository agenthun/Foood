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
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.Animatable
import android.support.v4.view.ScrollingView
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import com.facebook.common.internal.Preconditions
import com.facebook.drawee.controller.AbstractDraweeController
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.GenericDraweeHierarchyInflater
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.DraweeView

/**
 * DraweeView that has zoomable capabilities.
 *
 *
 * Once the image loads, pinch-to-zoom and translation gestures are enabled.
 */
open class ZoomableDraweeView : DraweeView<GenericDraweeHierarchy>, ScrollingView {

    private val mImageBounds = RectF()
    private val mViewBounds = RectF()

    private var mHugeImageController: DraweeController? = null
    private var mZoomableController: ZoomableController? = null
    private var mTapGestureDetector: GestureDetector? = null
    private var mAllowTouchInterceptionWhileZoomed = true

    private val mControllerListener = object : BaseControllerListener<Any>() {
        override fun onFinalImageSet(
                id: String,
                imageInfo: Any?,
                animatable: Animatable?) {
            this@ZoomableDraweeView.onFinalImageSet()
        }

        override fun onRelease(id: String) {
            this@ZoomableDraweeView.onRelease()
        }
    }

    private val mZoomableListener = object : ZoomableController.Listener {
        override fun onTransformBegin(transform: Matrix) {}

        override fun onTransformChanged(transform: Matrix) {
            this@ZoomableDraweeView.onTransformChanged(transform)
        }

        override fun onTransformEnd(transform: Matrix) {}
    }

    private val mTapListenerWrapper = GestureListenerWrapper()

    /**
     * Gets the zoomable controller.
     *
     *
     *  Zoomable controller can be used to zoom to point, or to map point from view to image
     * coordinates for instance.
     */
    /**
     * Sets a custom zoomable controller, instead of using the default one.
     */
    var zoomableController: ZoomableController?
        get() = mZoomableController
        set(zoomableController) {
            Preconditions.checkNotNull<ZoomableController>(zoomableController)
            mZoomableController!!.setListener(null)
            mZoomableController = zoomableController
            mZoomableController!!.setListener(mZoomableListener)
        }

    protected open val logTag: Class<*>
        get() = TAG

    constructor(context: Context, hierarchy: GenericDraweeHierarchy) : super(context) {
        setHierarchy(hierarchy)
        init()
    }

    constructor(context: Context) : super(context) {
        inflateHierarchy(context, null)
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflateHierarchy(context, attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        inflateHierarchy(context, attrs)
        init()
    }

    protected fun inflateHierarchy(context: Context, attrs: AttributeSet?) {
        val resources = context.resources
        val builder = GenericDraweeHierarchyBuilder(resources)
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
        GenericDraweeHierarchyInflater.updateBuilder(builder, context, attrs)
        aspectRatio = builder.desiredAspectRatio
        hierarchy = builder.build()
    }

    private fun init() {
        mZoomableController = createZoomableController()
        mZoomableController!!.setListener(mZoomableListener)
        mTapGestureDetector = GestureDetector(context, mTapListenerWrapper)
    }

    /**
     * Gets the original image bounds, in view-absolute coordinates.
     *
     *
     *  The original image bounds are those reported by the hierarchy. The hierarchy itself may
     * apply scaling on its own (e.g. due to scale type) so the reported bounds are not necessarily
     * the same as the actual bitmap dimensions. In other words, the original image bounds correspond
     * to the image bounds within this view when no zoomable transformation is applied, but including
     * the potential scaling of the hierarchy.
     * Having the actual bitmap dimensions abstracted away from this view greatly simplifies
     * implementation because the actual bitmap may change (e.g. when a high-res image arrives and
     * replaces the previously set low-res image). With proper hierarchy scaling (e.g. FIT_CENTER),
     * this underlying change will not affect this view nor the zoomable transformation in any way.
     */
    protected fun getImageBounds(outBounds: RectF) {
        hierarchy.getActualImageBounds(outBounds)
    }

    /**
     * Gets the bounds used to limit the translation, in view-absolute coordinates.
     *
     *
     *  These bounds are passed to the zoomable controller in order to limit the translation. The
     * image is attempted to be centered within the limit bounds if the transformed image is smaller.
     * There will be no empty spaces within the limit bounds if the transformed image is bigger.
     * This applies to each dimension (horizontal and vertical) independently.
     *
     *  Unless overridden by a subclass, these bounds are same as the view bounds.
     */
    protected fun getLimitBounds(outBounds: RectF) {
        outBounds.set(0f, 0f, width.toFloat(), height.toFloat())
    }

    /**
     * Check whether the parent view can intercept touch events while zoomed.
     * This can be used, for example, to swipe between images in a view pager while zoomed.
     *
     * @return true if touch events can be intercepted
     */
    fun allowsTouchInterceptionWhileZoomed(): Boolean {
        return mAllowTouchInterceptionWhileZoomed
    }

    /**
     * If this is set to true, parent views can intercept touch events while the view is zoomed.
     * For example, this can be used to swipe between images in a view pager while zoomed.
     *
     * @param allowTouchInterceptionWhileZoomed true if the parent needs to intercept touches
     */
    fun setAllowTouchInterceptionWhileZoomed(
            allowTouchInterceptionWhileZoomed: Boolean) {
        mAllowTouchInterceptionWhileZoomed = allowTouchInterceptionWhileZoomed
    }

    /**
     * Sets the tap listener.
     */
    fun setTapListener(tapListener: GestureDetector.SimpleOnGestureListener) {
        mTapListenerWrapper.setListener(tapListener)
    }

    /**
     * Sets whether long-press tap detection is enabled.
     * Unfortunately, long-press conflicts with onDoubleTapEvent.
     */
    fun setIsLongpressEnabled(enabled: Boolean) {
        mTapGestureDetector!!.setIsLongpressEnabled(enabled)
    }

    /**
     * Sets the image controller.
     */
    override fun setController(controller: DraweeController?) {
        setControllers(controller, null)
    }

    /**
     * Sets the controllers for the normal and huge image.
     *
     *
     *  The huge image controller is used after the image gets scaled above a certain threshold.
     *
     *
     *  IMPORTANT: in order to avoid a flicker when switching to the huge image, the huge image
     * controller should have the normal-image-uri set as its low-res-uri.
     *
     * @param controller controller to be initially used
     * @param hugeImageController controller to be used after the client starts zooming-in
     */
    fun setControllers(
            controller: DraweeController?,
            hugeImageController: DraweeController?) {
        setControllersInternal(null, null)
        mZoomableController!!.isEnabled = false
        setControllersInternal(controller, hugeImageController)
    }

    private fun setControllersInternal(
            controller: DraweeController?,
            hugeImageController: DraweeController?) {
        removeControllerListener(getController())
        addControllerListener(controller)
        mHugeImageController = hugeImageController
        super.setController(controller)
    }

    private fun maybeSetHugeImageController() {
        if (mHugeImageController != null && mZoomableController!!.scaleFactor > HUGE_IMAGE_SCALE_FACTOR_THRESHOLD) {
            setControllersInternal(mHugeImageController, null)
        }
    }

    private fun removeControllerListener(controller: DraweeController?) {
        if (controller is AbstractDraweeController<*, *>) {
            controller
                    .removeControllerListener(mControllerListener)
        }
    }

    private fun addControllerListener(controller: DraweeController?) {
        if (controller is AbstractDraweeController<*, *>) {
            controller
                    .addControllerListener(mControllerListener)
        }
    }

    override fun onDraw(canvas: Canvas) {
        val saveCount = canvas.save()
        canvas.concat(mZoomableController!!.transform)
        try {
            super.onDraw(canvas)
        } catch (e: Exception) {
            val controller = controller
            if (controller != null && controller is AbstractDraweeController<*, *>) {
                val callerContext = controller.callerContext
                if (callerContext != null) {
                    throw RuntimeException(
                            String.format("Exception in onDraw, callerContext=%s", callerContext.toString()),
                            e)
                }
            }
            throw e
        }

        canvas.restoreToCount(saveCount)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val a = event.actionMasked
        if (mTapGestureDetector!!.onTouchEvent(event)) {
            return true
        }

        if (mZoomableController!!.onTouchEvent(event)) {
            if (!mAllowTouchInterceptionWhileZoomed && !mZoomableController!!.isIdentity) {
                parent.requestDisallowInterceptTouchEvent(true)
            }
            return true
        }
        if (super.onTouchEvent(event)) {
            return true
        }
        // None of our components reported that they handled the touch event. Upon returning false
        // from this method, our parent won't send us any more events for this gesture. Unfortunately,
        // some components may have started a delayed action, such as a long-press timer, and since we
        // won't receive an ACTION_UP that would cancel that timer, a false event may be triggered.
        // To prevent that we explicitly send one last cancel event when returning false.
        val cancelEvent = MotionEvent.obtain(event)
        cancelEvent.action = MotionEvent.ACTION_CANCEL
        mTapGestureDetector!!.onTouchEvent(cancelEvent)
        mZoomableController!!.onTouchEvent(cancelEvent)
        cancelEvent.recycle()
        return false
    }

    override fun computeHorizontalScrollRange(): Int {
        return mZoomableController!!.computeHorizontalScrollRange()
    }

    override fun computeHorizontalScrollOffset(): Int {
        return mZoomableController!!.computeHorizontalScrollOffset()
    }

    override fun computeHorizontalScrollExtent(): Int {
        return mZoomableController!!.computeHorizontalScrollExtent()
    }

    override fun computeVerticalScrollRange(): Int {
        return mZoomableController!!.computeVerticalScrollRange()
    }

    override fun computeVerticalScrollOffset(): Int {
        return mZoomableController!!.computeVerticalScrollOffset()
    }

    override fun computeVerticalScrollExtent(): Int {
        return mZoomableController!!.computeVerticalScrollExtent()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        updateZoomableControllerBounds()
    }

    private fun onFinalImageSet() {
        if (!mZoomableController!!.isEnabled) {
            mZoomableController!!.isEnabled = true
            updateZoomableControllerBounds()
        }
    }

    private fun onRelease() {
        mZoomableController!!.isEnabled = false
    }

    protected fun onTransformChanged(transform: Matrix) {
        maybeSetHugeImageController()
        invalidate()
    }

    protected fun updateZoomableControllerBounds() {
        getImageBounds(mImageBounds)
        getLimitBounds(mViewBounds)
        mZoomableController!!.setImageBounds(mImageBounds)
        mZoomableController!!.setViewBounds(mViewBounds)
    }

    protected open fun createZoomableController(): ZoomableController {
        return AnimatedZoomableController.newInstance()
    }

    companion object {

        private val TAG = ZoomableDraweeView::class.java

        private val HUGE_IMAGE_SCALE_FACTOR_THRESHOLD = 1.1f
    }
}
