import axios from "axios";
import {UserPrincipal, UserProps} from "types/UserType";
import config from "config/config";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";


const userApi = {
  postRegister: async function postSignup(signupData: UserProps) {
    const res = await axios.post(`${config.gantryApiUrl}/users`, signupData);
    return res;
  },
};
export default userApi;

export function useAuthUser(): UserPrincipal|null {
  const {data} = useQuery({
    queryKey: ["authUser"],
    queryFn: getAuthUser,
  });

  const result = data == undefined ? null : data;
  return result;
};

export function useAuthUserMutation() {
  const queryClient = useQueryClient();

  const mutation = useMutation(
    ["authUser"],
    async (userPrincipal?: UserPrincipal) => {
      if (userPrincipal == null) {
        localStorage.removeItem("authUser");
      } else {
        localStorage.setItem("authUser", JSON.stringify(userPrincipal));
      }
      return userPrincipal;
    },
    {
      onSuccess: user => {
        const userJson = localStorage.getItem("authUser");
        const userPrincipal = userJson != null ? JSON.parse(userJson) as UserPrincipal : null;
        queryClient.setQueryData(["authUser"], userPrincipal);
        console.log("queryClient", queryClient.getQueryData(["authUser"]));
      }
    }
  );

  return {
    login: (userPrincipal: UserPrincipal) => mutation.mutate(userPrincipal),
    logout: () => mutation.mutate(undefined),
    mutation: mutation
  };
};

export function getAuthUser() {
  const userJson = localStorage.getItem("authUser");
  return userJson ? JSON.parse(userJson) as UserPrincipal : null;
}