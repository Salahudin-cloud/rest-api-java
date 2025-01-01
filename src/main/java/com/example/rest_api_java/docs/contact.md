# Contact API Spec 

## Create Contact 
EndPoint : POST /api/contacts
Request Header:
- API_TOKEN : Token (Mandatory)
Request Body :

```json
{
  "firstName": "Muhammad",
  "LastName" : "Salahudin",
  "email" : "salahudin@gmail.com", 
  "phone" : "892939123"
}
```
Response Body ( Success ) : 
```json
{
  "data" : {
    "uuid" : "random string",
    "firstName": "Muhammad",
    "LastName" : "Salahudin",
    "email" : "salahudin@gmail.com",
    "phone" : "892939123"
  }
}
```
Response Body ( Failed ) : 
```json
{
  "message" : "Can't create new contact"
}
```

## Update Contact 
EndPoint  PUT /api/v1/contacts/{id}
Request Header:
- API_TOKEN : Token (Mandatory)
Request Body :
```json
{
  "firstName": "Muhammad",
  "LastName" : "Salahudin",
  "email" : "salahudin@gmail.com",
  "phone" : "892939123"
}
```
Response Body ( Success ) :
```json
{
  "data" : {
    "uuid" : "random string",
    "firstName": "Muhammad",
    "LastName" : "Salahudin",
    "email" : "salahudin@gmail.com",
    "phone" : "892939123"
  }
}
```
Response Body ( Failed ) :
```json
{
  "message" : "Can't create new contact"
}
```

## Get Contact
EndPoint : GET /api/v1/contacts/{id}
Request Header:
- API_TOKEN : Token (Mandatory)
Response Body ( Success ) :
```json
{
  "data" : {
    "uuid" : "random string",
    "firstName": "Muhammad",
    "LastName" : "Salahudin",
    "email" : "salahudin@gmail.com",
    "phone" : "892939123"
  }
}
```
Response Body ( Failed , 404 ) :
```json
{
  "message" : "Contact is not found"
}
```
## Search Contact 
EndPoint : GET /api/contacts
Query Param : 
- name : String, contact first name or last name , using like query, optional
- phone : String, contact phone , using like query ,optional
- email : String , contact email, using like query , optional
- page : Integer , start from zero , default 0
- size : Integer , default 10
Request Header:
- API_TOKEN : Token (Mandatory)

Response Body ( Success ) :

```json
{
  "data": [
    {
      "uuid" : "random string",
      "firstName": "Muhammad",
      "LastName" : "Salahudin",
      "email" : "salahudin@gmail.com",
      "phone" : "892939123"
    }
  ],
  "paging" : {
    "currentPage" : 0,
    "totalPage" : 10,
    "size" : 10
  } 
}
```
Response Body ( Failed ) :
```json
{
  "message" : "Unauthorized"
}
```
## Remove Contact 
EndPoint : DELETE /api/v1/contacts/{id}
Request Header:
- API_TOKEN : Token (Mandatory)
Response Body ( Success ) :
```json
{
  "message" : "Delete contact successfully"
}
```
Response Body ( Failed ) : 
```json
{
  "message" : "Contact is not found"
}
```