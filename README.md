Для запуска приложения необходимо:

Java 21
Maven 3.8
PostgreSQL 12+
Docker** (для запуска через контейнеры)


Task Management — это Spring Boot приложение для управления задачами, включающее авторизацию/регистрацию через jwt  и базу данных PostgreSQL.


Сборка и запуск

1. "docker-compose up -d"
Поднимите бд через докер, написав эту команду в терминале
Дальше запускаем проект через эти команды
2. mvn clean install
3. mvn spring-boot:run

Можете протестировать через Postman по адресу

http://localhost:8080


   Основные эндпоинты

Аутентификация  

	•	POST /api/auth/register - регистрация нового пользователь  
 
 { 
   "email": "test@gmail.com",
   "password": "password"
 }  
 После получения токена введите его в Header, в key указать "Authorization"
 А в Value указать "Bearer *ваш токен*"
	•	POST /api/auth/login – вход в систему и получение токенов

Задачи  

	•	GET /api/tasks - список задач пользователя  

 {
   "title":"title1",
   "description":"example",
   "status"="IN_PROGRESS"
}

	•	GET /api/tasks?status=DONE - фильтрация задач по статусу
	•	GET /api/tasks/{id} - получить задачу по ID
	•	POST /api/tasks - создать новую задачу
	•	PUT /api/tasks/{id} - обновить задачу
	•	PATCH /api/tasks/{id} - частичное обновление задачи
	•	DELETE /api/tasks/{id} - удалить задачу
