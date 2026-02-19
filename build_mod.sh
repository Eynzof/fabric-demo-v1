#!/bin/bash

# 设置 SDKMAN 环境
export SDKMAN_DIR="$HOME/.sdkman"
if [[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]]; then
    source "$HOME/.sdkman/bin/sdkman-init.sh"
else
    echo "错误: 未找到 SDKMAN，请先安装 SDKMAN (https://sdkman.io/install)"
    exit 1
fi

echo "--- 正在检查并切换至 Java 17 ---"
# 安装并使用 Java 17 (Zulu)
sdk install java 17.0.18-zulu < /dev/null
sdk use java 17.0.18-zulu

echo "--- 正在检查并安装 Gradle 8.7 ---"
# 安装 Gradle 8.7
sdk install gradle 8.7 < /dev/null
sdk use gradle 8.7

# 显式加载 Gradle 路径
export GRADLE_HOME="$HOME/.sdkman/candidates/gradle/8.7"
export PATH="$GRADLE_HOME/bin:$PATH"

echo "--- 正在清理并开始编译 Mod (build) ---"
if [[ -f "./gradlew" ]]; then
    ./gradlew clean build
else
    echo "--- 正在尝试生成 Gradle Wrapper ---"
    gradle wrapper || "$GRADLE_HOME/bin/gradle" wrapper
    ./gradlew clean build
fi

if [[ $? -eq 0 ]]; then
    echo ""
    echo "=================================================="
    echo "✅ 编译成功！"
    echo "你的 Mod 文件位于: build/libs/"
    ls -lh build/libs/ | grep ".jar" | grep -v "sources"
    echo "=================================================="
else
    echo "❌ 编译失败，请检查上方报错信息。"
    exit 1
fi
