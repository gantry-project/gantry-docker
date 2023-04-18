import axios from "axios";

const applicationApi = {
  getApplication: async function getApplication() {
    const res = await axios.get("http://localhost:8080/api/v1/applications");
    return res.data;
  },
};
export default applicationApi;
