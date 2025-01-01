# User API Spec 

## Register User 
Endpoint : POST /api/v1/user
Request Body :
```json
{
  "username" : "Rem",
  "password" : "asdf",
  "name" : "Rem Chan"
}
```

Response Body (Success) :

```json
{
  "message": "OK"
}
```

Response Body (Failed) :

```json
{
  "message": "Username must not blank, ???"
}
```

## Login User
Endpoint : POST /api/v1/auth/login
Request Body :
```json
{
  "username" : "Rem",
  "password" : "asdf"
}
```

Response Body (Success) :

```json
{
  "message": "OK",
  "data": {
    "token": "TOKEN",
    "expireAt": 12312312 // miliseconds
  }
}
```

Response Body (Failed, 401) :

```json
{
  "message": "username or password wrong"
}
```

## Get User
Endpoint : GET /api/v1/users/{id}
Request Header: 
- API_TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "message": "OK",
  "data": {
    "username" : "Rem",
    "name" : "Rem Chan"
  }
}
```

Response Body (Failed, 401) :

```json
{
  "message": "Unauthorized"
}
```

## Update User
Endpoint : PATCH /api/v1/users/{id}
Request Header:
- API_TOKEN : Token (Mandatory)
Request Body :

```json
{
  "name": "Rem only", // put if only want to update name 
  "password" : "New Password" // put if only want to update name
}
```
Response Body (Success) :

```json
{
  "message": "OK",
  "data": {
    "username" : "Rem",
    "name" : "Rem Chan"
  }
}
```

Response Body (Failed, 401) :

```json
{
  "message": "Unauthorized"
}
```

## Logout User 
Endpoint : DELETE /api/v1/auth/logout

Request Header:
- API_TOKEN : Token (Mandatory)
  Response Body (Success) :
```json
{
  "message": "OK"
}
```