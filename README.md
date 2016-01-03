WearableListViewDemo
====================

For a detailed article, have a look at <a href="http://www.technotalkative.com/android-wear-part-5-wearablelistview/" target="_blank">WearableListView</a>.

It contains two examples:
<ol>
<li>Simple WearableListView demo</li>
<li>Demo to replicate start menu or settings screen.</li>
</ol>

<a href="http://www.technotalkative.com/?attachment_id=5581" rel="attachment wp-att-5581" 
target="_blank"><img src="http://www.technotalkative.com/wp-content/uploads/2014/08/android-wearablelistview-simple-286x300.png" alt="android wearablelistview simple" 
width="286" height="300" class="alignleft size-medium wp-image-5581" style="margin-right=10px;" /></a>

<a href="http://www.technotalkative.com/?attachment_id=5582" rel="attachment wp-att-5582" target="_blank">
<img src="http://www.technotalkative.com/wp-content/uploads/2014/08/android-wearablelistview-demo-286x300.png" 
alt="android wearablelistview demo" width="286" height="300" class="alignleft size-medium wp-image-5582" /></a>

Debugging wearable app on device:
=================================

To set up an Android Wear device:

    Install the Android Wear app, available on Google Play, on your handheld.
    Follow the app's instructions to pair your handheld with your wearable. This allows you to test out synced handheld notifications, if you're building them.
    Leave the Android Wear app open on your phone.
    Enable adb debugging on the Android Wear device.
        Go to Settings > About.
        Tap Build number seven times.
        Swipe right to return to the Settings menu.
        Go to Developer options at the bottom of the screen.
        Tap ADB Debugging to enable adb.
    Connect the wearable to your machine through USB, so you can install apps directly to it as you develop. A message appears on both the wearable and the Android Wear app prompting you to allow debugging.

    Note: If you can not connect your wearable to your machine via USB, you can try connecting over Bluetooth.
    On the Android Wear app, check Always allow from this computer and tap OK.

The Android tool window on Android Studio shows the system log from the wearable. The wearable should also be listed when you run the adb devices command.


Set Up a Debugging Session

    On the handheld, open the Android Wear companion app.
    Tap the menu on the top right and select Settings.
    Enable Debugging over Bluetooth. You should see a tiny status summary appear under the option:

    Host: disconnected
    Target: connected

    Connect the handheld to your machine over USB and run:

    adb forward tcp:4444 localabstract:/adb-hub
    adb connect localhost:4444
	(If you get message connection refused use 127.0.0.1:4444 instead.)

	To remove port forwarding:
        adb forward --remove tcp:4444

    Note: You can use any available port that you have access to.

In the Android Wear companion app, you should see the status change to:

Host: connected
Target: connected

To install app:
adb -s 127.0.0.1:4444 install wear/build/outputs/apk/wear-debug.apk

To uninstall app:
adb -s 127.0.0.1:4444 uninstall com.technotalkative.wearablelistviewdemo
