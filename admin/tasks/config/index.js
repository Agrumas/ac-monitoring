module.exports = {
  client: {
    source: ['client/**/*.{html,css,scss,ico}', '!**/app/**'],
    destination: '../web/resources/ac',
    app: ['client/**/*.js']
  },
  server: {
    source: ['server/**/*.{js,json}', '!server/**/*.spec.*'],
    destination: 'dist/server'
  },
  general: {
    source: ['package.json', 'Procfile'],
    destination: 'dist'
  },
  liveReload: {
    port: 35729
  },
  build: {
    destination: '../web/resources'
  }
};
