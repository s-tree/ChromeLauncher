java -jar ../qianming/signapk.jar ../qianming/platform.x509.pem ../qianming/platform.pk8 build/outputs/apk/debug/app-debug.apk build/outputs/apk/debug/app-signed.apk
adb install -r build/outputs/apk/debug/app-signed.apk
adb shell am start com.jingxi.chrome.plugin/com.jingxi.chrome.plugin.MainActivity