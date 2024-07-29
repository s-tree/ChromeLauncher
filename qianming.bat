java -jar ../carQianming/signapk.jar ../carQianming/platform.x509.pem ../carQianming/platform.pk8 build/outputs/apk/release/app-release.apk build/outputs/apk/release/app-signed.apk
adb install -r build/outputs/apk/release/app-signed.apk
adb shell am start com.jingxi.chrome.plugin/com.jingxi.chrome.plugin.MainActivity