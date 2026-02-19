# My Fabric Mod - 贤者之石 (Philosopher's Stone)

这是一个基于 Fabric Loader 的 Minecraft (1.20.1) 模组演示项目。

## 🌟 核心功能：贤者之石

本模组添加了一个极具魔力的物品：**贤者之石 (Philosopher's Stone)**。
*   **点石成金**: 手持贤者之石，对着世界中的 **铁块 (Iron Block)** 或 **铜块 (Copper Block)** 点击 **右键**，它们会瞬间转化为价值连城的 **金块 (Gold Block)**。
*   **获取方式**: 在创造模式的“工具与武器” (Tools & Utilities) 选项卡中可以找到，或者直接搜索名称获取。

## 🛠️ 环境要求

*   **Minecraft 版本**: 1.20.1
*   **Java**: 17 (推荐使用 SDKMAN 安装 Zulu 17)
*   **Fabric API**: 已集成在构建依赖中

## 🚀 如何运行

如果你在本地已经安装了 [SDKMAN](https://sdkman.io/)，可以直接使用我为你准备的自动化脚本：

```bash
# 进入项目目录
cd fabric-demo-v1

# 运行自动化配置并启动游戏
bash setup_and_run.sh
```

该脚本会自动完成：
1. 切换 Java 17 运行环境。
2. 安装并配置 Gradle 8.7。
3. 生成 Gradle Wrapper。
4. 编译项目并启动游戏客户端。

## 📁 项目结构

*   `src/main/java/com/enzo/mod/item/PhilosophersStoneItem.java`: 包含点金转化的核心逻辑。
*   `src/main/java/com/enzo/mod/item/ModItems.java`: 物品注册与创造栏添加逻辑。
*   `src/main/resources/assets/my_fabric_mod/`: 包含语言包 (i18n) 和物品模型定义。

## 📝 备注
目前由于资源生成限制，贤者之石在物品栏中显示为丢失贴图（紫黑块）。你可以手动将贴图文件放置在 `src/main/resources/assets/my_fabric_mod/textures/item/philosophers_stone.png`。
