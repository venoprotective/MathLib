# MathLib

Легковесная математическая библиотека на Java для работы с векторами и матрицами. Писал для учебного проекта по компьютерной графике.

[![Java](https://img.shields.io/badge/Java-11%2B-orange)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/build-Maven-blue)](https://maven.apache.org/)
[![JUnit](https://img.shields.io/badge/test-JUnit%205-green)](https://junit.org/junit5/)

---

## 📦 Возможности

### Векторы

| Класс | Размерность | Назначение |
|-------|-------------|------------|
| `Vector2f` | 2D (x, y) | Плоская геометрия, UI |
| `Vector3f` | 3D (x, y, z) | Трёхмерная графика, нормали |
| `Vector4f` | 4D (x, y, z, w) | Однородные координаты, шейдеры |

Все векторы поддерживают:

- ✅ Сложение / вычитание (`add`, `sub`, `addInPlace`)
- ✅ Умножение / деление на скаляр (`multiply`, `divide`)
- ✅ Длину и квадрат длины (`length`, `lengthSquared`)
- ✅ Расстояние между векторами (`distance`)
- ✅ Нормализацию (`normalized`)
- ✅ Скалярное произведение (`dot`)
- ✅ Векторное произведение (`cross`) — для `Vector3f` и `Vector4f`
- ✅ Читаемое строковое представление (`toString`)

### Матрицы

| Класс | Размер | Назначение |
|-------|--------|------------|
| `Matrix3x3` | 3×3 | 2D-трансформации (поворот, масштаб, сдвиг) |
| `Matrix4x4` | 4×4 | 3D-трансформации и проекции |

Поддерживаемые операции:

- ✅ Сложение / вычитание матриц (`add`, `sub`)
- ✅ Умножение матриц (`multiply(Matrix)`)
- ✅ Умножение матрицы на вектор (`multiply(Vector)`)
- ✅ Транспонирование (`transpose`)
- ✅ Статические конструкторы: `zeros()`, `ones()`, `identity()`
- ✅ Отладочная печать (`print`)

### Математические утилиты — `MathUtils`

Реализации базовых математических функций «с нуля»:

| Метод | Описание |
|-------|----------|
| `sqrt(float)` | Квадратный корень |
| `pow(float, float)` | Возведение в степень |
| `powInt(float, int)` | Целочисленная степень |
| `exp(float)` | Экспонента |
| `ln(float)` | Натуральный логарифм |
| `abs(float)` | Абсолютное значение |
| `max(float, float)` | Максимум |
| `min(float, float)` | Минимум |

---

## 🚀 Сборка и запуск

Проект собирается через **Maven**. Требуется **JDK 11+**.

```bash
# Сборка и установка в локальный репозиторий
mvn clean install

# Только компиляция
mvn compile

# Запуск тестов
mvn test
```

## 💡 Примеры использования

### Векторы

```java
Vector3f v1 = new Vector3f(1.0f, 2.0f, 3.0f);
Vector3f v2 = new Vector3f(4.0f, 5.0f, 6.0f);

Vector3f sum        = v1.add(v2);        // (5, 7, 9)
Vector3f diff       = v2.sub(v1);        // (3, 3, 3)
float    distance   = v1.distance(v2);
Vector3f normalized = v1.normalized();   // единичный вектор
float    dot        = v1.dot(v2);        // 32
Vector3f cross      = v1.cross(v2);      // (-3, 6, -3)
```
### Матрицы
```java
// Единичная матрица 4×4
Matrix4x4 identity = Matrix4x4.identity();

// Матрица трансляции по X
Matrix4x4 translation = Matrix4x4.identity();
translation.data[0][3] = 5.0f;

// Применение к вектору
Vector4f point = new Vector4f(1, 0, 0, 1);
Vector4f moved = translation.multiply(point);   // (6, 0, 0, 1)

// Умножение матриц
Matrix4x4 composed = identity.multiply(translation);

// Транспонирование и вывод
composed.transpose().print();
```

## Структура проекта
```
src/
├── main/java/
│   ├── Vector2f.java       # 2D вектор
│   ├── Vector3f.java       # 3D вектор
│   ├── Vector4f.java       # 4D вектор
│   ├── Matrix3x3.java      # Матрица 3×3
│   └── Matrix4x4.java      # Матрица 4×4
└── test/java/
    ├── Vector2fTest.java
    ├── Vector3fTest.java
    ├── Vector4fTest.java
    ├── Matrix3x3Test.java
    └── Matrix4x4Test.java
```

## ⚠️ Особенности реализации
```
Все вычисления выполняются в float (32-битная точность) — как в графических API.
Векторы и матрицы иммутабельны по умолчанию: add, sub, multiply возвращают новые объекты. Для изменения на месте предусмотрены *InPlace-методы (addInPlace).
При нормализации нулевого вектора бросается ArithmeticException.
Деление на ноль бросает IllegalArgumentException.
Matrix4x4 хранит данные в row-major порядке (data[строка][столбец]).
```
