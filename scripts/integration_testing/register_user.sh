curl -i -c cookies.txt -X POST http://localhost:8080/register \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "firstName=Alice&lastName=Smith&username=asmith&password=password123"
