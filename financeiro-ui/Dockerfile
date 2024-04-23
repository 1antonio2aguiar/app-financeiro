FROM node:latest as angular 
WORKDIR /src/app
COPY package.json /src/app
RUN npm install --legacy-peer-deps --quiet
COPY . .
RUN npm run build

FROM nginx:alpine
VOLUME /var/cache/nginx
COPY --from=angular /dist/financeiro-http /usr/share/nginx/html
COPY /config/nginx.conf /etc/nginx/conf.d/default.conf

# docker build -t financeiro-angular .
# docker run -p 8081:80 financeiro-angular