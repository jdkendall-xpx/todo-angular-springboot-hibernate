# Backend Server (API)
## Running the server
From inside the `backend/` folder, run `mvn spring-boot:run` -- this will start Tomcat and serve the API at `localhost:8080`. 
You can then use `curl` or Postman to make requests to the API while this is running like so:

#### Curl
```bash
curl 'http://localhost:8081/api/todos'
```

#### Postman
- Create a new request
- Choose `GET` verb
- URL is `http://localhost:8081/api/todos`