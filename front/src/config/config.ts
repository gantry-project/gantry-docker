interface Config {
    nodeEnv: string;
    gantryApiServerHost: string;
    gantryApiUrl: string;
}

function getGantryApiServerHost() {
    return process.env.REACT_APP_GANTRY_API_SERVER_HOST || "http://localhost:8080";
}

const config: Config = {
    nodeEnv : process.env.NODE_ENV || "development",
    gantryApiServerHost : getGantryApiServerHost(),
    gantryApiUrl : getGantryApiServerHost() + "/api/v1",
};


export default config;