First step
----------

Add a file local.properties in the root directory which contains

sdk.dir=/path/to/android-sdk


Building
--------

# ant debug

Installing
----------

# adb install -r bin/baywinds-debug.apk

Release
-------

First you need to generate a signing key

# keytool -genkey -v -keystore me.keystore -alias me -keyalg RSA -validity 10000

Then update local.properties to include the key store

key.store=/path/to/me.keystore
key.alias=me

Don't forget to update the android:versionName and android:VersionName in AndroidManifest.xml
before running

# ant release

and type the password you used to create your keystore when prompted

