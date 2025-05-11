curl --location 'localhost:8080/api/auth/register' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Cookie: JSESSIONID=5E2267C3E9E253417D48225CBBCBD7EE' \
--data '{
    "firstName": "Iason",
    "lastName": "Chatzopoulos",
    "username": "ichatzop2",
    "password": "mysecurepass"
}'