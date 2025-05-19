# Stage 1: Build the React app
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Serve with Nginx over HTTPS
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Copy static assets
COPY --from=build /app/build .

# Copy certs and config
COPY server.crt /etc/nginx/certs/server.crt
COPY server.key /etc/nginx/certs/server.key
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 443 80
CMD ["nginx", "-g", "daemon off;"]
