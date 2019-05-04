# THIS APP IS OBSOLETE!
Don't use this app because the lms-material plugin provides the possibility to add
the webapp to your android start screen and integrates with taskbar.

# lms-material-app
Simple Web View app for LMS material plugin https://github.com/CDrummond/lms-material

This application is only useful if you drive a squeezebox server at home and use the lms-material plugin for
controlling your music with your android smartphone.

This app will provide these additional features in comparison to running in a browser:

* fullscreen view
* volume control using hardware buttons

At least android 5 is required for running this app. The app has been tested for android 9 and android 7.

## Release Download

https://github.com/andreasbehnke/lms-material-app/releases

## Building and Signing the App

You can build the app using your own signing key. Only signed apk files can be installed by downloading, so the signing 
process is required. 

Read this documentation for details: https://developer.android.com/studio/publish/app-signing

* create a jsk keystore and a key for signing app:
```
keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-alias
```
* create file keystore.properties:
```
storePassword=myStorePassword
keyPassword=mykeyPassword
keyAlias=my-alias
storeFile=my-release-key.jks
```
* secure this file:
```
chmod 600 keystore.properties
```
* build release apk:
```
./gradlew assembleRelease
```
* move release artifact to your phone: 
```
/lms-material-app/app/build/outputs/apk/release/app-release.apk 
```
