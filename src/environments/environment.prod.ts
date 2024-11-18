import packageInfo from '../../package.json';

export const environment = {
  appVersion: packageInfo.version,
  production: true,
  apiUrl: 'http://localhost:4200'
  // apiUrl: 'http://172.25.21.25:8080/stationary'

};
