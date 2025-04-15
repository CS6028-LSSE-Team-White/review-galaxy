FROM node:alpine
RUN apk add --no-cache gettext
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN npm install -g @angular/cli
RUN npm install
ENV MIDDLEWARE_ADDR=http://localhost:3000/
RUN envsubst '$MIDDLEWARE_ADDR' < /usr/src/app/src/assets/config.template.js > /usr/src/app/src/assets/config.js
CMD ["ng", "serve", "--host", "0.0.0.0"]