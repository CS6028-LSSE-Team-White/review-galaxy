#!/bin/sh
envsubst '$MIDDLEWARE_ADDR' < /usr/src/app/src/assets/config.template.js > /usr/src/app/src/assets/config.js
exec ng serve --host 0.0.0.0