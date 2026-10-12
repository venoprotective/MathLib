# MathLib

Легковесная математическая библиотека на Java для работы с векторами и матрицами. Писал для учебного проекта по компьютерной графике.

### Векторы
- **Vector2f** — двумерный вектор (x, y)
- **Vector3f** — трёхмерный вектор (x, y, z)
- **Vector4f** — четырёхмерный вектор (x, y, z, w)

Все векторы поддерживают:
- Сложение/вычитание
- Умножение/деление на скаляр
- Нормализацию
- Вычисление длины и расстояния между векторами
- Скалярное произведение (dot product)
- Для Vector3f и Vector4f — векторное произведение (cross product)

### Матрицы
- **Matrix3x3** — матрица 3×3 для 2D-трансформаций
- **Matrix4x4** — матрица 4×4 для 3D-трансформаций и проекций

Операции:
- Сложение/вычитание матриц
- Умножение матриц
- Умножение матрицы на вектор
- Транспонирование
- Создание единичной, нулевой и заполненной единицами матриц

### Утилиты
Класс **MathUtils** содержит реализации базовых математических функций:
- `sqrt()` — квадратный корень
- `pow()` / `powInt()` - возведение в степень
- `exp()` — экспонента
- `ln()` — натуральный логарифм
- `abs()`, `max()`, `min()` - абсолютное значение, максимум, минимум

## Сборка и запуск

Проект использует Maven. Для сборки:

```bash
mvn clean install
```

### Примеры использования
```java
// Работа с векторами
Vector3f v1 = new Vector3f(1.0f, 2.0f, 3.0f);
Vector3f v2 = new Vector3f(4.0f, 5.0f, 6.0f);

Vector3f sum = v1.add(v2);
float distance = v1.distance(v2);
Vector3f normalized = v1.normalized();
float dotProduct = v1.dot(v2);
Vector3f crossProduct = v1.cross(v2);

// Работа с матрицами
Matrix4x4 identity = Matrix4x4.identity();
Matrix4x4 matrix4x4 = new Matrix4x4.ones();
Vector4f vec4 = new Vector4f(1, 0, 0, 1);
Vector4f newVec4 = Matrix4x4.multiply(vec4);
matrix4x4.print(); 

// Математические утилиты
float result = MathUtils.pow(2.0f, 10.0f); // 1024.0
float sqrt = MathUtils.sqrt(144.0f);       // 12.0
```

### Структура проекта
```
src/
├── main/java/
│   ├── MathUtils.java      # Математические утилиты
│   ├── Vector2f.java       # 2D вектор
│   ├── Vector3f.java       # 3D вектор
│   ├── Vector4f.java       # 4D вектор
│   ├── Matrix3x3.java      # Матрица 3×3
│   └── Matrix4x4.java      # Матрица 4×4
└── test/java/
    └── MathUtilsTest.java  # Unit-тесты
```

### Тестирование
На данный момент готово тестирование для:
- MathUtilsTest.java
- ...

