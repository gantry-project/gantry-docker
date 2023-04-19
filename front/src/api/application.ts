import axios from "axios";
import config from "config/config";

const applicationApi = {
  getApplication: async function getApplication() {
    const res = await axios.get(`${config.gantryApiUrl}/applications`);
    return res.data;
  },
};
export default applicationApi;
