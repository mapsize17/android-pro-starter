#!/usr/bin/env bash
set -euo pipefail

echo "=== Android Starter Template Verifier ==="
echo ""

# Check Java
if command -v java &> /dev/null; then
    JAVA_VER=$(java -version 2>&1 | head -1 | cut -d'"' -f2)
    echo "✅ Java: $JAVA_VER"
else
    echo "❌ Java not found. Install JDK 17+."
    exit 1
fi

# Check Android SDK
if [ -n "${ANDROID_HOME:-}" ] || [ -n "${ANDROID_SDK_ROOT:-}" ]; then
    SDK_DIR="${ANDROID_HOME:-$ANDROID_SDK_ROOT}"
    echo "✅ Android SDK: $SDK_DIR"
    if [ -f "$SDK_DIR/platforms/android-35/android.jar" ]; then
        echo "   - platform android-35: found"
    else
        echo "   ⚠️  platform android-35 not found. Run: sdkmanager 'platforms;android-35'"
    fi
else
    echo "⚠️  ANDROID_HOME not set. Set it to your Android SDK path."
fi

# Check Gradle wrapper
if [ -f "./gradlew" ]; then
    echo "✅ Gradle wrapper: found"
else
    echo "❌ gradlew not found. Run: gradle wrapper --gradle-version=8.10.2"
    exit 1
fi

echo ""
echo "=== Running lint and unit tests ==="
echo ""

./gradlew lintDebug testDebugUnitTest --no-daemon --continue

echo ""
echo "=== ✅ All checks passed! ==="
echo "The project is ready to build."
echo "Run: ./gradlew assembleDebug"
