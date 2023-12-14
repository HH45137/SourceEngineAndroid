# 
export ANDROID_NDK_HOME="../android-ndk-r10e/"
export PATH="/usr/lib/llvm-11/bin:$PATH"
chmod +x "../android-ndk-r10e/" -R

# ARM32
./waf configure -T release --togles --android=armeabi-v7a,host,21 -4 --prefix ../HL2_Android/arm32/
./waf install --strip -j8
# example 
# ./waf install --strip -j8 --target=client,server

# ARM64
./waf configure -T release --togles --android=aarch64,host,21 --prefix ../HL2_Android/arm64/
./waf install --strip -j8
