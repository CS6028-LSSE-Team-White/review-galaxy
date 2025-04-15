FROM node:alpine
RUN apk add --no-cache gettext
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN npm install -g @angular/cli
RUN npm install
ENV MIDDLEWARE_ADDR=http://localhost:3000/
ENTRYPOINT ["/usr/src/app/entrypoint.sh"]