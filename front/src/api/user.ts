import axios from "axios";
import { UserProps } from "types/userType";
import config from "config/config";


const userApi = {
  postRegister: async function postSignup(signupData: UserProps) {
    const res = await axios.post(
      `${config.gantryApiUrl}/users`,
      signupData
    );
    return res;
  },
};
export default userApi;
