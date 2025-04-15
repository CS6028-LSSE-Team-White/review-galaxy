#!/bin/sh
envsubst '$MIDDLEWARE_ADDR' < /usr/share/nginx/html/assets/config.template.js > /usr/share/nginx/html/assets/config.js
exec nginx -g 'daemon off;'