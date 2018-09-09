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

package com.agenthun.foood.util.fresco


import android.content.Context
import com.agenthun.foood.util.OkHttpClientProvider
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.internal.Suppliers
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.DefaultExecutorSupplier
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import java.util.*

/**
 * Creates ImagePipeline configuration for the sample app
 */
object ImagePipelineConfigFactory {
    private val IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache"

    private var sImagePipelineConfig: ImagePipelineConfig? = null
    private var sOkHttpImagePipelineConfig: ImagePipelineConfig? = null

    /**
     * Creates config using android http stack as network backend.
     */
    fun getImagePipelineConfig(context: Context): ImagePipelineConfig {
        if (sImagePipelineConfig == null) {
            val configBuilder = ImagePipelineConfig.newBuilder(context)
            configureCaches(configBuilder, context)
            configureLoggingListeners(configBuilder)
            configureOptions(configBuilder)
            configureProgressiveJpeg(configBuilder)
            sImagePipelineConfig = configBuilder.build()
        }
        return sImagePipelineConfig!!
    }

    /**
     * Creates config using OkHttp as network backed.
     */
    fun getOkHttpImagePipelineConfig(context: Context): ImagePipelineConfig {
        if (sOkHttpImagePipelineConfig == null) {
            val configBuilder = OkHttpImagePipelineConfigFactory.newBuilder(context,
                    OkHttpClientProvider.getInstance())
            configureCaches(configBuilder, context)
            configureLoggingListeners(configBuilder)
            configureOptions(configBuilder)
            configureProgressiveJpeg(configBuilder)
            sOkHttpImagePipelineConfig = configBuilder.build()
        }
        return sOkHttpImagePipelineConfig!!
    }

    /**
     * Configures disk and memory cache not to exceed common limits
     */
    private fun configureCaches(
            configBuilder: ImagePipelineConfig.Builder,
            context: Context) {
        val bitmapCacheParams = MemoryCacheParams(
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE, // Max entries in the cache
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE, // Max length of eviction queue
                Integer.MAX_VALUE)                    // Max cache entry size
        configBuilder
                .setBitmapMemoryCacheParamsSupplier { bitmapCacheParams }
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(context)
                                .setBaseDirectoryPath(context.applicationContext.cacheDir)
                                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                                .setMaxCacheSize(ConfigConstants.MAX_DISK_CACHE_SIZE.toLong())
                                .build())
    }

    private fun configureLoggingListeners(configBuilder: ImagePipelineConfig.Builder) {
        val requestListeners = HashSet<RequestListener>()
        requestListeners.add(RequestLoggingListener())
        configBuilder.setRequestListeners(requestListeners)
    }

    private fun configureOptions(configBuilder: ImagePipelineConfig.Builder) {
        configBuilder.isDownsampleEnabled = true
        configBuilder.setExecutorSupplier(DefaultExecutorSupplier(Runtime.getRuntime().availableProcessors()))
        configBuilder.experiment()
                .setBitmapPrepareToDraw(true, 0, Int.MAX_VALUE, true)
                .experiment()
                .setSmartResizingEnabled(Suppliers.BOOLEAN_TRUE)
    }

    //渐进式的网络JPEG图
    private fun configureProgressiveJpeg(configBuilder: ImagePipelineConfig.Builder) {
        configBuilder.setProgressiveJpegConfig(SimpleProgressiveJpegConfig())
    }
}
