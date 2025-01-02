# Address API Spec 

## Create Address
Endpoint : POST /api/v1/contacts/{id}/addresses

Request Header: 
- API_TOKEN : Token (Mandatory)
Request Body:

```json
{
  "street": "",
  "city" : "", 
  "province" : "",
  "country" : "", 
  "postalCode" : ""
}
```

Response Body ( Success ): 
```json
{
  "data" : {
    "uuid" : "",
    "street": "",
    "city" : "",
    "province" : "",
    "country" : "",
    "postalCode" : ""
  }
}
```
Response Body ( Failed ):

```json
{
  "message": "Address is not found"
}
```


## Update Address
Endpoint : PUT /api/v1/contacts/{idContact}/addresses/{idAddresses}

Request Header:
- API_TOKEN : Token (Mandatory)
  Request Body:

```json
{
  "street": "",
  "city" : "", 
  "province" : "",
  "country" : "", 
  "postalCode" : ""
}
```

Response Body ( Success ):
```json
{
  "data" : {
    "uuid" : "",
    "street": "",
    "city" : "",
    "province" : "",
    "country" : "",
    "postalCode" : ""
  }
}
```
Response Body ( Failed ):

```json
{
  "message": "Contact is not found"
}
```

## Get Address
Endpoint : GET /api/v1/contacts/{idContact}/addresses/{idAddresses}

Request Header:
- API_TOKEN : Token (Mandatory)


Response Body ( Success ):
```json
{
  "data" : {
    "uuid" : "",
    "street": "",
    "city" : "",
    "province" : "",
    "country" : "",
    "postalCode" : ""
  }
}
```
Response Body ( Failed ):

```json
{
  "message": "Addresses is not found"
}
```

## Delete Address
Endpoint : DELETE /api/v1/contacts/{idContact}/addresses/{idAddresses}

Request Header:
- API_TOKEN : Token (Mandatory)

Response Body ( Success ):
```json
{
  "message" : "OK"
}
```
Response Body ( Failed ): 
```json
{
  "message": "Addresses is not found"
}
```
## List Address
Endpoint : GET /api/v1/contacts/{idContact}/addresses

Request Header:
- API_TOKEN : Token (Mandatory)

Response Body ( Success ):
```json
{
  "data" : [
    {
      "uuid" : "",
      "street": "",
      "city" : "",
      "province" : "",
      "country" : "",
      "postalCode" : ""
    }
  ]
}
```
Response Body ( Failed ):

```json
{
  "message": "Addresses is not found"
}
```