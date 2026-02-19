# My Fabric Mod - 炼金术入门 (Alchemy Basics)

这是一个基于 Fabric Loader 的 Minecraft (1.20.1) 模组演示项目，展示了自定义物品、功能方块、GUI、以及复杂的合成模型。

## 🌟 核心功能

### 1. 炼金锅 (Alchemical Crucible) — 功能方块
*   **用途**: 用于处理原材料以提取炼金产物。
*   **操作**: 拥有类似熔炉的自定义 GUI 界面。需要放入**煤炭或木炭**作为燃料。
*   **炼制逻辑**: 放入**红石粉**作为原材料，经过一段时间的炼制，可以产出**水银锭**。

### 2. 水银锭 (Mercury Ingot) — 炼金产物
*   **描述**: 通过炼金锅提炼出的液体金属，是合成高级炼金法器的核心材料。

### 3. 贤者之石 (Philosopher's Stone) — 传奇物品
*   **点石成金**: 手持贤者之石，对着世界中的 **铁块 (Iron Block)** 或 **铜块 (Copper Block)** 点击 **右键**，将其转化为 **金块 (Gold Block)**。
*   **耐久说明**: 作为强大的法器，它是消耗品，使用时会消耗耐久。

## ⚒️ 合成配方 (Recipes)

### 炼金锅
在工作台中摆放铁锭成 U 型，中心放入一个金锭：
```
铁 🔌 铁
铁 金 铁
铁 铁 铁
```

### 贤者之石
使用钻石、水银锭和金锭合成：
*   **图案**:
    ```
    钻石 水银 钻石
    水银 金锭 水银
    钻石 水银 钻石
    ```

## 🚀 开发与编译指南

本快速入门项目提供了两个自动化脚本，旨在简化 SDK 管理。

### 1. 开发调试 (运行游戏)
```bash
# 自动配置 Java 17/Gradle 8.7 运行环境并启动游戏
bash setup_and_run.sh
```

### 2. 打包发布 (生成 JAR)
```bash
# 执行清理并编译生成的混淆后的 Mod JAR 文件
bash build_mod.sh
```
*   **产物位置**: `build/libs/my-fabric-mod-1.0.0.jar`

## 🛠️ 技术细节

*   **Minecraft 版本**: 1.20.1
*   **Fabric API**: 0.92.2+1.20.1
*   **核心特性**:
    *   **容器系统 (Inventory)**: 实现了 `ImplementedInventory` 接口处理方块实体库存。
    *   **自定义 GUI**: 实现了 `ExtendedScreenHandlerFactory` 以支持服务端-客户端数据同步，并复用了原版熔炉贴图以保持视觉统一。
    *   **方块实体 (Block Entity)**: 使用 `BlockEntityTicker` 实现动态炼制进度。

## 📁 关键代码位置

*   `com.enzo.mod.block.AlchemicalCrucibleBlock`: 炼金锅方块实现。
*   `com.enzo.mod.block.entity.AlchemicalCrucibleBlockEntity`: 炼金锅核心逻辑（消耗燃料、进度计算）。
*   `com.enzo.mod.screen.AlchemicalCrucibleScreen`: 客户端 GUI 渲染逻辑。
*   `com.enzo.mod.item.PhilosophersStoneItem`: 贤者之石点击转化的业务逻辑。
