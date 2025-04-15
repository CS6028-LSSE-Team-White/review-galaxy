# Stage 1: Build the Angular app
FROM node:alpine AS builder
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install -g @angular/cli
RUN npm install
COPY . .
RUN npm run build:prod

# Stage 2: Serve with Nginx
FROM nginx:alpine
RUN apk add --no-cache gettext
RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /usr/src/app/dist/visualization/ /usr/share/nginx/html/
COPY --from=builder /usr/src/app/src/assets/config.template.js /usr/share/nginx/html/assets/config.template.js
COPY entrypoint.sh /entrypoint.sh
RUN apk add --no-cache dos2unix && dos2unix /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENV MIDDLEWARE_ADDR=http://localhost:3000/
ENTRYPOINT ["/entrypoint.sh"]
