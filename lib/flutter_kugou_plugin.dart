import 'dart:async';

import 'package:flutter/services.dart';

class FlutterKugouPlugin {
  static const MethodChannel _channel = MethodChannel('flutter_kugou_plugin');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<void> init(String appId, String appSecret) async {
    await _channel.invokeMethod('init', {"appId": appId, "appSecret": appSecret});
  }
}
