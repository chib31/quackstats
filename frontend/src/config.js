export default {
  RUN_MODE:             process.env.NODE_ENV,
  TEAM_UUID:            process.env.VUE_APP_TEAM_UUID,
  BASE_URL:             process.env.VUE_APP_BASE_URL,
  STATS_API_PATH:       process.env.VUE_APP_STATS_API_PATH,
  REQUEST_TIMEOUT:      process.env.VUE_APP_REQUEST_TIMEOUT,
  API_USER:             process.env.VUE_APP_API_USER,
  API_PASSWORD:         process.env.VUE_APP_API_PASSWORD,
};
