package tringuyn.zombieman.news.di
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.cafrecode.obviator.data.di.Injectable
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import tringuyn.zombieman.news.App
import tringuyn.zombieman.news.di.components.DaggerAppComponent


/**
 * Created by Frederick on 20/08/2017.
 */
class AppInjector() {


    companion object {

        @JvmStatic
        fun init(app: App) {

            DaggerAppComponent.builder().application(app)
                .build().inject(app)

            app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    handleActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {

                }

                override fun onActivityResumed(activity: Activity) {

                }

                override fun onActivityPaused(activity: Activity) {

                }

                override fun onActivityStopped(activity: Activity) {

                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

                }

                override fun onActivityDestroyed(activity: Activity) {

                }
            })


        }


        @JvmStatic
        private fun handleActivity(activity: Activity) {
            if (activity is Injectable) {
                AndroidInjection.inject(activity)
            }
            (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(fm: FragmentManager, f: Fragment,
                                                       savedInstanceState: Bundle?) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true)
        }
    }

}