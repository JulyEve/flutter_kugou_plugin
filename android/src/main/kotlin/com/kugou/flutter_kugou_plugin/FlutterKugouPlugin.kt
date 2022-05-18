package com.kugou.flutter_kugou_plugin


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import com.kugou.opensdk.BuildConfig
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import com.kugou.opensdk.commomtransformer.ErrorCodes;
import com.kugou.opensdk.kgmusicaidlcop.KGCommonSdk;
import com.kugou.opensdk.kgmusicaidlcop.entity.ClientInfo;

/** FlutterKugouPlugin */
class FlutterKugouPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var context: Context

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_kugou_plugin")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "init") {
            var appId: String? = call.argument("appId")
            var appSecret: String? = call.argument("appSecret")
            if (appId != null && appSecret != null) {
                initial(context, appId, appSecret);
            };
            result.notImplemented()
        } else if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    private fun initial(context: Context, appId: String, appSecret: String) {

        KGCommonSdk.getInstance(context).setLogEnable(true)
       val code =  KGCommonSdk.getInstance(context).accountApi.init(
            appId,
            appSecret,
            context.applicationContext as Application,
            ClientInfo(
                "com.kugou.flutter_kugou_plugin",
                2204,
                // 扩展信息，接口调用日志存作记录。字符串限制最长300，超出时只截前300个字符
                "backups_code",
                null,
                null
            )
        )

        if (ErrorCodes.ERROR_OK == code) {
            Log.d(TAG, "初始化，成功");
        } else {
            Log.d(TAG, "初始化，失败：errCode" + code);
        }


    }

    companion object{

        const val TAG = "FlutterKugouPlugin"

    }

}
