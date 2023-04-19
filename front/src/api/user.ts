import axios from "axios";
import { UserProps } from "../types/userType";

const userApi = {
  postRegister: async function postSignup(signupData: UserProps) {
    const res = await axios.post(
      "http://localhost:8080/api/v1/users",
      signupData
    );
    return res;
  },
};
export default userApi;
