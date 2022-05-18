#import "FlutterKugouPlugin.h"
#if __has_include(<flutter_kugou_plugin/flutter_kugou_plugin-Swift.h>)
#import <flutter_kugou_plugin/flutter_kugou_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutter_kugou_plugin-Swift.h"
#endif

@implementation FlutterKugouPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterKugouPlugin registerWithRegistrar:registrar];
}
@end
