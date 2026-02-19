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

# 显式加载 Gradle 路径，防止 PATH 未更新
export GRADLE_HOME="$HOME/.sdkman/candidates/gradle/8.7"
export PATH="$GRADLE_HOME/bin:$PATH"

echo "--- 正在生成 Gradle Wrapper ---"
# 优先使用项目目录下的 gradlew，如果没有则使用全局 gradle
if [[ -f "./gradlew" ]]; then
    ./gradlew wrapper
else
    gradle wrapper || "$GRADLE_HOME/bin/gradle" wrapper
fi

echo "--- 正在尝试启动 Minecraft (runClient) ---"
echo "提示: 如果下载缓慢或报错，请检查网络代理设置。"
# 运行游戏
if [[ -f "./gradlew" ]]; then
    ./gradlew runClient
else
    echo "错误: 未能生成 gradlew 脚本。"
    exit 1
fi
