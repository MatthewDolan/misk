package misk.feature.testing

import com.google.inject.Provides
import com.google.inject.Singleton
import com.squareup.moshi.Moshi
import misk.feature.FeatureFlags
import misk.feature.FeatureService
import misk.ServiceModule
import misk.feature.DynamicConfig
import misk.inject.KAbstractModule
import misk.inject.toKey
import kotlin.reflect.KClass

/**
 * Binds a [FakeFeatureFlags] that allows tests to override values.
 */
class FakeFeatureFlagsModule(
  private val qualifier: KClass<out Annotation>? = null
) : KAbstractModule() {
  private val testFeatureFlags = FakeFeatureFlags(getProvider(Moshi::class.java))

  override fun configure() {
    val key = FakeFeatureFlags::class.toKey(qualifier)
    bind(key).toInstance(testFeatureFlags)
    bind(FeatureFlags::class.toKey(qualifier)).to(key)
    bind(FeatureService::class.toKey(qualifier)).to(key)
    bind(DynamicConfig::class.toKey(qualifier)).to(key)
    install(ServiceModule(FeatureService::class.toKey(qualifier)))
  }

  /**
   * Add overrides for the feature flags. Allows flags to be overridden at module instantiation
   * instead of within individual test classes.
   *
   * Usage:
   * ```
   * install(FakeFeatureFlagsModule().withOverrides {
   *   override(Feature("foo"), true)
   * })
   * ```
   */
  fun withOverrides(lambda: FakeFeatureFlags.() -> Unit): FakeFeatureFlagsModule {
    lambda(testFeatureFlags)
    return this
  }
}
