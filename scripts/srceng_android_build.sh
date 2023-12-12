export ANDROID_NDK_HOME="../android-ndk-r10e/"
export PATH="/usr/lib/llvm-11/bin:$PATH"
chmod +x "../android-ndk-r10e/" -R

# ARM32
./waf configure -T release --togles --android=armeabi-v7a,host,21 -4
./waf build
./waf install --destdir=../HL2_Android/arm32/ --strip

# ARM64
./waf configure -T release --togles --android=aarch64,host,21
./waf build
./waf install --destdir=../HL2_Android/arm64/ --strip
