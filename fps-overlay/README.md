# FPS Overlay Mod — Minecraft 1.16.5 Forge

Настраиваемый оверлей FPS для Minecraft 1.16.5 с поддержкой Forge.

## Что умеет мод

- Показывает FPS в реальном времени: `[ FPS: 120 ]`
- Полная кастомизация текста:
  - **Цвет** (R, G, B, прозрачность Alpha — ползунки)
  - **Жирный** (Bold)
  - *Курсив* (Italic)
  - ~~Зачёркнутый~~ (Strikethrough)
  - Подчёркнутый (Underline)
  - Запутанный / матричный (Obfuscated)
  - Тень под текстом (Shadow)
  - Масштаб (Scale, от 0.25x до 5x)
- Кастомные символы скобок: префикс и суффикс (любые символы, например `«` / `»`, `{` / `}`, `>>` / `<<`)
- Позиция на любом месте экрана — перетащить мышкой в меню настроек
- Сохранение всех настроек между сессиями

## Горячая клавиша

| Клавиша | Действие |
|---------|----------|
| **F9** | Открыть меню настроек |

(Можно изменить в Minecraft → Options → Controls → FPS Overlay)

## Требования

- Minecraft **1.16.5**
- **Forge 36.2.39** или новее

## Как собрать из исходников

### Требования к системе
- JDK 8 (Java 8)
- [Gradle Wrapper](https://gradle.org/install/) или `./gradlew`

### Шаги

```bash
# 1. Войти в папку мода
cd fps-overlay

# 2. Скачать зависимости и сгенерировать Minecraft sources (первый запуск долгий ~5 мин)
./gradlew genIntellijRuns   # для IntelliJ IDEA
# или
./gradlew genEclipseRuns    # для Eclipse

# 3. Собрать JAR
./gradlew build

# 4. Готовый JAR находится в:
#    build/libs/fpsoverlay-1.0.0.jar
```

### Установка

Скопируй `fpsoverlay-1.0.0.jar` в папку `.minecraft/mods/`

## Структура проекта

```
src/main/java/com/fpsoverlay/
├── FPSOverlayMod.java          — точка входа мода
├── config/
│   └── FPSConfig.java          — все настройки (Forge TOML config)
├── gui/
│   ├── OverlaySettingsScreen.java  — экран настроек с превью и перетаскиванием
│   └── ColorSlider.java            — слайдер для цвета / масштаба
├── keybind/
│   └── KeyBindings.java        — регистрация клавиши F9
└── overlay/
    └── FPSOverlay.java         — рендер оверлея поверх игры
```

## Файл конфига

После первого запуска создаётся файл:
```
.minecraft/config/fpsoverlay-client.toml
```
Его можно редактировать вручную — изменения применяются при следующем запуске игры или после переоткрытия меню настроек.

## Лицензия

MIT
